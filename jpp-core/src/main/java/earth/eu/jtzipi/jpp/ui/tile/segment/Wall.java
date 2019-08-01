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

package earth.eu.jtzipi.jpp.ui.tile.segment;

import earth.eu.jtzipi.jpp.ui.MapPropertiesFX;
import earth.eu.jtzipi.jpp.ui.tile.Position2D;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.*;


/**
 * A wall in a tile.
 */
public class Wall  {

    private static Map<Long, WallSegments> segments = new HashMap<>();
    // Rotate by 90° clockwise
    private static final Rotate ROTATE_90_DEGREE = new Rotate( 90D );
    // Flip
    private static final Rotate ROTATE_180_DEGREE = new Rotate( 180D );
    // Translate south
    private static final Translate TLATE_SOUTH = new Translate( 0D, MapPropertiesFX.FX_TILE_WIDTH_PROP.doubleValue() );
    // Translate east
    private static final Translate TLATE_EAST = new Translate( MapPropertiesFX.FX_TILE_WIDTH_PROP.doubleValue(), 0D );


    static {
        /**
         * Bind everything to tiles width.
         */

        DoubleProperty widthProp = MapPropertiesFX.FX_TILE_WIDTH_PROP;
        ROTATE_180_DEGREE.pivotXProperty().bind( widthProp.divide( 2D ) );
        TLATE_SOUTH.yProperty().bind( widthProp );
        TLATE_EAST.xProperty().bind( widthProp );

        for( WallSegments wallSegments : WallSegments.values() ) {
            segments.put( wallSegments.getId(), wallSegments );
        }

    }
    // Wall segment type
    private WallSegments ws;

    private Color color = Color.grayRgb( 47 );

    private ObjectProperty<Color> fxColorProp = new SimpleObjectProperty<>(this, "FX_WALL_COLOR_PROP", color );




    Wall( WallSegments wall ) {

        this.ws = wall;
    }


    /**
     * Create a wall by Id.
     * @param id wall id
     * @return wall if known or empty wall
     */
    public static Wall ofId( long id ) {
        return new Wall( segments.getOrDefault( id, WallSegments.NONE ) );
    }
    /**
     * Return a solid wall.
     *  pos2D position ( if null {@linkplain Position2D#E})
     * @return solid wall
     */
    public static Wall solid(  ) {
        return new Wall( WallSegments.SOLID );
    }

    /**
     * Return a empty wall.
     *
     * @return wall with no content
     */
    public static Wall empty(){
        return new Wall(WallSegments.NONE);
    }

    /**
     * Return a door which can be opened with key or forced.
     * @return a breakable door segment
     */
    public static Wall doorBreakable() {
        return new Wall( WallSegments.DOOR_BREAKABLE );
    }


    public static Wall gate() {
        return new Wall( WallSegments.GATE );
    }

    /**
     * Wall color property.
     * @return wall color property
     */
    public final ObjectProperty<Color> getColorPropFX() {

        return fxColorProp;
    }

    /**
     * Create wall for position.
     * @param p2D position
     * @return Path
     * @throws NullPointerException
     */
    public Shape toPath( Position2D p2D ) {
        Objects.requireNonNull(p2D);

        Shape path = ws.createPath();

        path.setStroke( fxColorProp.getValue() );

        // nothing  when none
        if( WallSegments.NONE == ws ) {
            return path;
        }



        List<Transform> tL = new ArrayList<>();



        switch ( p2D ) {
            case N: ;break;

            case W:
                if( ws.flip ) {
                    tL.add(ROTATE_180_DEGREE);
                }
                tL.add(ROTATE_90_DEGREE); break;

            case S:
                tL.add( TLATE_SOUTH );
                if( ws.flip ) {
                    tL.add(ROTATE_180_DEGREE);
                }

                break;
            case E:

                tL.add( TLATE_EAST );
                tL.add( ROTATE_90_DEGREE );break;

        }

        path.getTransforms().addAll( tL );

        return path;
    }

    /**
     * Return a breakable door wall.
     * WallSegments are all kind of walls to draw.
     */
    public enum WallSegments implements ISegment {

        /**
         * WallSegments is not there.
         */
        NONE( 0L, false ) {
            @Override
            public Path createPath() {
                return new Path();
            }


        },
        /**
         * Solid wall.
         */
        SOLID( 1L, false ) {
            @Override
            public Shape createPath() {


                double w = MapPropertiesFX.FX_TILE_WIDTH_PROP.doubleValue();
                double ws = MapPropertiesFX.SEGMENT_WIDTH.doubleValue();

                Path path = PathBuilder.create().strokeWidth( ws ).lx( w ).build();


                return path;
            }


        },
        /**
         * Breakable door.
         * <p>That is a door which can be opened with or without key or broken with force</p>
         */
        DOOR_BREAKABLE( 2L, true ) {
            @Override
            public Shape createPath() {
                //return new Path(  );

                double gysi = MapPropertiesFX.FX_TILE_WIDTH_PROP.doubleValue();
                double ws = MapPropertiesFX.SEGMENT_WIDTH.doubleValue();

                double near = MapPropertiesFX.SEGMENT_LEN.doubleValue();
                double door = MapPropertiesFX.SEGMENT_LEN_SMALL.doubleValue();


                Path path = PathBuilder.create().strokeWidth( ws ).lx( near ).ly( door ).lx( gysi - near ).ly( 0D ).lx( gysi ).build();
                Rectangle d = new Rectangle( near, 0D, gysi - near, door );

                return Shape.union(path, d);
            }


        },
        /**
         * A wall with a unbreakable door.
         */
        DOOR_UNBREAKABLE( 3L, true ) {
            @Override
            public Shape createPath() {


                double gysi = MapPropertiesFX.FX_TILE_WIDTH_PROP.doubleValue();
                double ws = MapPropertiesFX.SEGMENT_WIDTH.doubleValue();

                double near = MapPropertiesFX.SEGMENT_LEN.doubleValue();
                double door = MapPropertiesFX.SEGMENT_LEN_SMALL.doubleValue();


                return PathBuilder.create().strokeWidth( ws ).lx( near ).ly( door ).lx( gysi - near ).ly( 0D ).lx( gysi ).build();
            }
        },
        /**
         * A wall with dashes.
         */
        SPECIAL( 9L, false ) {
            @Override
            public Shape createPath() {


                double gysi = MapPropertiesFX.FX_TILE_WIDTH_PROP.doubleValue();
                double ws = MapPropertiesFX.SEGMENT_WIDTH.doubleValue();
                double dash = MapPropertiesFX.SEGMENT_LEN_SMALL.doubleValue();

                Line solid = new Line( 0D, 0D, gysi, 0D );
                solid.setStrokeWidth( ws );
                solid.getStrokeDashArray().addAll( dash, dash );
                return solid;
            }
        },

        /**
         * A wall with an alcove .
         */
        ALCOVE( 5L, true )
                {
                    @Override
                    public Path createPath() {
                        return null;
                    }
                },

        /**
         * A wall with a gate.
         */
        GATE( 4L, false ){
            @Override
            public Shape createPath() {


                //double ws = width * SEGMENT_WIDTH;
                double gysi = MapPropertiesFX.FX_TILE_WIDTH_PROP.doubleValue();
                double ws = MapPropertiesFX.SEGMENT_WIDTH.doubleValue();
                double near = MapPropertiesFX.SEGMENT_LEN.doubleValue();


                return PathBuilder.create().strokeWidth( ws ).lx( near ).mx( gysi - near ).lx( gysi ).build();
            }
        },
        /**
         * A wall with a arc on 'east'.
         */
        ARC_E( 7L, false ){
            @Override
            public Shape createPath() {
                return null;
            }
        },
        /**
         * A wall with a arc on 'west'.
         */
        ARC_W( 8L, false ) {
            @Override
            public Shape createPath() {
                return null;
            }
        },

        SOLID_WITH_SWITCH( 10L, true) {
            @Override
            public Shape createPath() {


                double w = MapPropertiesFX.FX_TILE_WIDTH_PROP.doubleValue();
                double ws = MapPropertiesFX.SEGMENT_WIDTH.doubleValue();

                Path path = PathBuilder.create().strokeWidth( ws ).lx( w ).build();


                return path;
            }

        }
        ;

        final long id;
        final boolean flip;
        /**
         * WallSegments Enum.
         *
         * @param id id
         */
        WallSegments( long id, boolean flip ) {
            this.id = id;
            this.flip = flip;
        }

        @Override
        public long getId() {
            return id;
        }

    }
}