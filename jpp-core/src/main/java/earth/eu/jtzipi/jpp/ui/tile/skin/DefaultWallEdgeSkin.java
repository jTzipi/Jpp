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
import earth.eu.jtzipi.jpp.util.FXUtils;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Objects;


/**
 * Default skin for a map edge.
 */
public class DefaultWallEdgeSkin extends SkinBase<MapEdge> {


    /** Graphics Context. */
    GraphicsContext gc;
    /** Canvas . */
    Canvas canvas;


    // Length binding
    private NumberBinding edgeLenBind;

    private NumberBinding tileOffsetBind;

    private double w;
    private double h;

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

        // draw edge
        dts.draw();

        return dts;
    }


    protected void init() {
        MapEdge mapEdge = getSkinnable();

        int t = mapEdge.getTilesPerEdge();
        double l = TileProperties.getLength();
        DoubleProperty tileWProp = TileProperties.widthPropertyFX();



        switch ( mapEdge.getPosition() ) {
            case E:
            case W:
                edgeLenBind = TileProperties.FX_GAP_NORTH_PROP.add( tileWProp.multiply( t ) );
                w = l;
                h = edgeLenBind.doubleValue();

                break;
            case S:
            case N:
                edgeLenBind = TileProperties.FX_GAP_WEST_PROP.add( tileWProp.multiply( t ) );
                w = edgeLenBind.doubleValue();
                h = l;
            break;
        }

        this.canvas = new Canvas(w, h);
        this.gc = canvas.getGraphicsContext2D();

        getChildren().add( canvas );
    }

    @Override
    protected double computeMinWidth( double height, double topInset, double rightInset, double bottomInset, double leftInset ) {
        return w;
    }

    @Override
    protected double computeMinHeight( double width, double topInset, double rightInset, double bottomInset, double leftInset ) {
        return h;
    }


    @Override
    protected double computePrefWidth( double height, double topInset, double rightInset, double bottomInset, double leftInset ) {
        return w;
    }

    @Override
    protected double computePrefHeight( double width, double topInset, double rightInset, double bottomInset, double leftInset ) {
        return h;
    }

    protected void resize() {


    }


    protected void redraw() {

    }

    private void draw() {

// Tile t = getSkinnable();

        //




        gc.setFill( Color.FORESTGREEN );
        // set background
        gc.fillRect( 0D, 0D, w, h );

        gc.setLineWidth( TileProperties.SEGMENT_WIDTH.doubleValue() );
        gc.setFill( Color.BLACK );



        MapEdge m = getSkinnable();
        for( int i = 0; i < m.getTilesPerEdge(); i++ ) {

            double y  =  // getSkinnable().getOffsetPropFX().doubleValue() +
                    i * TileProperties.getLength();
            double x = 0D;
            gc.strokeLine( x, y, w,y );
        }

/*
        // line width
        double ws = w / 10D;


        gc.setLineWidth( ws );

        gc.strokeLine( 0D, 0D, w, 0D );

        gc.translate( 0D, h );



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
