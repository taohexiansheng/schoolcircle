package com.xiaoyuan.schoolcircle.service;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

@Service
public class OcrService {

    @Autowired
    private AipOcr aipOcr;

    public String recognizeText(MultipartFile imageFile) {
        try {
            byte[] originalBytes = imageFile.getBytes();
            // 1. 图片智能缩放预处理（限制长边不超过2000px，防止大字被压缩模糊）
            byte[] processedBytes = resizeImageIfNecessary(originalBytes);

            // 2. 调用高精度版接口，并开启概率返回
            HashMap<String, String> options = new HashMap<>();
            options.put("detect_direction", "true");
            options.put("probability", "true");

            JSONObject res = aipOcr.basicAccurateGeneral(processedBytes, options);

            if (res.has("error_code")) {
                System.err.println("识别失败: " + res.getInt("error_code") + " - " + res.getString("error_msg"));
                throw new RuntimeException("百度OCR返回错误: " + res.getString("error_msg"));
            }

            if (res.has("words_result")) {
                return parseAndSmartExtract(res.getJSONArray("words_result"));
            }

            return "未识别到有效文字";

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("图片识别异常: " + e.getMessage());
        }
    }

    /**
     * 智能提取书名（基于置信度和字数打分）和出版社
     */
    /**
     * 智能提取书名（终极版：高置信度+拉满黑名单+长度限制）
     */
    private String parseAndSmartExtract(JSONArray wordsResult) {
        String bestBookName = "";
        double highestScore = -9999;
        String publisher = "";

        // 🚫 终极黑名单：教材封面的常见废话和干扰词
        String[] blacklist = {
                "教材", "规划", "世纪", "十五", "主编", "编著", "出版", "社", "杜",
                "供", "用", "第", "版", "册", "卷", "系列", "丛书", "高等学校",
                "国家级", "职业教育", "十二五", "十三五", "十四五"
        };

        for (int i = 0; i < wordsResult.length(); i++) {
            JSONObject item = wordsResult.getJSONObject(i);
            String word = item.getString("words").trim();

            // 获取置信度
            double prob = 0.0;
            if (item.has("probability") && item.getJSONObject("probability").has("average")) {
                prob = item.getJSONObject("probability").getDouble("average");
            }

            // 1. 提取出版社（自动纠错）
            if (word.contains("出版") || word.contains("社") || word.contains("杜")) {
                publisher = word.replace("教有", "教育").replace("，杜", "社").replace("杜", "社");
                continue; // 是出版社，不参与书名打分
            }

            // 2. 过滤纯英文/数字
            if (word.matches("^[a-zA-Z0-9\\s\\p{Punct}]+$")) continue;

            // 3. 黑名单拦截（只要包含这些词，直接淘汰）
            boolean isBlacklisted = false;
            for (String blackWord : blacklist) {
                if (word.contains(blackWord)) {
                    isBlacklisted = true;
                    break;
                }
            }
            if (isBlacklisted || word.length() <= 1) {
                continue;
            }

            // 4. 终极打分逻辑
            double score = (prob * 100); // 置信度打底（一般都在 90-100 分）

            // 书名通常不会太长，也不会太短
            if (word.length() >= 2 && word.length() <= 10) {
                score += 50; // 核心书名长度（如：有机化学、高等数学），重赏！
            } else if (word.length() > 10 && word.length() <= 15) {
                score += 10; // 稍长，可能是副标题
            } else {
                score -= 50; // 超过15个字基本是废话，扣分！
            }

            if (score > highestScore) {
                highestScore = score;
                bestBookName = word;
            }
        }

        // 5. 组装结果
        StringBuilder result = new StringBuilder();
        if (!bestBookName.isEmpty()) {
            result.append(bestBookName);
        }
        if (!publisher.isEmpty()) {
            result.append(" | ").append(publisher);
        }

        return result.length() > 0 ? result.toString() : "未能提取到有效书名";
    }
    /**
     * 图片缩放防压缩糊字
     */
    private byte[] resizeImageIfNecessary(byte[] originalBytes) {
        try {
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(originalBytes));
            if (originalImage == null) return originalBytes;

            int maxSide = Math.max(originalImage.getWidth(), originalImage.getHeight());
            if (maxSide <= 2000) return originalBytes;

            double scale = 2000.0 / maxSide;
            int newWidth = (int) (originalImage.getWidth() * scale);
            int newHeight = (int) (originalImage.getHeight() * scale);

            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            return originalBytes;
        }
    }
}