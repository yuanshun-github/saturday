package com.example.demo.controller;

import com.example.demo.bean.Role;
import com.example.demo.bean.User;
import com.example.demo.common.net.WxResult;
import com.example.demo.common.redis.RedisService;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private BookService bookService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUserById(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(id);
        return user;
    }

    @RequestMapping("/insertUser")
    @ResponseBody
    public int insertUser(@ModelAttribute User user){
        int i = userService.insertUser(user);
        return i;
    }

    /**
     * 获取用户权限
     * @param request
     * @return
     */
    @RequestMapping("/getRoleUrl")
    public String getRoles(HttpServletRequest request){
        String token = request.getParameter("token");
        String name = request.getParameter("name");
        String result = "";
        try {
            Integer userId = (Integer)redisService.get(token);
            userService.updateUserName(new User(userId,name));
            List<Role> roles = userService.selectUserRole(userId);
            result = WxResult.getSuccessResult(roles);
        }catch (Exception e){
            result = WxResult.getException("查询权限异常");
        }finally {
            return result;
        }
    }


    /**
     * 用户更新用户信息
     * @param request
     * @return
     */
    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(HttpServletRequest request){
        String result = "";
        String nickName = request.getParameter("name");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String gender = request.getParameter("gender");
        String token = request.getParameter("token");

        try {
            Integer userId = (Integer)redisService.get(token);
            User user = new User(userId,nickName, province, city, Integer.parseInt(gender));
            userService.updateUserInfo(user);
            result = WxResult.getSuccessResult("授权成功");
        }catch (Exception e){
            result = WxResult.getException("授权更新信息");
        }finally {
            return result;
        }
    }

}
