package com.mall.dao;

import com.mall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);
    //mybatis 有多个参数要用Param声明
    User selectLogin(@Param("username") String username,@Param("password") String password);

    int  checkUseremail(String email);
    String selectUserQuestionByUsername(String username);
    int checkAnswer(@Param("username") String username,@Param("question") String question,@Param("answer")String answer);
    int updatePasswordByUsername(@Param("username") String username,@Param("passwordNew") String passwordNew);
    int checkPassword(@Param(value="password")String password,@Param("userId")Integer userId);
    int checkEmailByUserId(@Param("email")String email,@Param("userId") Integer userId);
}