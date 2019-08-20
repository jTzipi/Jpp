/*
 *    Copyright 2019 (c) Tim Langhammer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package earth.eu.jtzipi.jpp;


import earth.eu.jtzipi.jpp.ui.MainPane;
import earth.eu.jtzipi.jpp.ui.MapPropertiesFX;
import earth.eu.jtzipi.jpp.ui.PenAndPaperPropertiesFX;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Main class.
 * @author jTzipi
 */
public final class JPP extends Application {


    private static final Logger LOGGER = LoggerFactory.getLogger( JPP.class );



    @Override
    public void start( Stage priStage )  {


        InitStage istage = new InitStage();
        Task<Void> iniTask = initTask();
        iniTask.setOnSucceeded( event -> {
            istage.stage.close();
            priStage.show();
        } );
        try {
            istage.init();

        } catch ( IOException ioE ) {
            LOGGER.error( "Fehler bei Init Stage", ioE );
            System.exit( 1 );
        }

        new Thread( iniTask ).start();
        istage.pb.progressProperty().bind( iniTask.progressProperty() );
        istage.infoLab.textProperty().bind( iniTask.messageProperty() );
        istage.start();






        Scene scene = new Scene( MainPane.create(), PenAndPaperPropertiesFX.WINDOW_WIDTH, PenAndPaperPropertiesFX.WINDOW_HEIGHT);

        scene.setOnKeyTyped( ke -> MapPropertiesFX.FX_KEY_EVENT_PROP.setValue( ke ) );

        PenAndPaperPropertiesFX.WINDOW_HEIGHT_PROP_FX.bind( scene.heightProperty() );
        PenAndPaperPropertiesFX.WINDOW_WIDTH_PROP_FX.bind( scene.widthProperty() );

        priStage.setTitle("Java Pen and Paper World!");
        priStage.setScene(scene);

    }


    private void onInitComplete() {

    }
    /**
     * JML start.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Task<Void> initTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                // load all
                // 1.

                // 2. fonts
                Resources.putAllFonts( IO.loadAllFont( IO.PATH_TO_RES.resolve( "font" ) ) );
                updateMessage( "Font loaded" );
                // 3.
                // a) background

                // b) textures

                return null;
            }
        };
    }

    private static class InitStage {

        private static final double W = 500D;
        private static final double H = 500D;

        Stage stage;
        ImageView iv;
        ProgressBar pb;
        Label infoLab;

        private InitStage() {

            Pane ip = new Pane();
            pb = new ProgressBar();
            infoLab = new Label();
            Scene scene = new Scene( ip, W, H );
            stage = new Stage();
            stage.setScene( scene );

        }

        private Label getInfoLab() {
            return infoLab;
        }

        private ProgressBar getProg() {
            return pb;
        }

        private void start() {
            stage.show();
        }

        private void init() throws IOException {

            //Image splashImage = IO.loadImageFromRes( Paths.get( "SplashImg.png" ) );
            //this.iv = new ImageView( splashImage );

        }
    }
}
