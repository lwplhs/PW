package com.lwp.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

import static com.lwp.blog.utils.WebSocketUtils.*;

@RestController
@ServerEndpoint("/chat-room/{username}")
@Component
public class ChatRoomServerEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRoomServerEndpoint.class);

    @OnOpen
    public  void openSession(@PathParam("username") String usernanme, Session session){
        LIVING_SESSION_CACHE.put(usernanme,session);
        String message = "欢迎用户["+usernanme+"]来到聊天室！";
        LOGGER.info(message);
        sendMessageAll(message);
    }

    @OnMessage
    public void onMessage(@PathParam("username") String username,String message){
        LOGGER.info(message);
        sendMessageAll("用户["+username+"]:"+message);
    }

    @OnClose
    public void onClose(@PathParam("username") String username,Session session){
        //当前Session移除
        LIVING_SESSION_CACHE.remove(username);
        //通知其他人当前用户已经离开聊天室了
        sendMessageAll("用户["+username+"]已经离开聊天室了！");
        try {
            session.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        try{
            session.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

    @GetMapping("/chat-room/{sender}/to/{receive}")
    public void onMessage(@PathVariable("sender") String sender,@PathVariable("receive") String receive,String message){
        sendMessage(LIVING_SESSION_CACHE.get(receive),"[" + sender + "]" + "->[" + receive + "]:" +message);
    }
}
