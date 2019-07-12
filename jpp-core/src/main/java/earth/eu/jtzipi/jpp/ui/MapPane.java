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


import earth.eu.jtzipi.jpp.map.IPenAndPaperMap;
import earth.eu.jtzipi.jpp.ui.tile.EdgeTile;
import earth.eu.jtzipi.jpp.ui.tile.Position2D;
import earth.eu.jtzipi.jpp.ui.tile.Tile;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;



/**
 * Pane for displaying a single map.
 */
public class MapPane extends Pane {

    /** map .*/
    // Pane tileP;
    /**
     * content of map.
     */
    IPenAndPaperMap pplm;


    DoubleBinding fxWidthBinding;

    DoubleBinding fxHeightBinding;

    /**
     * MapPanel.
     *
     * @param penAndPaperLevelMap map to draw
     */
    MapPane( IPenAndPaperMap penAndPaperLevelMap ) {
        this.pplm = penAndPaperLevelMap;

        init();
        createMapPane();
    }


    private void init() {
        final DoubleBinding offset = MapPropertiesFX.FX_TILE_OFFSET_BIND;
        final DoubleProperty gapNorth = MapPropertiesFX.FX_GAP_EDGE_NORTH_PROP;
        final DoubleProperty gapLeft = MapPropertiesFX.FX_GAP_EDGE_WEST_PROP;
        final DoubleProperty tw = MapPropertiesFX.FX_WIDTH_PROP;
        // set half of tile width gap
        //fxLeftGapBinding = tw.multiply( 0.5D );
        //fxTopGapBinding = tw.multiply( 0.5D );

        // width = rows * tile size + offset + left gap
        fxWidthBinding = tw.multiply( pplm.getDimX() ).add( offset ).add( gapNorth );
        // height = rows * tile size + offset + top gap
        fxHeightBinding = tw.multiply( pplm.getDimY() ).add( offset ).add( gapLeft );
        // bind width and height
        prefHeightProperty().bind( fxHeightBinding );
        prefWidthProperty().bind( fxWidthBinding );

        System.out.println( "Pref W/H " + fxWidthBinding.get() + " . " + fxHeightBinding.get() );
    }


    private void createMapPane() {


        for ( int i = 0; i < pplm.getDimX(); i++ ) {
            getChildren().add( EdgeTile.of( Position2D.N, i ) );
        }

        for ( int iy = 0; iy < pplm.getDimY(); iy++ ) {
            getChildren().add( EdgeTile.of( Position2D.W, iy ) );
        }
        // all tile dim x
        for ( int i = 0; i < pplm.getDimX(); i++ ) {

            // all tile dim y
            for ( int j = 0; j < pplm.getDimY(); j++ ) {
//                // add edge west
//                if( j == i ) {
//                    EdgeTile edge = EdgeTile.of( Position2D.W, j );
//                    getChildren().add( edge );
//
//                }
                // new tile pane
                Tile tile = Tile.solid( i, j );


                getChildren().add( tile );
            }

        }

    }

}
