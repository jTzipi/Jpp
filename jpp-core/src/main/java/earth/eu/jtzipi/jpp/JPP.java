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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
