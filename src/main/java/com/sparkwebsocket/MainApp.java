package com.sparkwebsocket;

import static spark.Spark.init;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

public class MainApp {
  public static void main(String[] args) {
    staticFiles.location("/public"); //index.html is served at localhost:4567 (default port)
    staticFiles.expireTime(600);
    webSocket("/chat", ChatWebSocketHandler.class);
    init();
  }
}
