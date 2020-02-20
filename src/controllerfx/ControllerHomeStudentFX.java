package controllerfx;

import bean.GradesPageBean;
import bean.StudentBean;
import controller.ControllerHomeStudent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Homework;
import model.ScheduleInfo;
import utils.OrariInfo;
import utils.ToastException;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerHomeStudentFX extends ControllerScenes implements Initializable {
    @FXML
    public Label labelWelcomeStudent;
    protected TextArea tabelHomework;
    protected TableView tableSchedule;
    protected Label labelDataHomework;
    protected AnchorPane rootHomeStudent;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelWelcomeStudent.setText("Benvenuto " + this.getCurrentStudent().getName());
        List<Homework> hmw = this.student.getHomework();
        tabelHomework.setText("");
        if(hmw != null) {
            for (Homework homework : hmw) {
                tabelHomework.appendText(homework.getClasse() + " " + homework.getMatter() + " " + homework.getData() + ":\n");
                tabelHomework.appendText(homework.getDescription() + "\n");
            }
        }

        List<ScheduleInfo> schedule = this.student.getSchedule();

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

        ObservableList<OrariInfo> values = FXCollections.observableArrayList();
        for(ScheduleInfo campo : schedule){
            OrariInfo orariInfo = new OrariInfo(campo);
            values.add(orariInfo);
        }
        tableSchedule.setItems(values);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(this.student.getCurrentDate());
        labelDataHomework.setText(strDate);
    }

    public void goDayBefore() throws ToastException, IOException {
        ControllerHomeStudent chs = new ControllerHomeStudent();
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.student.getCurrentDate());
        cal.add(Calendar.DATE, -1);
        this.student.setCurrentDate(cal.getTime());
        List<Homework> h = chs.scrollHomework(this.student.getClasse(), this.student.getCurrentDate());
        this.student.setHomework(h);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/homeStudent.fxml"));
        AnchorPane pane = loader.load();
        rootHomeStudent.getChildren().setAll(pane);
    }

    public void goToDayAfter() throws ToastException, IOException {
        ControllerHomeStudent chs = new ControllerHomeStudent();
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.student.getCurrentDate());
        cal.add(Calendar.DATE, +1);
        this.student.setCurrentDate(cal.getTime());
        List<Homework> h = chs.scrollHomework(this.student.getClasse(), this.student.getCurrentDate());
        this.student.setHomework(h);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/homeStudent.fxml"));
        AnchorPane pane = loader.load();
        rootHomeStudent.getChildren().setAll(pane);
    }

    public void goToGrades() throws IOException, ToastException {
        ControllerHomeStudent chs = new ControllerHomeStudent();
        GradesPageBean page = chs.fullGradesPage(this.student);
        this.setGradesStudent(page);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/gradesStudent.fxml"));
        AnchorPane pane = loader.load();
        rootHomeStudent.getChildren().setAll(pane);
    }

    public void goToAbsences() throws ToastException, IOException {
        ControllerHomeStudent chs = new ControllerHomeStudent();
        GradesPageBean page = chs.fullGradesPage(this.student);
        StudentBean studente = chs.full(this.student);
        this.setStudent(studente);
        this.setGradesStudent(page);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/absencesStudent.fxml"));
        AnchorPane pane = loader.load();
        rootHomeStudent.getChildren().setAll(pane);
    }

    public void logout() throws IOException {
        this.setStudent(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/login.fxml"));
        AnchorPane pane = loader.load();
        rootHomeStudent.getChildren().setAll(pane);
    }
}
