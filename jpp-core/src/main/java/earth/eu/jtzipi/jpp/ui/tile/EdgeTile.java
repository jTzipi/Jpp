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

package earth.eu.jtzipi.jpp.ui.tile;


import earth.eu.jtzipi.jpp.ui.PropertiesFX;
import earth.eu.jtzipi.jpp.ui.tile.segment.Wall;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;


public class EdgeTile extends Region {

    Position2D p2D;
    int idx;

    BooleanProperty fxVisibleProp = new SimpleBooleanProperty( this, "", false );

    Shape pwn;
    Shape pwe;
    Shape pww;
    Shape pws;

    BooleanBinding mouseOverBind;

    /**
     *
     * @param position2D
     * @param index
     */
    EdgeTile(  final Position2D position2D, final int index ) {
        this.p2D = position2D;
        this.idx = index;

        init();
        create();
    }

    public static EdgeTile of( Position2D pos2D, final int index )  {

        return new EdgeTile( pos2D, index );
    }

    private void init() {
        DoubleProperty tw = PropertiesFX.FX_WIDTH_PROP;

        prefWidthProperty().bind( tw );
        prefHeightProperty().bind( tw );

        tw.addListener( iv -> create() );

        BooleanBinding mouseOverBind = null;

        switch ( p2D ) {
            case E:
            case W: layoutYProperty().bind( tw.multiply( idx + 1 ).add( PropertiesFX.FX_GAP_EDGE_WEST_PROP ) );
                mouseOverBind = PropertiesFX.FX_MOUSE_Y_PROP.isEqualTo( idx + 1 );
            break;
            case S:
            case N: layoutXProperty().bind( tw.multiply( idx +1 ).add( PropertiesFX.FX_GAP_EDGE_NORTH_PROP ) );
            mouseOverBind = PropertiesFX.FX_MOUSE_X_PROP.isEqualTo( idx +1);
            break;
        }


    }

    /**
     *
     * @return
     */
    public final BooleanProperty getVisiblePropFX() {
        return fxVisibleProp;
    }

    private void create() {

        Wall w = Wall.solid();
        Wall we = Wall.empty();


        switch ( p2D ) {
            case E:
            case W:
                 pwn = w.toPath( Position2D.N );
                 pwe = w.toPath( Position2D.E );
                 pww = we.toPath( Position2D.W );
                 pws = w.toPath( Position2D.S );
                break;
            case S:
            case N:
                 pwn = we.toPath( Position2D.N );
                 pwe = w.toPath( Position2D.E );
                 pww = w.toPath( Position2D.W );
                 pws = w.toPath( Position2D.S );break;
        }

        getChildren().setAll( pwe, pww, pws, pwn );
    }
}
