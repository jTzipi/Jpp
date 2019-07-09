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


import earth.eu.jtzipi.jpp.cell.IPenAndPaperCell;
import earth.eu.jtzipi.jpp.cell.PenAndPaperCell;
import earth.eu.jtzipi.jpp.ui.MapPropertiesFX;
import earth.eu.jtzipi.jpp.ui.tile.segment.Wall;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static earth.eu.jtzipi.jpp.ui.tile.Position2D.*;

/**
 * A tile is a square on a pan & paper map.
 *
 * @author jTzipi
 */
public class Tile extends Region {



    /*
    public enum Dir2D {
        N,
        E,
        W,
        S;


    } */

    /**
     * Segments .
     */
    /** East Wall.*/


    final IPenAndPaperCell ppc;
    //Pane baseP;
    //Pane aviP;

    /**
     * Neighbour tiles.
     */



    /**
     * Tile complete constructor.
     *
     * @param ppc pen and paper cell

     *
     */
    Tile( IPenAndPaperCell ppc ) {
        this.ppc = ppc;
        //this.y = y;

         // Maup;
        // init code
        create();   // create all parts
        draw();     // draw

        LoggerFactory.getLogger( "Tile" ).error( "Gysi" );
    }


    Tile( int x, int y, int level ) {
        this( PenAndPaperCell.ofEmpty( x, y, level ) );
    }


    public static Tile of( final int x, final int y ) {
        if ( 0 > x || 0 > y ) {


        }


        return new Tile( x, y, IPenAndPaperCell.LEVEL_DEFAULT );
    }

    public static Tile solid( int x, int y ) {




        return new Tile( PenAndPaperCell.ofSolid( x, y, IPenAndPaperCell.LEVEL_DEFAULT ) );
    }



    private void create() {


        //baseP = new Pane();
        //aviP = new Pane();

        // tile width
        final DoubleProperty tw = MapPropertiesFX.FX_WIDTH_PROP;

        // offset x and y


        //System.out.println("Offset " + offset.getValue());
        prefWidthProperty().bind( tw );
        prefHeightProperty().bind( tw );


        //baseP.prefWidthProperty().bind( prefWidthProperty() );
        //baseP.prefHeightProperty().bind( prefHeightProperty() );

        // layout
         NumberBinding layoutX = tw.multiply( ppc.getX() ).add( MapPropertiesFX.FX_GAP_EDGE_WEST_PROP ).add( MapPropertiesFX.FX_TILE_OFFSET_BIND );
         NumberBinding layoutY = tw.multiply( ppc.getY() ).add( MapPropertiesFX.FX_GAP_EDGE_NORTH_PROP ).add( MapPropertiesFX.FX_TILE_OFFSET_BIND );


        layoutXProperty().bind( layoutX );
        layoutYProperty().bind( layoutY );

        tw.addListener( obs -> {
            layoutX.invalidate();
            layoutY.invalidate();
            draw();
        } );

        setOnMouseEntered( me -> System.out.println("tile go")  ); // me -> aviP.setVisible( true )
        setOnMouseExited( me -> System.out.println( "tile off" ) );

        //getChildren().addAll( baseP, aviP );

    }

    private void draw() {
        setWidth( getPrefWidth() );
        setHeight( getPrefHeight() );

        getChildren().setAll( createTile() );


        //setBackground( new Background( new BackgroundFill( Color.TEAL, null, null ) ) );


//        System.out.println( "Width :" + getWidth() );
//        System.out.println( "Height :" +getHeight() );
//        System.out.println( "PH :" + getPrefHeight() );
//        System.out.println( "PW :" + getPrefWidth() );
//        System.out.println( "W :" + MapPropertiesFX.SEGMENT_WIDTH.doubleValue() );
//
        System.out.println( "width :" + layoutXProperty().getValue() + " " + layoutYProperty().getValue() );
         System.out.println( "GADI " );

        createAviPane();
    }

    private void createAviPane() {
    /*
        Wall w = Wall.gate();
        w.getColorPropFX().setValue( Color.RED );

        Shape we = w.toPath( E );
        Shape ww = w.toPath( W );
        Shape ws = w.toPath( S );
        Shape wno = w.toPath( N );

   */  //   aviP.getChildren().setAll( we, ww, ws, wno );
     //   aviP.setVisible( false );

    }

    private Collection<? extends Shape> createTile() {
                // segment size



        List<Shape> segL = new ArrayList<>();

        Shape wW = Wall.ofId( ppc.getIdWallWest() ).toPath( W );
        Shape eW = Wall.ofId( ppc.getIdWallEast() ).toPath( E );
        Shape sW = Wall.ofId( ppc.getIdWallSouth() ).toPath( S );
        Shape nW = Wall.ofId( ppc.getIdWallNorth() ).toPath( N );

        // System.out.println( "Shape " + wW + " " + eW );

        segL.add( eW );
        segL.add( sW );
        segL.add( nW );
        segL.add( wW );

        Text tt = new Text();


        tt.setFont( Font.font( 27D ) );
        tt.setText( ppc.getX() + " " + ppc.getY() );
        tt.layoutXProperty().bind( prefWidthProperty().multiply( 0.1D ) );
        tt.layoutYProperty().bind( prefHeightProperty().subtract( 10D ) );
        segL.add( tt );

        return segL;
    }




//    public static final class Builder implements IBuilder<Tile> {
//
//        // mandatory
//        private final int lx;
//        private final int ly;
//        // optional
//
//        private int level;
//
//
//        Builder( final int lx, final int ly ) {
//            this.lx = lx;
//            this.ly = ly;
//        }
//
//
//        @Override
//        public Tile build() {
//            return new Tile( this );
//        }
//    }


    // public Map<Dir2D, WallSegments> getWallMap() {
    //     return wallM;
    // }


    public static class Floor {

        public static final Floor FLOOR_EMPTY = new Floor();

    }
}
