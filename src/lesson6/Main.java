package lesson6;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Socket socket;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(8191);
            socket = serverSocket.accept();

            Scanner serverInStream = new Scanner(socket.getInputStream());
            Scanner clientInStream = new Scanner(System.in);
            PrintStream serverOutStream = new PrintStream(socket.getOutputStream(), true);

            /*
                Честно до конца не понимаю, какой из этих тредов стоит назвать клиентским,
                а какой серверным.
                Вероятно еще есть путаница в названии потоков, но вроде бы socket.getInputStream() - серверный in
                Мало времени оставалось, не придумал как изящно завершить сервер, если отключился клиент.
                Думал в сторону демонизации серверного треда, но тогда видимо нужно было создавать тред не
                через анонимный класс.
                В общем, еще немного путаюсь где чьи стримы)
             */
            Thread clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        String str = serverInStream.nextLine();
                        if(str.equals("/bye")) {
                            break;
                        }
                        System.out.println("client: " + str);
                    }
                }
            });

            Thread serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        String str = clientInStream.nextLine();
                        if(str.equals("/stop")) {
                            break;
                        }
                        serverOutStream.println("server: " + str);
                    }
                }
            });

            clientThread.start();
            serverThread.start();

            try {
                clientThread.join();
                serverThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                serverSocket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
