package lesson7.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server {
    private Vector<ClientHandler> clients;

    public Server() throws SQLException {
        AuthService.connect();
//        System.out.println(AuthService.getNickByLoginAndPass("log2in1","pass1"));

        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    // Ищем и возвращаем клиент хэндлер по заданному нику, либо null
    public ClientHandler getClientByNick(String nick) {
        ClientHandler requested = null;

        for(ClientHandler ch: clients) {
            if(ch.nick.equals(nick)) {
                requested = ch;
                break;
            }
        }

        return requested;
    }

    public void broadcastMsg(String msg){
        for (ClientHandler c:clients ) {
            c.sendMSG(msg);
        }
    }

    // Пересылаем приватное сообщение только запрошенному адресату
    public void proxyPrivateMessage(String msg, String nick) {
        ClientHandler requested = getClientByNick(nick);
        // Только если такой ник существует
        if (requested != null) {
            requested.sendMSG("Whisper from " + nick + ": " + msg);
        }
    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }
}
