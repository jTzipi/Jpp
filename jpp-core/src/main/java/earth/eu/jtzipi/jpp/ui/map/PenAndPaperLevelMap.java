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

import java.util.ArrayList;
import java.util.List;

/**
 * Pan and Paper map.
 * <p>
 *     This class models a complete level map.
 * </p>
 * @author jTzipi
 */
public class PuPMap {

    String name;
    String desc;
    int dimX;
    int dimY;
    int level;
    List<List<IPenAndPaperCell>> map;


    PuPMap( int dimX, int dimY, int level, String name ) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.level = level;
        this.name = name;
        this.map = new ArrayList<>(dimX);
    }


    public static PuPMap of( final int xDim, final int yDim, final int level, final String nameStr ) {
        if( 0 > xDim || 0 > yDim ) {

        }
        return new PuPMap( xDim, yDim, level, null == nameStr ? "TODO:RENAME" : nameStr );
    }

    public void addTile( int x, int y, IPenAndPaperCell tile ) {
        if( 0 > x || 0 > y || x >= dimX || y >= dimY ) {
            throw new IllegalArgumentException("lx/ly must >= 0 and < dimX/dimY! Your lx/ly = ");
        }

        // lazy create new ArrayList


    }
}
