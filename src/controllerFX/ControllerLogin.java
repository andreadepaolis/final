package controllerFX;

import bean.ProfessorBean;
import bean.StudentBean;
import bean.UserLoginBean;
import controller.ControllerHomeStudent;
import database.ProfessorDao;
import database.StudentDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Professor;
import model.Student;
import utils.BasicExcpetion;
import utils.CustomException;
import utils.ToastException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;


public class ControllerLogin extends ControllerScenes implements Initializable {

    public TextField matricolaStudente;
    public PasswordField passwordStudente;

    public ControllerLogin(){} //Costruttore di default

    @FXML
    private AnchorPane root;

    @FXML
    private Label isRightProf;

    @FXML
    private TextField matricolaProf;

    @FXML
    private PasswordField passwordProf;

    private UserLoginBean generateBean(String matricola , String password){
        UserLoginBean u = new UserLoginBean();
        u.setMatricola(matricola);
        u.setPassword(password);
        return u;
    }

    private ProfessorBean validateProfessor(UserLoginBean user){
        Professor prof = null;
        try {
            prof = ProfessorDao.validate(user.getMatricola(),user.getPassword());
        } catch (SQLException | CustomException e) {
            e.printStackTrace();
        }
        ProfessorBean pb = null;
        if(prof != null){

            pb = new ProfessorBean();
            pb.setMatricola(prof.getMatricola());
            pb.setLastname(prof.getLastname());
            pb.setName(prof.getName());
        }

        return pb;
    }

    private StudentBean checkStudente(UserLoginBean user){
        StudentBean student = null;
        try {
            student = StudentDao.validate(user.getMatricola(),user.getPassword());
        } catch (SQLException | BasicExcpetion e) {
            e.printStackTrace();
        }
        return student;
    }

    @FXML
    public void checkLoginProf(ActionEvent actionEvent) throws Exception {
        String matricola = matricolaProf.getText();
        String password = passwordProf.getText();
        UserLoginBean user = generateBean(matricola , password);
        ProfessorBean pb = validateProfessor(user);
        if(pb != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/professorHome.fxml"));
            this.setProfessor(pb);
            AnchorPane pane = loader.load();
            root.getChildren().setAll(pane);
        }else{
            isRightProf.setText("Non Trovato");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.matricolaProf.setText("9999");
        this.passwordProf.setText("pass");
        this.matricolaStudente.setText("1234");
        this.passwordStudente.setText("password");
    }

    public void loginStudent(ActionEvent actionEvent) throws IOException, ToastException {
        String matricola = matricolaStudente.getText();
        String password = passwordStudente.getText();
        UserLoginBean user = generateBean(matricola , password);
        StudentBean sb = this.checkStudente(user);
        controller.ControllerHomeStudent cntl = new ControllerHomeStudent();
        sb = cntl.full(sb);
        sb.setCurrentDate(new Date());
        this.setStudent(sb);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/homeStudent.fxml"));
        AnchorPane pane = loader.load();
        root.getChildren().setAll(pane);
    }
}
