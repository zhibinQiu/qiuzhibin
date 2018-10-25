package com.mall.controller.portal;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dao.UserMapper;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;


    //用户登录
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
       ServerResponse<User> response = iUserService.login(username,password);
       if(response.isSuccess()){
           session.setAttribute(Const.CURRENT_USER,response.getData());
       }
       return response;
    }

    //登出
    @RequestMapping(value = "logout.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    //注册
    @RequestMapping(value = "register.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    //校验有效性
    @RequestMapping(value = "check_valid.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    //获取用户信息
 @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
 @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if(user!=null){

            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获得用户信息");
    }
    //获取密保问题
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.forgetGetQuestion(username);
    }
    //验证问题答案是否正确
    @RequestMapping(value = "forget_check_answer.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerResponse forgetCheckAnswer(String username,String question,String answer){
        return iUserService.forgetCheckQuestion(username,question,answer);
    }
    //忘记密码中的重置密码
    @RequestMapping(value = "forget_reset_password.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken){
        return iUserService.forgetResetPassword( username, newPassword,forgetToken);
    }
    //登陆后的重置密码
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }
    //修改信息后更新信息

    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateInformation(HttpSession session,User user){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    //强制登录
    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getInformation(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录，status=10");
        }
        return iUserService.getInformation(currentUser.getId());

    }
}

