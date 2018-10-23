package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.User;
import org.springframework.stereotype.Service;

public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str,String type);
    ServerResponse<String> forgetGetQuestion(String username);
    ServerResponse<String> forgetCheckQuestion(String username,String question,String answer);
    ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken);
    ServerResponse<String> resetPassword(String passwordOld,String PasswordNew,User user);
    ServerResponse<User> updateInformation(User user);
    ServerResponse getInformation(Integer userId);
    ServerResponse checkAdminRole(User user);
}
