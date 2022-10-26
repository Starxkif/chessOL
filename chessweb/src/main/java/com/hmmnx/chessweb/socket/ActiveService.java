package com.hmmnx.chessweb.socket;

import com.alibaba.fastjson.JSONObject;
import com.hmmnx.chessweb.boardModel.ChessUtils;
import com.hmmnx.chessweb.boardModel.Chesses;

import javax.websocket.Session;
import java.util.Map;

/**
 * @program: chessOL
 * @description:
 * @author: ZAM
 * @create: 2022-10-24 11:13
 **/
public class ActiveService {
    public static String dianji(Map map, Chesses chesses) {
        Integer x = (Integer) map.get("x");
        Integer y = (Integer) map.get("y");
        int flag = ChessUtils.click(chesses, x, y);
        JSONObject jb = new JSONObject();
        jb.put("flag", flag);
        jb.put("curPlay", chesses.curPlayer);
        if (flag == 2) {
            jb.put("map", ChessUtils.mapStr(chesses));
        }
        return jb.toJSONString();
    }

    public static void huiqi() {
    }

    public static void daochu() {
    }

    public static void qiuhe() {
    }

    public static void renshu() {
    }

    public static void faxiaoxi() {
    }

    public static String pipei(Session session, String account) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag", 0);
        WebSocketServer.getMap().forEach((k, v) -> {
            //找到一个单人房间
            if (v.sessionList.size() == 1) {
                v.sessionList.add(session);
                WebSocketServer.getMap().put(account, v);
                jsonObject.put("flag", 2);
                jsonObject.put("curPlay", v.curPlayer);
                jsonObject.put("map", ChessUtils.mapStr(v));
            }
        });
        Integer flag = (Integer) jsonObject.get("flag");
        if (flag==0) {
            //找不到自己创建一个
            Chesses chesses = new Chesses();
            chesses.sessionList.add(session);
            WebSocketServer.getMap().put(account, chesses);
            jsonObject.put("flag", 0);
        }
        return jsonObject.toJSONString();
    }
}
