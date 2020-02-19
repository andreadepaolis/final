package controllerFX;

import bean.GradesPageBean;
import bean.MatterBean;
import bean.StudentBean;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import model.Grades;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGradesStudentFX extends ControllerScenes implements Initializable {

    public VBox vBoxMateria;
    public TextArea textAreaVoti;
    public TextArea textAreaReport;
    private StudentBean student;
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
    }

    public void goToHome(ActionEvent actionEvent) {
    }

    public void goToLogout(ActionEvent actionEvent) {
    }

    public void goToAbsences(ActionEvent actionEvent) {
    }
}
