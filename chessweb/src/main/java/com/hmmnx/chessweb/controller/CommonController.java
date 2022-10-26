package com.hmmnx.chessweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmmnx.chessweb.boardModel.ChessUtils;
import com.hmmnx.chessweb.boardModel.Chesses;
import com.hmmnx.chessweb.pojo.User;
import com.hmmnx.chessweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @program: chessOL
 * @description:
 * @author: ZAM
 * @create: 2022-10-23 15:03
 **/
@Controller
public class CommonController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url) {
        return url;
    }

    @RequestMapping("/login")
    @ResponseBody
    public JSONObject login(String account, String password, HttpSession httpSession) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("password", password);
        User one = userService.getOne(queryWrapper);
        JSONObject json = new JSONObject();
        json.put("flag", false);
        if (one != null) {
            json.put("flag", true);
            httpSession.setAttribute("account", account);
        }
        return json;
    }

    @RequestMapping("/register")
    @ResponseBody
    public JSONObject register(String account, String password) {
        User user = new User(account, password, 0);
        boolean save = userService.save(user);
        JSONObject json = new JSONObject();
        json.put("flag", save);
        return json;
    }

}
