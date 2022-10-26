package com.hmmnx.chessweb.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hmmnx.chessweb.boardModel.ChessUtils;
import com.hmmnx.chessweb.boardModel.Chesses;
import com.hmmnx.chessweb.boardModel.Index;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@ServerEndpoint("/websocket/{account}")
public class WebSocketServer {
    //当前在线连接数
    private static int onlineCount = 0;
    //存放每个客户端对应的MyWebSocket对象
    private static ConcurrentHashMap<String, Chesses> sessionMap = new ConcurrentHashMap<>();

    private Session session;

    //接收account
    private String account = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("account") String account) {
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:" + account + ",当前在线人数为:" + getOnlineCount());
        this.session = session;
        this.account = account;
        String pipei = ActiveService.pipei(this.session, account);
        sendMessage(pipei);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        //一个人退出也要通知对方
        Chesses chesses = sessionMap.get(account);
        sessionMap.forEach((k,v)->{
            if (v.equals(chesses)) {
                sessionMap.remove(k);
            }
        });
        subOnlineCount();           //在线数减1
        log.info("释放的account为：" + account);
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + account + "的信息:" + message);
        Map requestMap = (Map) JSON.parse(message);
        Chesses chesses = sessionMap.get(account);
        Integer flag = (Integer) requestMap.get("flag");
        String res = "";
        switch (flag) {
            case 1:
                if (chesses.sessionList.get(chesses.curPlayer).equals(this.session)) {
                    //当前点击玩家和移动方一致就可以点击
                    res = ActiveService.dianji(requestMap, chesses);
                }
                break;
            case 2:
                ActiveService.huiqi();
                break;
            case 3:
                ActiveService.daochu();
                break;
            case 4:
                ActiveService.qiuhe();
                break;
            case 5:
                ActiveService.renshu();
                break;
            case 6:
                ActiveService.faxiaoxi();
                break;
        }
        sendMessage(res);
    }

    /**
     * @ Param session
     * @ Param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        //一个人退出也要通知对方
        Chesses chesses = sessionMap.get(account);
        sessionMap.forEach((k,v)->{
            if (v.equals(chesses)) {
                sessionMap.remove(k);
            }
        });
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        Chesses chesses = sessionMap.get(account);
        for (Session session : chesses.sessionList) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static ConcurrentHashMap<String, Chesses> getMap() {
        return sessionMap;
    }
}