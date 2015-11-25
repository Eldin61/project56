package Data_Project;

import javafx.fxml.FXML;

/**
 * Created by Indi on 5/27/2015.
 */
public class ZTemplateWindowController {

    @FXML
    public void Back() {
        fxmlController logout = new fxmlController();
        logout.setLogin("Log In", "/ApplicationMenuWindow.fxml");
    }
}
