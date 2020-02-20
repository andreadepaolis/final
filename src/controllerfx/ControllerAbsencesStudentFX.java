package controllerfx;

import bean.StudentBean;
import controller.ControllerStudent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Absences;
import utils.ToastException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAbsencesStudentFX extends ControllerScenes implements Initializable {
    @FXML
    private Label labelTitolo;
    @FXML
    private VBox containerAssenze;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StudentBean studente = this.getCurrentStudent();
        System.out.println(studente.getName());
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
    }
}
