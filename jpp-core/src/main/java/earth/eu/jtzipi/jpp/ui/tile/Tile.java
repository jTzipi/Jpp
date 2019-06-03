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

import earth.eu.jtzipi.jpp.IBuilder;
import earth.eu.jtzipi.jpp.ui.tile.segment.Wall;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static earth.eu.jtzipi.jpp.ui.tile.Position2D.*;

/**
 * A tile is a square on a pan & paper map.
 *
 * @author jTzipi
 */
public class Tile extends Region implements ITile {



    /*
    public enum Dir2D {
        N,
        E,
        W,
        S;


    } */



    int x;
    int y;
    int level;
    /** Segments .*/
    Wall eW;
    Wall wW;
    Wall sW;
    Wall nW;



    /** Neighbour tiles. */
    EnumMap<Position2D, ITile> neighbourM = new EnumMap<>( Position2D.class );



    Tile( Wall eastWall, Wall northWall, Wall westWall, Wall southWall, int x, int y, int level ) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.eW = eastWall;
        this.sW = southWall;
        this.nW = northWall;
        this.wW = westWall;
        init();
        draw();
    }


    Tile( int x, int y, int level ) {
        this.x = x;
        this.y = y;
    this.level = level;
    }

    Tile( Builder builder ) {
        this( builder.x,
                builder.y, builder.level
                );
    }

    Tile() {
        TileProperties.widthPropertyFX().addListener( ( obs, oldW, newW )  -> {

        } );
        // createTile();
        draw();
    }

    public static Tile of( Wall eastWall, Wall northWall, Wall westWall, Wall southWall, int x, int y, int level ) {
        return new Tile( eastWall, northWall, westWall, southWall, x, y, level);
    }

    public static Tile of( final int x, final int y){
        if( 0 > x || 0 > y ) {


        }



        return new Tile(x, y, LEVEL_DEFAULT);
    }

    public static Tile solid( int x, int y ) {

        Wall we = Wall.solid(  );



        return new Tile( we, we, we, we, x, y, 0 );
    }

private void init() {
    final DoubleProperty tw = TileProperties.widthPropertyFX();
        this.prefWidthProperty().bind( tw );
        this.prefHeightProperty().bind( tw );
        setWidth( getPrefWidth() );
        setHeight( 21 );
}

    void draw() {

        getChildren().setAll( createTile() );
        setBackground( new Background( new BackgroundFill( Color.TEAL, null, null  ) )  );


        System.out.println("Width :" + getWidth() );
        System.out.println("Height :" + getHeight() );
        System.out.println("PH :" + getPrefHeight() );
        System.out.println("PW :" + getPrefWidth() );
    }

    private Collection<? extends Path> createTile() {


        //double w = TileProperties.getLength();

        // segment size

        //Line wall = new Line( 0D, 0D, 0, h );
        //wall.setStrokeWidth( 5D );
       List<Path> segL = new ArrayList<>();

       segL.add( eW.toPath( E ) );
       segL.add( sW.toPath( S ) );
       segL.add( nW.toPath( N ) );
       segL.add( wW.toPath( W ) );

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



    public static final class Builder implements IBuilder<Tile> {

        // mandatory
        private final int x;
        private final int y;
        // optional

        private int level;


        Builder( final int x, final int y ) {
            this.x = x;
            this.y = y;
        }



        @Override
        public Tile build() {
        return new Tile( this );
        }
    }


   // public Map<Dir2D, WallSegments> getWallMap() {
   //     return wallM;
   // }



    public static class Floor {

        public static final Floor FLOOR_EMPTY = new Floor();

    }
}
