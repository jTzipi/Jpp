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


import earth.eu.jtzipi.jpp.ui.MapPropertiesFX;
import earth.eu.jtzipi.jpp.ui.tile.segment.Wall;
import earth.eu.jtzipi.jpp.util.FXUtils;
import javafx.animation.*;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

/**
 * Tile on the edge of map.
 *
 * @author jTzipi
 */
public class EdgeTile extends Region {

    Position2D p2D; // Position
    int idx;        // index of this edge

    Shape pwn;  // path wall north
    Shape pwe;  // path wall east
    Shape pww;  // path wall west
    Shape pws;  // path wall south

    Text tt;    // tile text

    BooleanBinding mouseXOverBind;   // is mouse over a north/south tile belonging to this
    BooleanBinding mouseYOverBind;   // is mouse over a west/east tile

    Transition mouseOverTra;
    Transition bgTra;
    Polygon t;

    ParallelTransition pt;

    Color mouseOverCol = Color.gray( 0.74D );
    Background bgDef = Background.EMPTY;
    Background bgMOver = FXUtils.createColorBG( mouseOverCol );
    /**
     * Edge Tile.
     * @param position2D position
     * @param index index
     */
    EdgeTile( final Position2D position2D, final int index ) {
        this.p2D = position2D;
        this.idx = index;
        this.tt = new Text();
        init();
        if( MapPropertiesFX.FX_SHOW_MAP_EDGE_PROP.get() ) {
            draw();
        }
    }

    /**
     * Return tile for position and index.
     * @param pos2D position
     * @param index index
     * @return
     */
    public static EdgeTile of( Position2D pos2D, final int index )  {

        return new EdgeTile( pos2D, index );
    }

    private void init() {
        // width of tile
        DoubleProperty twProp = MapPropertiesFX.FX_TILE_WIDTH_PROP;
        // bind to this tile
        prefWidthProperty().bind( twProp );
        prefHeightProperty().bind( twProp );
        // on change
        twProp.addListener( iv -> update(  ) );
        MapPropertiesFX.FX_SHOW_MAP_EDGE_PROP.addListener( iv -> update(  ) );
        // DEBUG
        NumberBinding yPosBind =prefHeightProperty().subtract( 12D );

        // Text transform
        ScaleTransition ts = new ScaleTransition( Duration.millis( 170L ), tt );
        ts.setFromX( 1D );
        ts.setFromY( 1D );
        ts.setToX( 7.5D );
        ts.setToY( 7.5D );

        mouseOverTra = ts;


        // layout
        // ------
        // Caution: add + 1 because of corner edge

        switch ( p2D ) {
            // east and west
            case E:
            case W:
                layoutYProperty().bind( twProp.multiply( idx + 1 ).add( MapPropertiesFX.FX_GAP_EDGE_NORTH_PROP ) );
                mouseYOverBind = MapPropertiesFX.FX_MOUSE_Y_PROP.isEqualTo( idx );
                mouseYOverBind.addListener( ( obs, oldVal, newVal ) -> onMouse( newVal ) );
            break;
            // south and north
            case S:
            case N:
                layoutXProperty().bind( twProp.multiply( idx + 1 ).add( MapPropertiesFX.FX_GAP_EDGE_WEST_PROP ) );

                mouseXOverBind = MapPropertiesFX.FX_MOUSE_X_PROP.isEqualTo( idx );
                mouseXOverBind.addListener( ( obs, oldVal, newVal ) -> onMouse( newVal ) );
            break;

        }


        tt.setText( "" + (idx + 1) );
        tt.xProperty().setValue( 10D );
        tt.yProperty().bind( yPosBind );
    }

    private void update(  ) {
        if( MapPropertiesFX.FX_SHOW_MAP_EDGE_PROP.get() ) {
            draw();
        } else {
            getChildren().setAll(  );
        }
    }

    /**
     * If mouse is over a tile of this edge paint .
     *
     * @param over mouse is over a tile of this edge
     */
    private void onMouse( boolean over ) {
        // if mouse over a cell of this edge
        if ( over ) {
            setBackground( bgMOver );
            mouseOverTra.setRate( 1D );
            mouseOverTra.play();
            //setBackground( new Background( new BackgroundFill( Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY ) ) );
        } else {
            //setBackground( Background.EMPTY );
            mouseOverTra.setRate( -1D );
            mouseOverTra.play();
            setBackground( bgDef );
        }
    }

    

    private void draw() {

        //TODO : kann statisch
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


        t = new Polygon();
        t.getPoints().addAll( 0D, 0D, 25D, 0D, 12.4D, 25D );

        t.layoutXProperty().bind( prefWidthProperty().divide( 2 ).subtract( 12.4D ) );
        t.layoutYProperty().bind( prefHeightProperty().subtract( 12.4D ) );
        t.setStroke( Color.gray( 0.64D ) );
        t.setFill( Color.gray( 0.7D ) );

        t.setStrokeLineCap( StrokeLineCap.ROUND );
        t.setStrokeLineJoin( StrokeLineJoin.ROUND );
        t.setStrokeWidth( 1.5D );

        getChildren().setAll( pwe, pww, pws, pwn, tt, t );
    }
}
