/*
 *    Copyright (c) 2019 Tim Langhammer
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
 */

package earth.eu.jtzipi.jpp.ui.tile;

import earth.eu.jtzipi.jpp.ui.tile.segment.Wall;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

import static earth.eu.jtzipi.jpp.ui.tile.Position2D.*;

/**
 * A tile is a square on a pan & paper map.
 *
 * @author jTzipi
 */
public class Tile extends StackPane implements ITile {



    /*
    public enum Dir2D {
        N,
        E,
        W,
        S;


    } */
    /** X Position on grid. */
    int x;
    /** Y Position on grid. */
    int y;
    /** Level of map */
    int level;
    /**
     * Segments .
     */
    /** East Wall.*/
    Wall eW;
    Wall wW;
    Wall sW;
    Wall nW;


    Pane baseP;
    Pane aviP;


    /**
     * Neighbour tiles.
     */
    EnumMap<Position2D, ITile> neighbourM = new EnumMap<>( Position2D.class );


    /**
     * Tile complete constructor.
     *
     * @param eastWall  wall east
     * @param northWall wall north
     * @param westWall  wall west
     * @param southWall wall south
     * @param x         coordinate lx
     * @param y         coordinate ly
     * @param level     level
     */
    Tile(  int x, int y, int level, Wall eastWall, Wall northWall, Wall westWall, Wall southWall ) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.eW = null == eastWall ? Wall.empty() : eastWall;
        this.sW = null == southWall ? Wall.empty() : southWall;
        this.nW = null ==  northWall ? Wall.empty() : northWall;
        this.wW = null == westWall ? Wall.empty() : westWall; // Maup;

        init();     // init code
        create();   // create all parts
        draw();     // draw
    }


    Tile( int x, int y, int level ) {
        this( x, y, level, null, null, null, null );
    }


    Tile() {

        // createTile();
        draw();
    }

    public static final Tile of( Wall eastWall, Wall northWall, Wall westWall, Wall southWall, int x, int y, int level ) {
        return new Tile( x, y, level, eastWall, northWall, westWall, southWall );
    }

    public static Tile of( final int x, final int y ) {
        if ( 0 > x || 0 > y ) {


        }


        return new Tile( x, y, LEVEL_DEFAULT );
    }

    public static Tile solid( int x, int y ) {

        Wall we =  Wall.solid();


        return new Tile( x, y, 0, we , we, we, we );
    }

    private void init() {
        final DoubleProperty tw = TileProperties.widthPropertyFX();
        final DoubleProperty gnorth = TileProperties.FX_GAP_NORTH_PROP;
        final DoubleProperty gwest = TileProperties.FX_GAP_WEST_PROP;

        prefWidthProperty().bind( tw );
        prefHeightProperty().bind( tw );


        tw.addListener( ( obs, oldW, newW ) -> {

            if ( oldW != newW ) {
                draw();
            }
        } );

        // layout
        NumberBinding layoutXBd = tw.multiply( getX() ).add( gwest );
        NumberBinding layoutYBd = tw.multiply( getY() ).add( gnorth );

        layoutXProperty().bind( layoutXBd );
        layoutYProperty().bind( layoutYBd );
    }

    private void create() {


        baseP = new Pane();
        aviP = new Pane();



        setOnMouseEntered( me -> System.out.println("tile go")  ); // me -> aviP.setVisible( true )
        setOnMouseExited( me -> System.out.println( "tile off" ) );

        getChildren().addAll( baseP, aviP );

    }

    private void draw() {
        setWidth( getPrefWidth() );
        setHeight( getPrefHeight() );

        baseP.getChildren().setAll( createTile() );


        baseP.setBackground( new Background( new BackgroundFill( Color.TEAL, null, null ) ) );


        System.out.println( "Width :" + getWidth() );
        System.out.println( "Height :" + getHeight() );
        System.out.println( "PH :" + getPrefHeight() );
        System.out.println( "PW :" + getPrefWidth() );

        createAviPane();
    }

    private void createAviPane() {

        Wall w = Wall.gate();
        w.getColorPropFX().setValue( Color.RED );

        Path we = w.toPath( E );
        Path ww = w.toPath( W );
        Path ws = w.toPath( S );
        Path wno = w.toPath( N );

        aviP.getChildren().setAll( we, ww, ws, wno );
     //   aviP.setVisible( false );

    }

    private Collection<? extends Shape> createTile() {
                // segment size



        List<Shape> segL = new ArrayList<>();

        segL.add( eW.toPath( E ) );
        segL.add( sW.toPath( S ) );
        segL.add( nW.toPath( N ) );
        segL.add( wW.toPath( W ) );

        Text tt = new Text();


        tt.setFont( Font.font( 27D ) );
        tt.setText( getX() + " " + getY() );
        tt.layoutXProperty().bind( layoutXProperty().add( 10D ) );
        tt.layoutYProperty().bind( prefHeightProperty().subtract( 10D ) );
        segL.add( tt );

        return segL;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public ITile getNeighbour( Position2D position2D ) {
        return neighbourM.getOrDefault( position2D, UnknownTile.SINGLETON );
    }

    @Override
    public void link( ITile cell, boolean bidiProp ) {

    }

    @Override
    public void unlink( ITile cell, boolean bidiProp ) {

    }

    @Override
    public boolean isLinked( ITile cell ) {
        return false;
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
