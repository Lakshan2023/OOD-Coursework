package LoginDashboardManager;

import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private double xPosition;

    private double yPosition;

    private Scene scene;
    private Stage stage;

    private Parent root;

    @FXML
    private StackPane LoginPane;

    @FXML
    private Circle clubAdvisorCircle;

    @FXML
    private Circle studentCircle;


    // work done by- Lakshan
    @FXML
    void LoginDragDetected(MouseEvent event) {
        Stage stage = (Stage) LoginPane.getScene().getWindow();
        stage.setX(event.getScreenX() - xPosition);
        stage.setY(event.getScreenY() - yPosition);
    }
    
    // work done by- Lakshan
    @FXML
    void LoginPanePressed(MouseEvent event) {
        xPosition = event.getSceneX();
        yPosition = event.getSceneY();
    }

    // work done by- Lakshan and Deelaka
    // Login sequence diagram : 1.1 : DirectClubAdvisor()
    @FXML
    void DirectClubAdvisor(ActionEvent event) throws IOException {
        System.out.println("Direct to club Advisor");
        Parent root = FXMLLoader.load(getClass().getResource("/LoginDashboardManager/ClubAdvisorLogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // work done by- Lakshan and Deelaka
    @FXML
    void DirectStudent(ActionEvent event) throws IOException {
        System.out.println("Direct to student");
        Parent root = FXMLLoader.load(getClass().getResource("/LoginDashboardManager/StudentLogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // work done by- Lakshan
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // work done by- Lakshan
    @FXML
    void ClosePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    // work done by- Lakshan
    @FXML
    void MinimizePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(LoginPane);
    }
}