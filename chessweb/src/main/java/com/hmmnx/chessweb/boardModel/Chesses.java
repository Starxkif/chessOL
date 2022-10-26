package com.hmmnx.chessweb.boardModel;

import javax.websocket.Session;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: chessOL
 * @description:
 * @author: ZAM
 * @create: 2022-10-23 00:11
 **/
//棋局对象
public class Chesses {
    //棋子共有10行9列
    //大写是0方，小写是1方
    public char map[][] = {
            {'C', 'M', 'X', 'S', 'J', 'S', 'X', 'M', 'C'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', 'P', ' ', ' ', ' ', ' ', ' ', 'P', ' '},
            {'B', ' ', 'B', ' ', 'B', ' ', 'B', ' ', 'B'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'b', ' ', 'b', ' ', 'b', ' ', 'b', ' ', 'b'},
            {' ', 'p', ' ', ' ', ' ', ' ', ' ', 'p', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'c', 'm', 'x', 's', 'j', 's', 'x', 'm', 'c'},
    };
    public int curPlayer = 0;//当前轮到谁走，0是小写，1是大写
    public Index index = new Index(-1,-1);   //记录上次点击的内容
    public List<Session> sessionList=new ArrayList<>();
}
