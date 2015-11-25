package Data_Project;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * Created by Indi on 5/26/2015.
 *
 * Sample code from http://www.mkyong.com/java/jsoup-html-parser-hello-world-examples/
 *
 */
public class JsoupWindowController {
    public TextArea outputarea; //FXML textarea
    Document doc; // Jsoup document
    String title; // page title

    @FXML
    public void Back() {
        fxmlController logout = new fxmlController();
        logout.setLogin("Application Menu", "/ApplicationMenuWindow.fxml");
    }

    @FXML
    public void Google() throws IOException {
        try {
            ///////////////////////////
            // Google Jsoup connection
            // need http protocol
            doc = Jsoup.connect("http://google.com").get();
            // get page title
            title = doc.title();
            outputarea.appendText("title : " + title);
            // get all links
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                /////////////////
                // output  JFX textarea
                outputarea.appendText("\nlink : " + link.attr("href"));
                outputarea.appendText("text : " + link.text());
                ///////////////////////////////
            }
        } catch (IOException e) {
            e.printStackTrace();
            outputarea.appendText("Error");
        }

    }
}
