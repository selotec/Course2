package lesson7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    String nick;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    // цикл авторизации
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");
//                            String[] token = str.split(" +",3);
                            String newNick = AuthService.getNickByLoginAndPass(token[1], token[2]);
                            if (newNick != null) {
                                sendMSG("/authok");
                                nick = newNick;
                                server.subscribe(this);
                                System.out.println("Клиент " + nick + " авторизовался");
                                break;
                            } else {
                                sendMSG("Неверный логин / пароль");
                            }
                        }
                    }
                    //цикл работы
                    while (true) {
                        String str = in.readUTF();
                        // О чем говорилось в конце вебинара - при помощи limit отделяем сообщение от служебных команд
                        String[] whisper = str.split(" ", 3);
                        // Если клиент запросил отправку приватного сообщения
                        if (whisper[0].equals("/w")) {
                            // Просим сервер переслать сообщение только адресату
                            server.proxyPrivateMessage(whisper[2], whisper[1]);
                            continue;
                        }
                        if (str.equals("/end")) {
                            break;
                        }

                        server.broadcastMsg(nick + " : " + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                    System.out.println("Клиент " + nick + " отключился");
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMSG(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
