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


import earth.eu.jtzipi.jpp.ui.map.PenAndPaperLevelMap;
import earth.eu.jtzipi.jpp.ui.tile.EdgeTile;
import earth.eu.jtzipi.jpp.ui.tile.Position2D;
import earth.eu.jtzipi.jpp.ui.tile.Tile;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.*;

/**
 * Pane for displaying a single map.
 */
public class MapPane extends Pane {

    /** Show north edge. */
    BooleanProperty fxShowN_EdgeProp = new SimpleBooleanProperty( this, "FX_SHOW_NORTH_EDGE_PROP", false );
    /** Show east edge. */
    BooleanProperty fxShowE_EdgeProp = new SimpleBooleanProperty( this, "FX_SHOW_NORTH_EDGE_PROP", false );

    /** rows of map. */
    int row;
    /** cols of map .*/
    int column;
    /** map .*/
    // Pane tileP;
    /** content of map. */
    PenAndPaperLevelMap pplm;


    DoubleBinding fxTopGapBinding;

    DoubleBinding fxLeftGapBinding;

    DoubleBinding fxWidthBinding;

    DoubleBinding fxHeightBinding;


    /**
     * MapPanel.
     *
     */
     MapPane( PenAndPaperLevelMap penAndPaperLevelMap ) {
        this.pplm = penAndPaperLevelMap;

        init();
        createMapPane();
    }




    private void init() {

        final DoubleProperty tw = PropertiesFX.FX_WIDTH_PROP;
        // set half of tile width gap
        fxLeftGapBinding = tw.multiply( 0.5D );
        fxTopGapBinding = tw.multiply( 0.5D );

        // width = rows * tile size + left gap
        fxWidthBinding = tw.multiply( row ).add( fxLeftGapBinding.doubleValue() );
        // height = rows * tile size + top gap
        fxHeightBinding = tw.multiply( column ).add( fxTopGapBinding.doubleValue() );
        // bind width and height
        prefHeightProperty().bind( fxHeightBinding );
        prefWidthProperty().bind( fxWidthBinding );


    }



    private void createMapPane() {
        // new tile pane


        // test
        // IntStream.range(0, 100).mapToObj(  )

        boolean ee = fxShowE_EdgeProp.getValue();
        boolean en = fxShowN_EdgeProp.getValue();

        // rows
        for( int i = 0; i < pplm.getDimY(); i++ ) {

            getChildren().add( EdgeTile.of( Position2D.W, i ) );

            for( int j = 0; j < pplm.getDimX(); j++ ) {

                Tile tile = Tile.solid( i + 1, j );


                getChildren().add(tile);
            }
        }




    }

}
