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


import earth.eu.jtzipi.jpp.cell.IPenAndPaperCell;
import earth.eu.jtzipi.jpp.cell.PenAndPaperCell;
import earth.eu.jtzipi.jpp.ui.map.PenAndPaperLevelMap;
import earth.eu.jtzipi.jpp.ui.tile.segment.Wall;

/**
 * Catalogue of tiles.
 */
public final class PandPCells {
    // , IFloor floor
    private PandPCells() {}



    public static IPenAndPaperCell ofSolidWallESWDoorBreakableN( int x, int y, int level) {

        Wall solidWall = Wall.solid();
        Wall doorBreakable = Wall.doorBreakable(  );

        long idSolid = Wall.WallSegments.SOLID.getId();
        long idDoorB = Wall.WallSegments.DOOR_BREAKABLE.getId();

        return PenAndPaperCell.of( x, y, level, idDoorB, idSolid, idSolid, idSolid, 0, null );
    }

    public static IPenAndPaperCell ofSolidNEWBreakable( int x, int y, int level) {



        long idSolid = Wall.WallSegments.SOLID.getId();
        long idDoorB = Wall.WallSegments.DOOR_BREAKABLE.getId();

        return PenAndPaperCell.of( x, y, level, idSolid, idSolid, idSolid, idDoorB, 0, null );
    }
}
