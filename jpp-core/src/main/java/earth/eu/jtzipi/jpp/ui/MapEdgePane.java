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
import earth.eu.jtzipi.jpp.ui.tile.Position2D;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;


public class MapEdgePane extends Pane {

    private MapGeoPropVO geoPropVO;     // geo prop vo
    private Position2D p2D;             // position

    /**
     * @param mapProp
     */
    MapEdgePane( final MapGeoPropVO mapProp, final Position2D pos2D ) {
        this.geoPropVO = mapProp;
        this.p2D = pos2D;
        init();
        create();
    }

    public static final MapEdgePane westEdge( final MapGeoPropVO mapProp ) {

        return new MapEdgePane( mapProp, Position2D.W );
    }

    public static final MapEdgePane northEdge( final MapGeoPropVO mapProp ) {

        return new MapEdgePane( mapProp, Position2D.N );
    }

    private void init() {
        DoubleProperty tw = MapPropertiesFX.FX_TILE_WIDTH_PROP;
        switch ( p2D ) {

            /** Edge on west. Bind width with tile width. Bind height with geo prop height. */
            case W:
                this.prefHeightProperty().bind( geoPropVO.fxHeightBinding() );
                this.prefWidthProperty().bind( tw );
                geoPropVO.fxDimYProp().addListener( iv -> create() );

                break;
            /** Edge on north. Bind width with geo width. Bind height with tile width. */
            case N:
                this.prefHeightProperty().bind( tw );
                this.prefWidthProperty().bind( geoPropVO.fxWidthBinding() );
                geoPropVO.fxDimXProp().addListener( iv -> create() );

        }
        // on tile width change
        tw.addListener( iv -> create() );


    }

    private void create() {
        this.getChildren().clear();
        int t;

        switch ( p2D ) {
            case W:
                t = geoPropVO.fxDimYProp().intValue();
                break;
            case N:
                t = geoPropVO.fxDimXProp().intValue();
                break;
            default:
                throw new IllegalArgumentException( "Wrong Position " + p2D );
        }

        for ( int i = 0; i < t; i++ ) {
            getChildren().add( EdgeTile.of( p2D, i ) );
        }

    }
}
