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

package earth.eu.jtzipi.jpp.ui;

import earth.eu.jtzipi.jpp.ui.tile.Tile;
import earth.eu.jtzipi.jpp.ui.tile.TileProperties;
import earth.eu.jtzipi.jpp.ui.tile.Tiles;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

import java.util.Map;

/**
 *
 * @author jTzipi
 */
public final class MainPane extends Parent {

    private static final MainPane SINGLETON = new MainPane();

    private Spinner<Double> tileLenSpin;

    private BorderPane mainPane;
    /**
     * MainPane.
     */
    private MainPane() {
    createMainPane();
    }

    /**
     * Single instance of main pane.
     * @return main pane
     */
    public static MainPane create() {
        return SINGLETON;
    }

    private void createMainPane() {

        mainPane = new BorderPane();

        tileLenSpin = new Spinner<>( TileProperties.MIN_LEN_TILE, TileProperties.MAX_LEN_TILE, TileProperties.PREF_LEN_TILE );

        //tileLenSl.setMajorTickUnit( 10D );

        TileProperties.widthPropertyFX().bind( tileLenSpin.valueProperty() );

        ToolBar toolBar = new ToolBar( tileLenSpin );

        // TileProperties.setLength( 70D );

        Tile t1 = Tiles.ofSolidNEWBreakable(0, 0, 0 );
        Tile t = Tiles.ofSolidWallESWDoorBreakableN( 0, 0, 0 );

        t1.setTranslateX( 550D );
        t1.setTranslateY( 100D );

        t.setTranslateX( 550D );
        t.setTranslateY( 200D );


        MapPane mapP = new MapPane( 3, 4 );

        mainPane.setCenter( mapP );

        mainPane.setTop( toolBar );

        // getChildren().setAll(  t1, t );

        getChildren().setAll( mainPane );
    }
}
