// File Name GreetingServer.java

import java.net.*;
import java.io.*;

public class GreetingServer extends Thread {
  private ServerSocket serverSocket;
  
  public GreetingServer(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    serverSocket.setSoTimeout(10000); 
  }
  
  public void run() {
    while (true) {
      try {
        System.out.println("Waiting for client on port "
          + serverSocket.getLocalPort() + "...");
        Socket server = serverSocket.accept();
        System.out.println("Just connected to " 
          + server.getRemoteSocketAddress());
        DataInputStream in = new DataInputStream(server.getInputStream());
        System.out.println(in.readUTF());
        DataOutputStream out = new DataOutputStream(server.getOutputStream());
        out.writeUTF("Thank U for connecting to "
          + server.getLocalSocketAddress() + "\nGo?odBye");
        server.close();
      } catch (SocketTimeoutException e) {
        System.out.println("Socket Time Out!");
        break;
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
    }
  }
  
  public static void main(String[] args) {
    int port = Integer.parseInt(args[0]);
    try {
      Thread T = new GreetingServer(port);
      T.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
