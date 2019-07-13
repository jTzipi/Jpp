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
package earth.eu.jtzipi.jpp.map;

import earth.eu.jtzipi.jpp.cell.IPenAndPaperCell;

import java.util.List;

/**
 * Pen And Paper Level map.
 *
 *
 *
 */
public interface IPenAndPaperMap {
    /**
     * Minimum x coordinate.
     */
    int MIN_X = 1;
    /**
     * Minimum y coordinate.
     */
    int MIX_Y = 1;
    /**
     * minimum level.
     */
    int MIN_LEVEL = -999;
    /**
     * Maximum level.
     */
    int MAX_LEVEL = 1_000;
    /**
     * Name of map.
     * @return name
     */
    String getName();

    /**
     * Map description.
     *
     * @return desc
     */
    String getDescription();;

    /**
     * X dimension.
     * @return dim x
     */
    int getDimX();

    /**
     * Y dimension.
     * @return dim y
     */
    int getDimY();

    /**
     * Level of this map.
     * @return level
     */
    int getLevel();

    /**
     * Return cell [x,y].
     *
     * @param x x
     * @param y y
     * @return cell for location if found
     */
    IPenAndPaperCell getCell( int x, int y );

    /**
     * Cells of this map.
     * @return cell list
     */
    List<IPenAndPaperCell> getCells();
}
