package com.sparkwebsocket;

import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.article;
import static j2html.TagCreator.b;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;

public class Chat {

  static Map<Session, String> userUserNameMap = new ConcurrentHashMap<>();
  static int nextUserNumber = 1;

  public static void broadcastMessage(String sender, String message) {
    userUserNameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
      try {
        session.getRemote().sendString(String.valueOf(new JSONObject()
                .put("userMessage", createHtmlMessageFromSender(sender, message))
                .put("userlist", userUserNameMap.values())
        ));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  private static String createHtmlMessageFromSender(String sender, String message) {
    return article().with(
            b(sender + " says:"),
            p(message),
            span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
    ).render();
  }

}
