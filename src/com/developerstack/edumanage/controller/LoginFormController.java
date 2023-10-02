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
import java.util.Optional;

public class LoginFormController {
    public TextField txtEmail;
    public PasswordField txtPassword;
    public AnchorPane context;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String email=txtEmail.getText().toLowerCase();
        String password=txtPassword.getText().trim();

        /*
        for(User user: Database.userTable){
            if(user.getEmail().equals(email)){
                if(user.getPassword().equals(password)){
                    System.out.println(user.toString());
                    return;
                }else{
                    new Alert(Alert.AlertType.ERROR,
                            "Wrong Password").show();
                }

            }
        }
        new Alert(Alert.AlertType.WARNING,
                String.format("User not found (%s)",email)).show();

         */
        Optional<User> selectedUser=
                Database.userTable.stream().filter(e->e.getEmail().equals(email)).findFirst();
        if(selectedUser.isPresent()){
            if(new PasswordManager().checkPassword(password,selectedUser.get().getPassword())){
                setUi("DashBoardForm");
            }else{
                new Alert(Alert.AlertType.ERROR,
                        "Wrong Password").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,
                    String.format("User not found (%s)",email)).show();

        }
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignUpForm");
    }

    public void forgotPasswordOnAction(ActionEvent actionEvent) {
    }

    private void setUi(String location) throws IOException {
        Stage stage=(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
