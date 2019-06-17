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

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import earth.eu.jtzipi.jpp.ui.map.PenAndPaperLevelMap;
import earth.eu.jtzipi.jpp.ui.tile.TileProperties;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author jTzipi
 */
public final class MainPane extends Parent {

    private static final Logger LOG = LoggerFactory.getLogger( "MainPane" );
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

        DoubleProperty tw = TileProperties.widthPropertyFX();
        // main pane
        mainPane = new BorderPane();

        //tileLenSpin = new Spinner<>( TileProperties.MIN_LEN_TILE, TileProperties.MAX_LEN_TILE, TileProperties.PREF_LEN_TILE );

        //tileLenSl.setMajorTickUnit( 10D );

        //TileProperties.widthPropertyFX().bind( tileLenSpin.valueProperty() );

        //
        ToolBar toolBar = new ToolBar(  );

        MaterialDesignIcon plusIcon = MaterialDesignIcon.PLUS_CIRCLE_OUTLINE;
        MaterialDesignIcon minusIcon = MaterialDesignIcon.MINUS_CIRCLE_OUTLINE;
        MaterialDesignIconView plus = new MaterialDesignIconView(plusIcon);



        MaterialDesignIconView mi = new MaterialDesignIconView(minusIcon);
        plus.setGlyphSize( 29D );
        mi.setGlyphSize( 29D );

        plus.setOnMouseClicked( me -> tw.setValue( tw.getValue() + 2D ) );
        mi.setOnMouseClicked( me -> tw.setValue( tw.getValue() -2D ) );

        Label tileSizeLab = new Label();
        tileSizeLab.setFont( Font.font(29D) );

        tileSizeLab.textProperty().bind( tw.asString() );

        toolBar.getItems().setAll( plus, mi, tileSizeLab );

        // TileProperties.setLength( 70D );

        PenAndPaperLevelMap pplm = PenAndPaperLevelMap.of( 3, 4, 0, "Gysi" );
        MapPane mapP = new MapPane( pplm );

        mainPane.setCenter( mapP );

        mainPane.setTop( toolBar );

        // getChildren().setAll(  t1, t );

        getChildren().setAll( mainPane );
    }
}
