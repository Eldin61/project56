package Data_Project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class ApplicationMenuController  {

    @FXML
    public void Login() {
        fxmlController logout = new fxmlController();
        logout.setLogin("Main Menu", "/MainMenuWindowTEMPNAME.fxml");
    }

    @FXML
    public void CreateAccount() {
        fxmlController logout = new fxmlController();
        logout.setLogin("New Account", "/NewAccountWindow.fxml");
    }
}
