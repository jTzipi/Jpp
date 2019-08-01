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


import earth.eu.jtzipi.jpp.ui.tile.segment.ISegment;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;

/**
 * Global Properties of Map.
 *
 * @author jtzipi
 */
public final class MapPropertiesFX {

    /** Forbid Instance. */
    private MapPropertiesFX() {
        //
    }

    /** Min tile length. */
    public static final double MIN_LEN_TILE = 10D;
    /** Pref tile length. */
    public static final double PREF_LEN_TILE = 50D;
    /** Max tile length. */
    public static final double MAX_LEN_TILE = 100D;

    /** Main Property every other is derived from. */
    public static final DoubleProperty FX_TILE_WIDTH_PROP = new SimpleDoubleProperty( PREF_LEN_TILE );

    /**
     *  A single default segment's length.
     */
    public static NumberBinding SEGMENT_LEN = FX_TILE_WIDTH_PROP.multiply( ISegment.LEN_DEFAULT );
    /**
     * A small segment's .
     */
    public static NumberBinding SEGMENT_LEN_SMALL = FX_TILE_WIDTH_PROP.multiply( ISegment.LEN_SMALL );
    /**
     * A large segment.
     */
    public static NumberBinding SEGMENT_LEN_LARGE = FX_TILE_WIDTH_PROP.multiply( ISegment.LEN_LARGE );
    /**
     * Width of segment.
     */
    public static NumberBinding SEGMENT_WIDTH = FX_TILE_WIDTH_PROP.multiply( ISegment.SEGMENT_WIDTH );

    /**
     * North Edge length.
     */
    public static DoubleProperty FX_GAP_EDGE_NORTH_PROP = new SimpleDoubleProperty( 25D );
    /**
     * West edge length.
     */
    public static DoubleProperty FX_GAP_EDGE_WEST_PROP = new SimpleDoubleProperty( 25D );
    /**
     * Offset left and top of map pane to show legend.
     */
    public static DoubleBinding FX_TILE_OFFSET_BIND;



    /**
     * Show edge prop.
     */
    public static final BooleanProperty FX_SHOW_MAP_EDGE_PROP = new SimpleBooleanProperty(true);
    /** Mouse map grid x position.  */
    public static final IntegerProperty FX_MOUSE_X_PROP = new SimpleIntegerProperty();
    /** Mouse map grid y position.  */
    public static final IntegerProperty FX_MOUSE_Y_PROP = new SimpleIntegerProperty();

    static {
        FX_TILE_OFFSET_BIND  = new DoubleBinding() {
            @Override
            protected double computeValue() {
                return FX_SHOW_MAP_EDGE_PROP.get() ? FX_TILE_WIDTH_PROP.doubleValue() : 0D;
            }
        };
        FX_TILE_WIDTH_PROP.addListener( ( iv ) -> FX_TILE_OFFSET_BIND.invalidate() );
    }



}
