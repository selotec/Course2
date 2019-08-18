package lesson7.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public HBox upperPanel;
    @FXML
    public HBox bottomPanel;

    private boolean isAuthorized;


    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
        if(isAuthorized){
            upperPanel.setVisible(false);
            bottomPanel.setVisible(true);
        } else{
            upperPanel.setVisible(true);
            bottomPanel.setVisible(false);
        }

    }

    /*
        Пытался создать текстовый лейбл, в который можно было бы передавать никнейм (чтоб не путаться между окнами клиентов).
        Клиент хэндлер возвращал ответ вида /authok nick1, я разбивал эту строку, чтобы получить никнейм.
        Далее менялся текст лейбла, лейбл делался видимым.
        Но при запуске получил исключение - java.lang.IllegalStateException: Not on FX application thread
        На стековерфлоу говорят нужно использовать Platform.runLater(new Runnable(),
        например тут https://stackoverflow.com/questions/49343256/threads-in-javafx-not-on-fx-application-thread
        Но так и не понял, почему например textArea мы спокойно меняем, а при изменении лейбла получаем исключение.
     */

    public void connect(){
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    //цикл авторизации
                    while (true) {
                        String str = in.readUTF();
                        if(str.equals("/authok")){
                            setAuthorized(true);
                            break;
                        }
                        textArea.appendText(str + "\n");
                    }
                    // цикл работы
                    while (true) {
                        String str = in.readUTF();
                        textArea.appendText(str + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setAuthorized(false);
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMSG(ActionEvent actionEvent) {
        try {
            out.writeUTF(textField.getText());
            textField.setText("");
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()){
            connect();
        }
        try {
            out.writeUTF("/auth "+ loginField.getText()+" "+ passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
