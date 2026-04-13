CREATE TABLE IF NOT EXISTS `user` (
                                      `id` INT PRIMARY KEY AUTO_INCREMENT,
                                      `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(100) NOT NULL,
    `real_name` VARCHAR(50),
    `student_id` VARCHAR(20) UNIQUE,
    `campus` VARCHAR(20),
    `credit_score` INT DEFAULT 60,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
    );
CREATE TABLE IF NOT EXISTS `product` (
                                         `id` INT PRIMARY KEY AUTO_INCREMENT,
                                         `user_id` INT NOT NULL COMMENT '发布者ID，关联user表',
                                         `title` VARCHAR(100) NOT NULL COMMENT '商品标题',
                                         `description` TEXT COMMENT '商品描述',
                                         `price` DECIMAL(10,2) NOT NULL COMMENT '价格，两位小数',
                                         `images` VARCHAR(500) COMMENT '图片路径，多个用逗号分隔',
                                         `campus` VARCHAR(20) COMMENT '所在校区',
                                         `status` TINYINT DEFAULT 1 COMMENT '状态 1:在售 0:已售',
                                         `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                         FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);
