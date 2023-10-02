package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.User;
import com.developerstack.edumanage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpFormController {
    public AnchorPane context;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public PasswordField txtPassword;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        String email=txtEmail.getText().toLowerCase();
        String firstName=txtFirstName.getText();
        String lastName=txtLastName.getText();
        String password=new PasswordManager().encrypt(txtPassword.getText().trim());
        Database.userTable.add(
                new User(firstName,lastName,email,password)
        );
        new Alert(Alert.AlertType.INFORMATION,"Welcome!").show();
        setUi("LoginForm");
    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage=(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();

    }
}
