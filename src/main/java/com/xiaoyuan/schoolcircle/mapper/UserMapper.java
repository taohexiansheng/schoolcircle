package com.xiaoyuan.schoolcircle.mapper;

import com.xiaoyuan.schoolcircle.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO user(username, password, real_name, student_id, campus) VALUES(#{username}, #{password}, #{realName}, #{studentId}, #{campus})")
    int insert(User user);
}
