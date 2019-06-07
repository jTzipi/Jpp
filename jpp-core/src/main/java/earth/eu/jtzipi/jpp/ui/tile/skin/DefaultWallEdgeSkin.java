/*
 * Copyright (c) 2019 Tim Langhammer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package earth.eu.jtzipi.jpp.ui.tile.skin;


import earth.eu.jtzipi.jpp.ui.map.MapEdge;
import earth.eu.jtzipi.jpp.ui.tile.TileProperties;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Objects;


/**
 * Default skin for a tile.
 */
public class DefaultWallEdgeSkin extends AbstractWallEdgeSkin {


    /** Graphics Context. */
    GraphicsContext gc;
    /** Canvas . */
    Canvas canvas;


    private DoubleProperty fxWidthProp = new SimpleDoubleProperty(this, "", TileProperties.getLength() );

    private NumberBinding edgeLenBind;

    /**
     *
     * @param mapEdge
     */
    DefaultWallEdgeSkin( final MapEdge mapEdge ) {
        super( mapEdge );

    }



    public static DefaultWallEdgeSkin of( final MapEdge mapEdge ) {
        Objects.requireNonNull( mapEdge );
        DefaultWallEdgeSkin dts = new DefaultWallEdgeSkin( mapEdge );
        dts.init();
        dts.drawTile();
        return dts;
    }

    @Override
    protected void init() {
        MapEdge mapEdge = getSkinnable();
        DoubleProperty tileWProp = TileProperties.widthPropertyFX();


        switch ( mapEdge.getPosition() ) {
            case E:
            case W: edgeLenBind = TileProperties.FX_GAP_WEST_PROP.add( mapEdge.getTilesPerEdge() * TileProperties.getLength() );
        }

        canvas = new Canvas(getWidth(), getHeight());
        gc = canvas.getGraphicsContext2D();

    }

    @Override
    protected void resize() {


    }

    @Override
    protected void redraw() {

    }

    private void drawTile() {

        Pane pane = new Pane(  );
       // Tile t = getSkinnable();



        gc.setFill( Color.FORESTGREEN );
        // set background
        gc.fillRect( 0D, 0D, w, h );

        gc.setFill( Color.BLACK );
        //Tile.WallSegments wallw = t.getWallMap().get( Dir2D.WEST );
        // line width
        double ws = w / 10D;


        gc.setLineWidth( ws );

        gc.strokeLine( 0D, 0D, w, 0D );

        gc.translate( 0D, h );


        /*
        gc.beginPath();
        gc.moveTo( 0D, 0D );

        // west segment
        gc.lineTo( 0D,h );
        gc.closePath();
        gc.stroke();

        gc.translate( w - ws, 0 );
        //gc.stroke();

        // east segment
        //gc.lineTo( w, h );
        // south
        //gc.lineTo( w, 0D );
        // west
        //gc.lineTo( 0D, 0D );
    */



        //getChildren().setAll( canvas );


    }


}
