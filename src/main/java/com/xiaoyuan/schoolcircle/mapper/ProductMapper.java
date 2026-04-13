package com.xiaoyuan.schoolcircle.mapper;

import com.xiaoyuan.schoolcircle.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO product(user_id, title, description, price, images, campus, status) " +
            "VALUES(#{userId}, #{title}, #{description}, #{price}, #{images}, #{campus}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    @Select("SELECT * FROM product WHERE id = #{id}")
    Product findById(Integer id);

    @Select("SELECT * FROM product ORDER BY created_at DESC")
    List<Product> findAll();

    @Select("SELECT * FROM product WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<Product> findByUserId(Integer userId);

    @Update("UPDATE product SET title=#{title}, description=#{description}, price=#{price}, images=#{images}, campus=#{campus}, status=#{status} WHERE id=#{id}")
    int update(Product product);

    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteById(Integer id);

    @Select("SELECT * FROM product WHERE title LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%')")
    List<Product> search(String keyword);
}