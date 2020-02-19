package controllerFX;

import bean.ProfessorBean;
import bean.StudentBean;
import controller.ControllerHomeProfessor;
import database.ProfessorDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Absences;
import model.Grades;
import register.ProfessorRegister;
import utils.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControllerProfessorRegister extends ControllerScenes implements Initializable {

    @FXML
    public AnchorPane rootProfRegistro;
    public TableView tableVoti;
    public ComboBox comboClasse;
    public ComboBox comboMateria;
    public Button prevMese;
    public Button nextMese;
    public Label curMese;
    public Button buttToday;
    public ComboBox votoStudente;
    public ComboBox votoMateria;
    public ComboBox votoTipologia;
    public DatePicker votoData;
    public Button votoConferma;
    public ComboBox assenzaStudente;
    public ComboBox assenzaTipologia;
    public DatePicker assenzaData;
    public Button assenzaConferma;
    public Label labelName;
    public TextField textVoto;

    private ProfessorRegister registro;
    private ProfessorBean professor;
    private String today;
    private Month pr;
    private Month sx;


    public ControllerProfessorRegister(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.registro = this.getCurrentRegister();
        this.professor = this.getCurrentProfessor();


        Month m = this.registro.getCurrentMonth();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.today = dateFormat.format(new Date());

        MonthFactory mf = new MonthFactory();
        if(m.getIndex() == 1) {
            this.pr = mf.createMonth(12,m.getYear()-1);
        } else {
            this.pr = mf.createMonth(m.getIndex()-1,m.getYear());
        }
        if(m.getIndex() == 12){
            this.sx = mf.createMonth(1,m.getYear()+1);
        } else {
            this.sx = mf.createMonth(m.getIndex()+1,m.getYear());
        }
        String materia = this.registro.getCurrentMatter();

        for(String i : this.professor.getMatter()){
            this.comboMateria.getItems().add(i);
            this.votoMateria.getItems().add(i);
        }
        this.comboMateria.getSelectionModel().selectFirst();
        this.votoMateria.getSelectionModel().selectFirst();
        for(String i : this.professor.getClassi()){
            this.comboClasse.getItems().add(i);
        }
        this.comboClasse.getSelectionModel().select(this.registro.getCurrentClass());
        for(StudentBean i : this.registro.getStudents()){
            this.votoStudente.getItems().add(i);
            this.assenzaStudente.getItems().add(i);
        }
        this.votoStudente.getSelectionModel().selectFirst();
        this.assenzaStudente.getSelectionModel().selectFirst();
        this.votoTipologia.getItems().add("orale");
        this.votoTipologia.getItems().add("scritto");
        this.votoTipologia.getItems().add("laboratorio");
        this.assenzaTipologia.getItems().add("ritardo");
        this.assenzaTipologia.getItems().add("assenza");
        this.assenzaTipologia.getSelectionModel().selectFirst();
        this.votoTipologia.getSelectionModel().selectFirst();
        this.labelName.setText("Benvenuto Prof. " + this.professor.getLastname());


        //List<ScheduleInfo> schedule = this.professor.getSchedule();

        TableColumn materiaCol = new TableColumn("MATERIA");
        materiaCol.setCellValueFactory(new PropertyValueFactory<>("materia"));
        TableColumn votoCol = new TableColumn("VOTO");
        votoCol.setCellValueFactory(new PropertyValueFactory<>("voto"));
        TableColumn tipologiaCol = new TableColumn("TIPOLOGIA");
        tipologiaCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        TableColumn dataCol = new TableColumn("DATA");
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));

        tableVoti.getColumns().clear();
        tableVoti.getColumns().addAll(materiaCol , votoCol , tipologiaCol , dataCol);

        ObservableList<Grades> values = FXCollections.observableArrayList();
        for(StudentBean u: registro.getUsers()){
            if(u.getGrades()!= null) {
                for (Grades g : u.getGrades()) {
                    values.add(g);
                }
            }
        }
        tableVoti.setItems(values);

        this.curMese.setText(this.registro.getCurrentMonth().getName() + " "+ this.registro.getCurrentMonth().getYear());
        for(StudentBean u: registro.getUsers()){
            //System.out.println(u.getName() + " " + u.getLastname() +":\n");
            if(u.getGrades()!= null) {
                for (Grades g : u.getGrades()) {
                    //System.out.println("   " + g.getNomeProfessore() + " " + g.getData() + " " + g.getVoto() + g.getMateria() + "\n");
                }
            }
        }
        System.out.println(this.registro.getCurrentClass());

    }

    public void addVoto(ActionEvent actionEvent) throws IOException, CustomException, CustomSQLException, ToastException {

            int voto = Integer.parseInt(textVoto.getText());
            String tipo = (String) votoTipologia.getValue();
            String materia = (String) votoMateria.getValue();

            int matricola = ((StudentBean) votoStudente.getValue()).getMatricola();
            int matricolaProfessore = this.professor.getMatricola();
            String nomeProfessore = this.professor.getLastname();
            InputController inp_cnt = new InputController();
            Date d = inp_cnt.converDate(votoData.getValue().toString());

            Grades g = new Grades(matricola, materia, voto , tipo, matricolaProfessore, nomeProfessore, d);
            int result = ProfessorDao.saveGrades(g);

            Calendar cal = GregorianCalendar.getInstance();
            MonthFactory f = new MonthFactory();
            Date giornoD = new Date();
            Month m;
            cal.setTime(giornoD);
            m = f.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

            String materiaProf = this.professor.getMatter().get(0);
            String classeProf = this.professor.getClassi().get(0);

            ControllerHomeProfessor chp = new ControllerHomeProfessor();
            ProfessorRegister register = chp.getFullRegister(classeProf, m, materiaProf);
            this.setRegister(register);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/profRegistro.fxml"));
            AnchorPane pane = loader.load();
            rootProfRegistro.getChildren().setAll(pane);
    }

    public void addAbsence(ActionEvent actionEvent) throws IOException, SQLException, CustomException, ToastException {



            String tipo = String.valueOf(assenzaTipologia.getValue());
            int matricola = ((StudentBean) assenzaStudente.getValue()).getMatricola();
            InputController inp_cnt = new InputController();
            Date d = inp_cnt.converDate(assenzaData.getValue().toString());

            Absences a = new Absences(matricola, tipo, d, 1);

            int result = ProfessorDao.saveAbsence(a);


            Calendar cal = GregorianCalendar.getInstance();
            MonthFactory f = new MonthFactory();
            Date giornoD = new Date();
            Month m;
            cal.setTime(giornoD);
            m = f.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));

            String materiaProf = this.professor.getMatter().get(0);
            String classeProf = this.professor.getClassi().get(0);

            ControllerHomeProfessor chp = new ControllerHomeProfessor();
            ProfessorRegister register = chp.getFullRegister(classeProf, m, materiaProf);
            this.setRegister(register);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/profRegistro.fxml"));
            AnchorPane pane = loader.load();
            rootProfRegistro.getChildren().setAll(pane);
    }

    public void goToPrevMese(ActionEvent actionEvent) throws IOException, ToastException {
        String month = String.valueOf(this.pr.getIndex());
        String year = String.valueOf(this.pr.getYear());
        ControllerHomeProfessor chp = new ControllerHomeProfessor();
        Month m = chp.getMonth(year, month);
        this.registro = chp.getFullRegister(this.registro.getCurrentClass(), m , this.registro.getCurrentMatter());
        this.setRegister(this.registro);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/profRegistro.fxml"));
        AnchorPane pane = loader.load();
        rootProfRegistro.getChildren().setAll(pane);
    }

    public void goToNextMese(ActionEvent actionEvent) throws IOException, ToastException {
        String month = String.valueOf(this.sx.getIndex());
        String year = String.valueOf(this.sx.getYear());
        ControllerHomeProfessor chp = new ControllerHomeProfessor();
        Month m = chp.getMonth(year, month);
        this.registro = chp.getFullRegister(this.registro.getCurrentClass(), m , this.registro.getCurrentMatter());
        this.setRegister(this.registro);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/profRegistro.fxml"));
        AnchorPane pane = loader.load();
        rootProfRegistro.getChildren().setAll(pane);
    }

    public void goToToday(ActionEvent actionEvent) throws IOException {
        Calendar cal = Calendar.getInstance();
        MonthFactory mf = new MonthFactory();
        Month m = mf.createMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        this.registro.setCurrentMonth(m);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/profRegistro.fxml"));
        AnchorPane pane = loader.load();
        rootProfRegistro.getChildren().setAll(pane);
    }

    public void goToHomePage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/professorHome.fxml"));
        AnchorPane pane = loader.load();
        rootProfRegistro.getChildren().setAll(pane);
    }

    public void goToLogout(ActionEvent actionEvent) throws IOException {
        this.setProfessor(null);
        this.setRegister(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/login.fxml"));
        AnchorPane pane = loader.load();
        rootProfRegistro.getChildren().setAll(pane);
    }


    public void cambiaClasse(ActionEvent actionEvent) throws IOException, ToastException {
        String currClasse = (String) comboClasse.getValue();
        ControllerHomeProfessor chp = new ControllerHomeProfessor();
        this.registro = chp.getFullRegister(currClasse, this.registro.getCurrentMonth() , this.registro.getCurrentMatter());
        this.setRegister(this.registro);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/profRegistro.fxml"));
        AnchorPane pane = loader.load();
        rootProfRegistro.getChildren().setAll(pane);
    }

    public void changeMatter(ActionEvent actionEvent) throws IOException, ToastException {
        String currMateria = (String) comboMateria.getValue();
        ControllerHomeProfessor chp = new ControllerHomeProfessor();
        this.registro = chp.getFullRegister(this.registro.getCurrentClass(), this.registro.getCurrentMonth() , currMateria);
        this.setRegister(this.registro);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewFX/profRegistro.fxml"));
        AnchorPane pane = loader.load();
        rootProfRegistro.getChildren().setAll(pane);
    }
}
