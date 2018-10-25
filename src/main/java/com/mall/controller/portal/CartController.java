package com.mall.controller.portal;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.pojo.Cart;
import com.mall.pojo.User;
import com.mall.service.ICartService;
import com.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private ICartService iCartService;

@RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpSession session){
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if(user==null){
        return ServerResponse.createByErrorMessage("用户未登录");
    }
    return iCartService.list(user.getId());

}


}
