package com.mall.controller.backend;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.pojo.User;
import com.mall.service.ICategoryService;
import com.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CateManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

@RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId",defaultValue ="0")int parentId){
    User user = (User)session.getAttribute(Const.CURRENT_USER);
    if(user==null){
        return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
    }
    if(iUserService.checkAdminRole(user).isSuccess()){
              return iCategoryService.addCategory(categoryName,parentId);
    }else{
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

}
    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
    User user = (User)session.getAttribute(Const.CURRENT_USER);
    if(user==null){
        return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
    }
    if(iUserService.checkAdminRole(user).isSuccess()){
        return iCategoryService.updateCategoryName(categoryId,categoryName);
    }else{
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
}
@RequestMapping(value = "get_category_and_deep_children_by_id.do")
@ResponseBody
public ServerResponse getCategoryAndDeepChildrenById(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0")Integer categoryId){
    User user = (User)session.getAttribute(Const.CURRENT_USER);
    if(user == null){
        return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
    }
    if(iUserService.checkAdminRole(user).isSuccess()){
        //查询当前节点的id和递归子节点的id
//            0->10000->100000
        return iCategoryService.selectCategoryAndChildrenById(categoryId);

    }else{

        return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
    }
}


}
