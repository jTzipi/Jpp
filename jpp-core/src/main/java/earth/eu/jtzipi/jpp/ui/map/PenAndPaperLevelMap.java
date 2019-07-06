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

package earth.eu.jtzipi.jpp.ui.map;

import earth.eu.jtzipi.jpp.cell.IPenAndPaperCell;
import earth.eu.jtzipi.jpp.map.IPenAndPaperMap;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Pan and Paper map.
 * <p>
 *     This class models a complete level map.
 * </p>
 * @author jTzipi
 */
public class PenAndPaperLevelMap implements IPenAndPaperMap {

    /** name of map. */
    String name;
    /** desc of map. */
    String desc;
    /** dimension x .*/
    int dimX;
    /** dimension y .*/
    int dimY;
    /** level .*/
    int level;
    /** tiles. */
    List<List<IPenAndPaperCell>> map;

    /**
     *
     * @param dimX
     * @param dimY
     * @param level
     * @param name
     */
    PenAndPaperLevelMap( int dimX, int dimY, int level, String name ) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.level = level;
        this.name = name;
        this.map = new ArrayList<>(dimX);
    }

    /**
     * Create a empty level map with [x,y] dimension.
     * @param xDim x dimension
     * @param yDim y dimension
     * @param level level
     * @param nameStr name
     * @return map
     */
    public static PenAndPaperLevelMap of( final int xDim, final int yDim, final int level, final String nameStr ) {
        if( 0 > xDim || 0 > yDim ) {

        }
        return new PenAndPaperLevelMap( xDim, yDim, level, null == nameStr ? "TODO:RENAME" : nameStr );
    }

    @Override
    public int getDimX() {
        return dimX;
    }
    @Override
    public int getDimY() {
        return dimY;
    }

    private void init() {

    }

    public String getDescription() {
        return desc;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public IPenAndPaperCell getCell( int x, int y ) {

        if( x < 0 || map.size() <= x ) {

        }
        if( y < 0 || map.get( 0 ).size() <= y) {

        }

        return map.get(x).get(y);
    }

    @Override
    public List<IPenAndPaperCell> getCells() {
        return map.stream().flatMap( list -> list.stream() ).collect( toList() );
    }
}
