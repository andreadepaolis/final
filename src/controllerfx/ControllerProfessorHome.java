package controllerfx;

import bean.HomeworkBean;
import bean.ProfessorBean;
import controller.ControllerHomeProfessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Argument;
import model.ScheduleInfo;
import register.ProfessorRegister;
import utils.MonthFactory;
import utils.Month;
import utils.OrariInfo;
import utils.ToastException;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class ControllerProfessorHome extends ControllerScenes implements Initializable{

    private static final Logger LOGGER =Logger.getLogger(ControllerProfessorHome.class.getName());

    @FXML
    private TextArea textAreaArgomenti;
    private ControllerHomeProfessor chp = new ControllerHomeProfessor();

    @FXML
    private AnchorPane root;

    @FXML
    private TextArea areaCompiti;

    @FXML
    private ComboBox comboClasse;

    @FXML
    private ComboBox comboSubject;

    @FXML
    private DatePicker dateHomework;

    @FXML
    private TextArea homeworkDescription;

    @FXML
    private TableView<OrariInfo> tableSchedule;

    private ProfessorBean professor;



    public void addHomework() throws ToastException {
        String classe =  this.comboClasse.getValue().toString();
        String materia = this.comboSubject.getValue().toString();
        String data = this.dateHomework.getValue().toString();
        String description = this.homeworkDescription.getText();
        ControllerHomeProfessor chpHomework = new ControllerHomeProfessor();
        HomeworkBean hmwbean = chpHomework.generateHomeworkBean(classe,description,materia,data,this.professor.getMatricola());
        this.chp.save(hmwbean);
        this.areaCompiti.appendText(hmwbean.getMyclasse() + " " + hmwbean.getData() + " " + hmwbean.getMateria() + "\n");
        this.areaCompiti.appendText(hmwbean.getDescription());
        this.areaCompiti.appendText("\n \n");
    }


    public void goToRegistro() throws IOException, ToastException {
        Calendar cal = Calendar.getInstance();
        MonthFactory f = new MonthFactory();
        Date d = new Date();
        Month m;
        cal.setTime(d);
        m = f.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

        String materia = this.professor.getMatter().get(0);
        String classe = this.professor.getClassi().get(0);

        ProfessorRegister register = chp.getFullRegister(classe, m, materia);
        this.setRegister(register);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/profRegistro.fxml"));
        AnchorPane pane = loader.load();
        root.getChildren().setAll(pane);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.professor = this.getCurrentProfessor();

        try {
            this.professor = this.chp.full(this.professor);
        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        List<HomeworkBean> homeworks = this.professor.getHomework();
        this.areaCompiti.setText("");
        for(HomeworkBean homework : homeworks) {
            this.areaCompiti.appendText(homework.getMyclasse() + " " + homework.getData() + " " + homework.getMateria() + "\n");
            this.areaCompiti.appendText(homework.getDescription());
            this.areaCompiti.appendText("\n \n");
        }
        for(String materia : this.professor.getMatter()){
            this.comboSubject.getItems().add(materia);
        }
        for(String classi : this.professor.getClassi()){
            this.comboClasse.getItems().add(classi);
        }


        List<ScheduleInfo> schedule = this.professor.getSchedule();

        TableColumn giornoCol = new TableColumn("GIORNO");
        giornoCol.setCellValueFactory(new PropertyValueFactory<>("day"));
        TableColumn oraCol = new TableColumn("ORA");
        oraCol.setCellValueFactory(new PropertyValueFactory<>("hours"));
        TableColumn materiaCol = new TableColumn("MATERIA");
        materiaCol.setCellValueFactory(new PropertyValueFactory<>("materia"));
        TableColumn classeCol = new TableColumn("CLASSE");
        classeCol.setCellValueFactory(new PropertyValueFactory<>("classe"));

        tableSchedule.getColumns().clear();
        tableSchedule.getColumns().addAll(giornoCol , oraCol , materiaCol , classeCol);

        ObservableList<OrariInfo> values = FXCollections.observableArrayList(); //ScheduleInfo va cambiata con OrariInfo
        for(ScheduleInfo campo : schedule){
            OrariInfo orariInfo = new OrariInfo(campo);
            values.add(orariInfo);
        }
        tableSchedule.setItems(values);

        this.textAreaArgomenti.setText("");
        int numberLesson = 1;
        for(Argument argoment :this.professor.getArguments()){
            this.textAreaArgomenti.appendText("Lezione " + numberLesson +":\n");
            this.textAreaArgomenti.appendText(argoment.getDescprition() + "\n");
        }
    }

    public void logout() throws IOException {
        this.professor = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/login.fxml"));
        AnchorPane pane = loader.load();
        root.getChildren().setAll(pane);
    }
}
