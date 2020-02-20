package controllerfx;

import bean.GradesPageBean;
import bean.MatterBean;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Grades;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGradesStudentFX extends ControllerScenes implements Initializable {
    @FXML
    private Button currentPage;
    @FXML
    private VBox vBoxMateria;
    @FXML
    private TextArea textAreaVoti;
    @FXML
    private TextArea textAreaReport;
    @FXML
    private AnchorPane rootGradesStudent;
    private GradesPageBean pageGrades;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        student = this.getCurrentStudent();
        pageGrades = this.getCurrentGradesPage();

        for(MatterBean materia : this.pageGrades.getMatter()){
            Button btn = new Button(materia.getMateria());
            btn.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    MatterBean matter = null;
                    for (MatterBean m : pageGrades.getMatter()) {
                        if (m.getMateria().equals(btn.getText()))
                            matter = m;
                    }
                    pageGrades.setCurrentMatter(matter);
                    textAreaVoti.setText("");
                    for (Grades g : pageGrades.getCurrentMatter().getGradesForMatter()) {
                        textAreaVoti.appendText("Mark: " + g.getVoto() + "\n");
                        textAreaVoti.appendText("Type: " + g.getTipo() + "\n");
                        textAreaVoti.appendText("Date: " + g.getData() + "\n\n");
                    }
                }
            });
            vBoxMateria.getChildren().add(btn);
        }
        this.textAreaReport.setText("");
        for(MatterBean m : this.pageGrades.getMatter()){
            this.textAreaReport.appendText(m.getMateria()+": " + m.getMedia() + "\n");
        }
        this.currentPage.setStyle("-fx-border-color: green;");
    }

    public void goToHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/homeStudent.fxml"));
        AnchorPane pane = loader.load();
        rootGradesStudent.getChildren().setAll(pane);
    }

    public void goToLogout() throws IOException {
        this.setStudent(null);
        this.setGradesStudent(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/login.fxml"));
        AnchorPane pane = loader.load();
        rootGradesStudent.getChildren().setAll(pane);
    }

    public void goToAbsences() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/absencesStudent.fxml"));
        AnchorPane pane = loader.load();
        rootGradesStudent.getChildren().setAll(pane);
    }
}
