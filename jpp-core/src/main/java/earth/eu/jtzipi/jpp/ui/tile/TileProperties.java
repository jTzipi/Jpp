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

import earth.eu.jtzipi.jpp.ui.tile.segment.ISegment;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


/**
 * Shared Properties.
 */
@Deprecated
public final class TileProperties {



    public static final double MIN_LEN_TILE = 10D;
    public static final double PREF_LEN_TILE = 50D;
    public static final double MAX_LEN_TILE = 100D;

    // Main Property every other is derived from
    private static final DoubleProperty FX_WIDTH_PROP = new SimpleDoubleProperty( PREF_LEN_TILE );

    /**
     *  A single default segment's length.
     */
    public static NumberBinding SEGMENT_LEN = FX_WIDTH_PROP.multiply( ISegment.LEN_DEFAULT );
    /**
     * A small segment's .
     */
    public static NumberBinding SEGMENT_LEN_SMALL  = FX_WIDTH_PROP.multiply( ISegment.LEN_SMALL );
    /**
     * A large segment.
     */
    public static NumberBinding SEGMENT_LEN_LARGE = FX_WIDTH_PROP.multiply( ISegment.LEN_LARGE );
    /**
     * Width of segment.
     */
    public static NumberBinding SEGMENT_WIDTH = FX_WIDTH_PROP.multiply( ISegment.SEGMENT_WIDTH );

    public static DoubleProperty FX_GAP_NORTH_PROP = new SimpleDoubleProperty( 25D );
    public static DoubleProperty FX_GAP_WEST_PROP = new SimpleDoubleProperty( 24D );



    private TileProperties() {

    }



    public static double getLength() {
        return FX_WIDTH_PROP.getValue();
    }

    public static DoubleProperty widthPropertyFX() {
        return FX_WIDTH_PROP;
    }

}
