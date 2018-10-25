package com.mall.controller.portal;

        import com.github.pagehelper.PageHelper;
        import com.github.pagehelper.PageInfo;
        import com.mall.common.Const;
        import com.mall.common.ResponseCode;
        import com.mall.common.ServerResponse;
        import com.mall.pojo.Shipping;
        import com.mall.pojo.User;
        import com.mall.service.IShippingService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.stereotype.Service;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.ResponseBody;

        import javax.servlet.http.HttpSession;
        import java.util.List;

@Controller
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    private IShippingService iShippingService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session,Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        return iShippingService.add(user.getId(),shipping);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpSession session,Integer shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        return iShippingService.del(user.getId(),shippingId);
    }
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session,Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆");
        }
        return iShippingService.update(user.getId(),shipping);
    }
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse select(HttpSession session,Integer shippingId){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        return iShippingService.select(user.getId(),shippingId);
    }
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(user.getId(), pageNum, pageSize);
    }
}
