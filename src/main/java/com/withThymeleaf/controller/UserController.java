package com.withThymeleaf.controller;

import com.withThymeleaf.pojo.User;
import com.withThymeleaf.service.UserService;
import com.withThymeleaf.utils.VerifyCodeUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping("/login")
    public String login(String username, String password, HttpSession session) {

        try {
            // 进行登录
            User user = userService.login(username, password);

            // 将用户信息保存到会话中
            session.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }

        return "redirect:/employee/lists";// 直接跳转将会没有数据, 跳转到 EmployeeController 进行加载数据
    }

    // 员工注册
    @PostMapping("/register")
    public String register(User user, String code, HttpSession session) {
        try {
            String verifyCode = session.getAttribute("code").toString();

            if (!verifyCode.equalsIgnoreCase(code)) throw new RuntimeException("验证码输入错误!");

            userService.register(user);
        } catch (RuntimeException e) {// 出现异常则继续注册
            e.printStackTrace();
            return "redirect:/register";
        }

        return "redirect:/login";
    }

    // 退出
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 销毁会话信息
        session.invalidate();

        return "redirect:/login";
    }

    /**
     * 生成随机数<br>
     * => 生成 四 位随机数<br>
     * => 将随机数保存到 session 作用域中<br>
     * ==> 页面首次加载的时候将会请求验证码, 当用户点击进行请求时候, 将会刷新 request 域<br>
     * => 根据随机数生成图片<br>
     * => 通过 response 进行响应图片
     */
    @GetMapping("/generateImage")
    @SneakyThrows
    public void generateImageCode(HttpSession session, HttpServletResponse response) {
        // 生成随机数
        String code = VerifyCodeUtils.generateVerifyCode(4);

        // 保存到 session 作用域中
        session.setAttribute("code", code);

        // 设置响应类型
        response.setContentType("image/png");

        ServletOutputStream out = response.getOutputStream();

        VerifyCodeUtils.outputImage(220, 60, out, code);

        // 刷新关闭流
        out.flush();
        out.close();
    }

}