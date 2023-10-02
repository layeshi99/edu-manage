package com.developerstack.edumanage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoardFormController {
    public AnchorPane context;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        setData();
    }
    public void setData(){
        lblDate.setText(new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
        lblTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }
    private void setUi(String location) throws IOException {
        Stage stage=(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void openStudentFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("StudentForm");
    }
}
