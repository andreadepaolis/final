import controllerFX.ControllerScenes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*ControllerScenes c = new ControllerScenes(primaryStage);
        c.changeScene("viewFX/professorHome.fxml");*/
        Parent root = FXMLLoader.load(getClass().getResource("viewFX/login.fxml"));
        primaryStage.setTitle("Registro");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
