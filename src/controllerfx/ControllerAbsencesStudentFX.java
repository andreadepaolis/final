package controllerfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Absences;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAbsencesStudentFX extends ControllerScenes implements Initializable {
    @FXML
    private VBox containerAssenze;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.student.getAbsences() != null) {
            for (Absences absences : this.student.getAbsences()) {
                AnchorPane ancora = new AnchorPane();
                Label labelData = new Label("Data: " + absences.getData());
                Label labelTipo = new Label("Tipo: " + absences.getTipo());
                ancora.getChildren().add(labelData);
                ancora.getChildren().add(labelTipo);
                containerAssenze.getChildren().add(ancora);
            }
        }
    }
}
