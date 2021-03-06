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


import earth.eu.jtzipi.jpp.ui.tile.EdgeTile;
import earth.eu.jtzipi.jpp.ui.tile.PenAndPaperPos;
import earth.eu.jtzipi.jpp.ui.tile.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Pane for displaying a single map.
 */
public class MapPane extends Pane {

    private static final Logger LOG = LoggerFactory.getLogger( "MapPane" );
    /**
     * Map geo properties .
     */
    private MapGeoPropVO geoPropVO;

    /**
     * MapPanel.
     *
     * @param mapGeoProp map prop
     */
    MapPane( final MapGeoPropVO mapGeoProp ) {
        this.geoPropVO = mapGeoProp;

        init();
        createMapPane();
    }

    /**
     * Init this pane.
     */
    private void init() {
        // bind width and height
        prefHeightProperty().bind( geoPropVO.fxHeightBinding() );
        prefWidthProperty().bind( geoPropVO.fxWidthBinding() );
        // on change
        geoPropVO.fxDimXProp().addListener( iv -> createMapPane() );
        geoPropVO.fxDimYProp().addListener( iv -> createMapPane() );

        //boundsInParentProperty().addListener( iv -> System.out.println( iv ) );
        //boundsInLocalProperty().addListener( iv -> System.out.println( iv ) );

        setOnKeyTyped( ke -> LOG.error( "drücke '" + ke + "'" ) );
    }

    /**
     * Create this pane.
     */
    private void createMapPane() {
        getChildren().setAll();

        int xt = geoPropVO.fxDimXProp().intValue();
        int yt = geoPropVO.fxDimYProp().intValue();

        for ( int i = 0; i < xt; i++ ) {
            getChildren().add( EdgeTile.of( PenAndPaperPos.N, i ) );
        }

        for ( int iy = 0; iy < yt; iy++ ) {
            getChildren().add( EdgeTile.of( PenAndPaperPos.W, iy ) );
        }
        // all tile dim x
        for ( int i = 0; i < xt; i++ ) {

            // all tile dim y
            for ( int j = 0; j < yt; j++ ) {

                getChildren().add( Tile.of( i, j ) );
            }

        }
        Text info = new Text();

        info.layoutXProperty().bind( prefWidthProperty().subtract( 10 ) );
        info.layoutYProperty().bind( prefHeightProperty().add( 27 ) );
        info.setText( "XY [" + prefWidthProperty().doubleValue() + ", " + prefHeightProperty().doubleValue() + "]" );

        getChildren().add( info );
    }


}
