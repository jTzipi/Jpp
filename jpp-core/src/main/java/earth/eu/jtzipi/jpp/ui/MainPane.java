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

import com.sun.org.apache.xpath.internal.operations.Bool;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import earth.eu.jtzipi.jpp.ui.map.PenAndPaperLevelMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import org.controlsfx.control.ToggleSwitch;


/**
 * Main panel.
 * @author jTzipi
 */
public final class MainPane extends Pane {


    private static final MainPane SINGLETON = new MainPane();



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

        DoubleProperty twProp = PropertiesFX.FX_WIDTH_PROP;
        BooleanProperty edgeProp = PropertiesFX.FX_SHOW_MAP_EDGE_PROP;
    edgeProp.setValue( true );
        // main pane
        mainPane = new BorderPane();
        mainPane.prefWidthProperty().bind( prefWidthProperty() );
        //tileLenSpin = new Spinner<>( TileProperties.MIN_LEN_TILE, TileProperties.MAX_LEN_TILE, TileProperties.PREF_LEN_TILE );

        //tileLenSl.setMajorTickUnit( 10D );

        //TileProperties.widthPropertyFX().bind( tileLenSpin.valueProperty() );

        //
        ToolBar toolBar = new ToolBar(  );

        // increase grid size
        MaterialDesignIcon plusIcon = MaterialDesignIcon.PLUS_CIRCLE_OUTLINE;
        // decrease grid size
        MaterialDesignIcon minusIcon = MaterialDesignIcon.MINUS_CIRCLE_OUTLINE;
        // show edge

        MaterialDesignIcon gridIcon = MaterialDesignIcon.GRID;

        MaterialDesignIcon edgeIcon = MaterialDesignIcon.IMAGE_FILTER_NONE;

        RoundLabel gysi = new RoundLabel();


        //gysi.setFill( new Color( 0D,0D,0D,0D ) );
        //gysi.setStroke( Color.grayRgb( 117 ) );
        //gysi.setStrokeWidth( 1D );

        MaterialDesignIconView plus = new MaterialDesignIconView(plusIcon);
        MaterialDesignIconView mi = new MaterialDesignIconView(minusIcon);
        MaterialDesignIconView grid = new MaterialDesignIconView( gridIcon );
        MaterialDesignIconView edge = new MaterialDesignIconView(edgeIcon);

        plus.setGlyphSize( 29D );
        mi.setGlyphSize( 29D );
        grid.setGlyphSize( 29D );
        edge.setGlyphSize( 29D );

        plus.setOnMouseClicked( me -> twProp.setValue( twProp.getValue() + 2D ) );
        mi.setOnMouseClicked( me -> twProp.setValue( twProp.getValue() -2D ) );

        Label tileSizeLab = new Label();
        tileSizeLab.setFont( Font.font(29D) );

        tileSizeLab.textProperty().bind( twProp.asString() );

        ToggleButton showEdgeTogB = new ToggleButton();
        showEdgeTogB.setGraphic( edge );
        showEdgeTogB.setSelected( true );
        edgeProp.bind( showEdgeTogB.selectedProperty() );
        toolBar.getItems().setAll( grid, gysi,showEdgeTogB, plus, mi, tileSizeLab );

        // TileProperties.setLength( 70D );

        PenAndPaperLevelMap pplm = PenAndPaperLevelMap.of( 3, 4, 0, "Gysi" );
        MapPane mapP = new MapPane( pplm );

        mainPane.setCenter( mapP );

        mainPane.setTop( toolBar );


        // getChildren().setAll(  t1, t );

        getChildren().setAll( mainPane );
    }
}
