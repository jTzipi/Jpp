package earth.eu.jtzipi.jpp;


import earth.eu.jtzipi.jpp.ui.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class.
 * @author jTzipi
 */
public final class JPP extends Application {

    @Override
    public void start( Stage priStage )  {


        Scene scene = new Scene( MainPane.create(), 750, 750);

        priStage.setTitle("Java Pen and Paper World!");
        priStage.setScene(scene);
        priStage.show();
    }

    /**
     * JML start.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
