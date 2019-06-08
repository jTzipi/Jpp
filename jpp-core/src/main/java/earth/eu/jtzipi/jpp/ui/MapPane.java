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

import earth.eu.jtzipi.jpp.ui.tile.ITile;
import earth.eu.jtzipi.jpp.ui.tile.Tile;
import earth.eu.jtzipi.jpp.ui.tile.TileProperties;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.*;
import org.controlsfx.control.GridView;

import java.util.stream.IntStream;

/**
 * Pane for displaying a single map.
 */
public class MapPane extends BorderPane {



    int row;
    int column;
    Pane tileP;

    int gapVert;
    int gapHori;


    DoubleBinding fxTopGapBinding;

    DoubleBinding fxLeftGapBinding;

    DoubleBinding fxWidthBinding;

    DoubleBinding fxHeightBinding;

    /**
     * MapPanel.
     * @param rows
     * @param columns
     */
     MapPane( final int rows, final int columns ) {
        this.row = rows;
        this.column = columns;

        init();
        createMapPane();
    }




    private void init() {

        final DoubleProperty tw = TileProperties.widthPropertyFX();
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
        tileP = new TilePane();
        final DoubleProperty tw = TileProperties.widthPropertyFX();
        // test
        // IntStream.range(0, 100).mapToObj(  )

        // double ws = Math.round( TileProperties.SEGMENT_WIDTH.doubleValue());

        for( int i = 0; i < row; i++ ) {

            for( int j = 0; j < column; j++ ) {

                Tile tile = Tile.solid( i, j );

                tile.setLayoutX( fxLeftGapBinding.doubleValue() + ( i * tw.getValue() )  );
                tile.setLayoutY( fxTopGapBinding.doubleValue() + ( j * tw.getValue())  );

                getChildren().add(tile);
            }
        }
    }

}
