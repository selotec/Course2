/*
    1. Сделал textArea нередактируемым
    2. Автоматический перенос текста в textArea
    3. Возвращаем фокус в textField после нажатия на кнопку
    4. Кастомная иконка для приложения
 */


package lesson4.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.getIcons().add(new Image("lesson4/resources/email.png"));
        primaryStage.setTitle("EasyChat");
        primaryStage.setScene(new Scene(root, 400, 375));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
