package controllerfx;

import bean.GradesPageBean;
import bean.StudentBean;
import controller.ControllerHomeStudent;
import controller.ControllerStudent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Absences;
import utils.ToastException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAbsencesStudentFX extends ControllerScenes implements Initializable {
    @FXML
    private Button currentPage;
    @FXML
    private AnchorPane rootAbsences;
    @FXML
    private Label labelTitolo;
    @FXML
    private VBox containerAssenze;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StudentBean studente = this.getCurrentStudent();
        ControllerStudent cs = new ControllerStudent();
        try {
            List<Absences> list = cs.loadAbsences(studente.getMatricola());
            studente.setAbsences(list);
        } catch (ToastException e) {
            labelTitolo.setText(e.getTitle() + e.getMessage());
        }

        if(studente.getAbsences() != null) {
            for (Absences absences : this.student.getAbsences()) {
                HBox hbox = new HBox();
                hbox.setPadding(new Insets(15, 12, 15, 12));
                Label labelData = new Label("Data: " + absences.getData());
                Label labelTipo = new Label("                    Tipo: " + absences.getTipo());
                hbox.getChildren().add(labelData);
                hbox.getChildren().add(labelTipo);
                containerAssenze.getChildren().add(hbox);
            }
        }
        this.currentPage.setStyle("-fx-border-color: green;");
    }

    public void goToLogout() throws IOException {
        this.setStudent(null);
        this.setGradesStudent(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/login.fxml"));
        AnchorPane pane = loader.load();
        rootAbsences.getChildren().setAll(pane);
    }

    public void goToGrades() throws ToastException, IOException {
        ControllerHomeStudent chs = new ControllerHomeStudent();
        GradesPageBean page = chs.fullGradesPage(this.student);
        this.setGradesStudent(page);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/gradesStudent.fxml"));
        AnchorPane pane = loader.load();
        rootAbsences.getChildren().setAll(pane);
    }

    public void goToHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/homeStudent.fxml"));
        AnchorPane pane = loader.load();
        rootAbsences.getChildren().setAll(pane);
    }
}
