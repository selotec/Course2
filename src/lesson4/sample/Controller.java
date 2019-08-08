package lesson4.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
   @FXML public TextArea textArea;
   @FXML public TextField textField;


    public void sendMSG(ActionEvent actionEvent) {
        // Проверяем, что текст не пустой, обычно в чате нельзя отправить пустое сообщение
        if(textField.getText().trim().length() > 0) {
            textArea.appendText(textField.getText() + "\n");
            textField.setText("");
            textField.requestFocus(); // запрос фокуса после нажатия на кнопку
        }
        else {
            // На случай, если сообщение содержало одни пробелы, чистим text field
            textField.setText("");
        }
    }
}
