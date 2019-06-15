package earth.eu.jtzipi.jpp.ui.tile.segment;

import earth.eu.jtzipi.jpp.ui.tile.Position2D;
import earth.eu.jtzipi.jpp.ui.tile.TileProperties;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;


import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

/**
 * A wall in a tile.
 */
public class Wall  {


    // Rotate by 90° clockwise
    private static final Rotate ROTATE_90_DEGREE = new Rotate( 90D );
    // Flip
    private static final Rotate ROTATE_180_DEGREE = new Rotate( 180D );
    // Translate south
    private static final Translate TLATE_SOUTH = new Translate( 0D, TileProperties.getLength() );
    // Translate east
    private static final Translate TLATE_EAST = new Translate( TileProperties.getLength(), 0D);


    static {
        /**
         * Bind everything to tiles width.
         */

        ROTATE_180_DEGREE.pivotXProperty().bind( TileProperties.widthPropertyFX().divide( 2D ) );

        TLATE_SOUTH.yProperty().bind( TileProperties.widthPropertyFX() );

        TLATE_EAST.xProperty().bind( TileProperties.widthPropertyFX() );
    }
    // Wall segment type
    private WallSegments ws;

    private Color color = Color.grayRgb( 47 );
    private ObjectProperty<Color> fxColorProp = new SimpleObjectProperty<>(this, "FX_WALL_COLOR_PROP", color );


    Wall( WallSegments wall ) {

        this.ws = wall;
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


                double w = TileProperties.getLength();
                double ws = TileProperties.SEGMENT_WIDTH.doubleValue();

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
                //double ws = width * SEGMENT_WIDTH;
                double gysi = TileProperties.getLength();
                double ws = TileProperties.SEGMENT_WIDTH.doubleValue();

                double near = TileProperties.SEGMENT_LEN.doubleValue();
                double door = TileProperties.SEGMENT_LEN_SMALL.doubleValue();
                //double offDoor = LEN_DEFAULT * width;
                //double endDoor = width - offDoor;
                //double doorheight = width * LEN_SMALL;

                Path path = PathBuilder.create().strokeWidth( ws ).lx( near ).ly( door ).lx( gysi - near ).ly( 0 ).lx( gysi ).build();
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



                double gysi = TileProperties.getLength();
                double ws = TileProperties.SEGMENT_WIDTH.doubleValue();

                double near = TileProperties.SEGMENT_LEN.doubleValue();
                double door = TileProperties.SEGMENT_LEN_SMALL.doubleValue();


                return   PathBuilder.create().strokeWidth( ws ).lx( near ).ly( door ).lx( gysi - near ).ly( 0 ).lx( gysi ).build()             ;
            }
        },
        /**
         * A wall with dashes.
         */
        SPECIAL( 9L, false ) {
            @Override
            public Shape createPath() {
                return null;
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
                double gysi = TileProperties.getLength();
                double ws = TileProperties.SEGMENT_WIDTH.doubleValue();

                double near = TileProperties.SEGMENT_LEN.doubleValue();


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


                double w = TileProperties.getLength();
                double ws = TileProperties.SEGMENT_WIDTH.doubleValue();

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