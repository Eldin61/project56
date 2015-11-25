package Data_Project;

import javafx.fxml.FXML;


public class ApplicationMenuController  {

    @FXML
    public void Jsoup() {
        fxmlController logout = new fxmlController();
        logout.setLogin("Jsoup webcrawler", "/JsoupWindow.fxml");
    }

}
