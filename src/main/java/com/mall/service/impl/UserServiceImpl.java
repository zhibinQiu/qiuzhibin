package com.mall.service.impl;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.common.TokenCache;
import com.mall.dao.UserMapper;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import com.mall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //TODO 密码登录MD5

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user  = userMapper.selectLogin(username,md5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
           return ServerResponse.createBySuccess("登陆成功",user);

    }
    //注册
    public ServerResponse<String> register(User user){
        ServerResponse validResponse = this.checkValid(user.getUsername(),user.getPassword());
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);

        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuucessMessage("注册成功");

    }
    //检查有效性
    public ServerResponse checkValid(String str,String type)
    {
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(type)){
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }

            }
            if(Const.EMAIL.equals(type)){
                int resultCount= userMapper.checkUseremail(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }

        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuucessMessage("校验成功");
    }

    //获取密保问题
    public ServerResponse<String> forgetGetQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.selectUserQuestionByUsername(username);
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(question)){
            return ServerResponse.createBySuucessMessage(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题为空");
    }
   //验证密保答案
    public ServerResponse<String> forgetCheckQuestion(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
//            生成Token
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PRIFIX+username,forgetToken);
            return  ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }
  //忘记密码时的重设密码
    public ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken){
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(forgetToken))
        {
            return ServerResponse.createByErrorMessage("参数错误，Token需要传递");
        }
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PRIFIX+username);
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或过期");

        }
        if(org.apache.commons.lang3.StringUtils.equals(forgetToken,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(newPassword);
            int rowCount = userMapper.updatePasswordByUsername(username,md5Password);
            if(rowCount>0){
                return ServerResponse.createBySuucessMessage("修改密码成功");

            }else{
                return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
            }
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }
    //登陆后的重设密码
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        //防止横向越权，校验旧密码一定要指向该用户
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount>0){
            return ServerResponse.createBySuucessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }

    //更新个人信息
    public ServerResponse<User> updateInformation(User user){
        //username不能被更新
        //email也要校验
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("邮箱已存在");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount>0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }
    //强制登录
    public ServerResponse getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return  ServerResponse.createBySuccess(user);

    }
    //backend
    //校验是否是管理员登录
    public ServerResponse checkAdminRole(User user){
        if(user!=null&&user.getRole().intValue()==Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}
