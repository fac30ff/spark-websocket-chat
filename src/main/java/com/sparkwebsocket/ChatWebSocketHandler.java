package com.sparkwebsocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

public class ChatWebSocketHandler {
  private String sender, msg;

  @OnWebSocketConnect
  public void onConnect(Session user) throws Exception {
    String username = "User" + Chat.nextUserNumber++;
    Chat.userUserNameMap.put(user, username);
    Chat.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
  }

  @OnWebSocketClose
  public void onClose(Session user, int statusCode, String reason) {
    String username = Chat.userUserNameMap.get(user);
    Chat.userUserNameMap.remove(user);
    Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
  }

  @OnWebSocketMessage
  public void onMessage(Session user, String message) {
    Chat.broadcastMessage(sender = Chat.userUserNameMap.get(user), msg = message);
  }
}
