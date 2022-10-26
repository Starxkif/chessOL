package com.hmmnx.chessweb.controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @program: chessOL
 * @description:
 * @author: ZAM
 * @create: 2022-10-26 15:48
 **/
@Controller
public class GameController {

    @RequestMapping("/play")
    public void play(String room, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.getAttribute("account");
    }
}
