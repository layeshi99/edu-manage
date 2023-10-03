package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.view.tm.StudentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StudentFormController {
    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public DatePicker txtDob;
    public TableView<StudentTM> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDob;
    public TableColumn colAddress;
    public TableColumn colOption;
    public Button btn;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setStudentId();
        setTableData();

        tblStudent.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (null!=newValue){
                        setData(newValue);
                    }
                });
    }

    private void setData(StudentTM tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getFullName());
        txtAddress.setText(tm.getAddress());
        txtDob.setValue(LocalDate.parse(tm.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        btn.setText("Update Student");
    }

    private void setTableData() {
        ObservableList<StudentTM> obList= FXCollections.observableArrayList();
        for(Student st:Database.studentTable){
            Button btn=new Button("Delete");
            StudentTM tm=new StudentTM(
                    st.getStudentId(),
                    st.getFullName(),
                    new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),
                    st.getAddress(),
                    btn
            );
            obList.add(tm);
        }
        tblStudent.setItems(obList);
    }

    private void setStudentId() {
        if(!Database.studentTable.isEmpty()){
            Student lastStudent=Database.studentTable.get(
                    Database.studentTable.size()-1
            );
            String lastId=lastStudent.getStudentId();
            String splitData[]=lastId.split("-");
            String lastIntegerNumberAsString=splitData[1];
            int lastIntegerIdAsInt=Integer.parseInt(lastIntegerNumberAsString);
            lastIntegerIdAsInt++;
            String generatedStudentId="S-"+lastIntegerIdAsInt;
            txtId.setText(generatedStudentId);
        }else{
            txtId.setText("S-1");
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if(btn.getText().equalsIgnoreCase("Save Student")){
            Student student=new Student(
                    txtId.getText(),
                    txtName.getText(),
                    Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    txtAddress.getText()
            );
            Database.studentTable.add(student);
            setStudentId();
            clear();
            setTableData();
            new Alert(Alert.AlertType.INFORMATION,"Student Saved").show();

        }else {
            for (Student st:Database.studentTable) {
                if (st.getStudentId().equals(txtId.getText())){
                    st.setAddress(txtAddress.getText());
                    st.setFullName(txtName.getText());
                    st.setDateOfBirth(Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    setTableData();
                    clear();
                    setStudentId();
                    btn.setText("Save Student");
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING, "Not Found").show();
        }

    }

    private void clear(){
        txtDob.setValue(null);
        txtName.clear();
        txtAddress.clear();
    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        clear();
        setStudentId();
        btn.setText("Save Student");
    }
}
