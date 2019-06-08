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



import javafx.scene.shape.Shape;

/**
 * A Segment is a part of a tile which is drawn.
 * <p>
 *     Typical segments are
 *     <ul>
 *         <li>walls</li>
 *         <li>doors which are part of a wall</li>
 *         <li>treasure</li>
 *         <li>stairs</li>
 *         <li>knobs</li>
 *     </ul>
 *     Segment are draw as {@linkplain Shape}'s.
 * </p>
 */
public interface ISegment {

    /*** Percentage of a default segment. */
    double LEN_DEFAULT = 0.15D;
    /** Percentage of a large segment. */
    double LEN_LARGE = 0.7D;
    /** Percentage of a small segment. */
    double LEN_SMALL = 0.1D;
    /** Segment with .*/
    double SEGMENT_WIDTH = 0.02D;

    /**
     * Create this segments path.
     * param width width of tile
     * @return path of segment
     */
    Shape createPath(  );

    /**
     * Id of segment.
     * @return unique id
     */
    long getId();


}
