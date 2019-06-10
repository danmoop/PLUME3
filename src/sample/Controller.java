package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    JFXButton btn;

    @FXML
    JFXTextField textField;

    private String textFieldText;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println("Initialized");

        textField.textProperty().addListener((observable, oldValue, newValue) ->
                textFieldText = newValue);
    }
}