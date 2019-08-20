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

package earth.eu.jtzipi.jpp.cell;

import earth.eu.jtzipi.jpp.ui.tile.Position2D;
import earth.eu.jtzipi.jpp.ui.tile.segment.Wall;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Set;

/**
 * Pen and Paper cell.
 * @author jTzipi
 */
public class PenAndPaperCell implements IPenAndPaperCell {

    /**
     * Id of wall north .
     */
    long idWN;
    /** Id of wall east .*/
    long idWE;
    /** Id of wall west .*/
    long idWW;
    /** Id of wall south .*/
    long idWS;
    /**
     * X Position on grid.
     */
    private int x;
    /**
     * Y Position on grid.
     */
    private int y;
    /**
     * Level of map
     */
    private int level;
    /**
     * Id of floor
     */
    private long idF;
    private Set<Tag> tagS;
    private EnumMap<Position2D, ICellQuad> neighbourM = new EnumMap<>( Position2D.class );



    private PenAndPaperCell( final int cX,
                             final int cY,
                             final int level,
                             final long idWallNorth,
                             final long idWalleast,
                             final long idWallWest,
                             final long idWallSouth,
                             long idFloor, Set<Tag> tagSet ) {
        this.x = cX;
        this.y = cY;
        this.level = level;
        this.idWN = idWallNorth;
        this.idWE = idWalleast;
        this.idWW = idWallWest;
        this.idWS = idWallSouth;
        this.idF = idFloor;
        this.tagS = tagSet;
    }

    /**
     * Create a new pen&paper cell with empty walls and floor.
     * @param cX x
     * @param cY y
     * @param level level
     * @return empty ppc
     * @throws IllegalArgumentException
     */
    public static PenAndPaperCell ofEmpty( final int cX, final int cY, final int level ) {
        // position wrong
        if ( 0 > cX || 0 > cY ) {
            throw new IllegalArgumentException( "cell x[="+cX+"] or y[="+cY+"] < 0" );
        }

        long id = Wall.WallSegments.NONE.getId();

        return new PenAndPaperCell( cX, cY, level, id, id, id, id, id, null );
    }

    /**
     * Return a tile with all wall solid.
     * @param cX
     * @param cY
     * @param level
     * @return
     */
    public static PenAndPaperCell ofSolid( final int cX, final int cY, final int level ) {
        // position wrong
        if ( 0 > cX || 0 > cY ) {
            throw new IllegalArgumentException( "cell x[="+cX+"] or y[="+cY+"] < 0" );
        }

        long id = Wall.WallSegments.SOLID.getId();

        return new PenAndPaperCell( cX, cY, level, id, id, id, id, id, null );
    }

    /**
     *
     * @param cX
     * @param cY
     * @param level
     * @param idWN
     * @param idWE
     * @param idWW
     * @param idWS
     * @param idF
     * @param tagSet
     * @return
     */
    public static PenAndPaperCell of( final int cX, final int cY, final int level, long idWN, long idWE, long idWW, long idWS, long idF, Set<Tag> tagSet ) {
        return new PenAndPaperCell( cX, cY, level, idWN, idWE, idWW, idWS, idF, tagSet );
    }

    @Override
    public int getLevel() {
        return level;
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
    public ICellQuad getNeighbour( Position2D position2D ) {
        return neighbourM.getOrDefault( position2D, UnknownCell.SINGLETON );
    }

    @Override
    public void link( ICellQuad cell, boolean bidiProp ) {

    }

    @Override
    public void unlink( ICellQuad cell, boolean bidiProp ) {

    }

    @Override
    public Set<ICellQuad> getLinkedCells() {
        return null;
    }

    @Override
    public Set<ICellQuad> getNeighbours() {
        return null;
    }

    @Override
    public boolean isLinked( ICell cell ) {
        return false;
    }

    // Segment
    @Override
    public long getIdWallNorth() {
        return idWN;
    }

    @Override
    public long getIdWallEast() {
        return idWE;
    }

    @Override
    public long getIdWallWest() {
        return idWW;
    }

    @Override
    public long getIdWallSouth() {
        return idWS;
    }

    @Override
    public long getIdFloor() {
        return idF;
    }

    @Override
    public Set<Tag> getTagSet() {
        return tagS;
    }

    @Override
    public int hashCode() {
        return Objects.hash( getX(), getY(), getLevel() );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        PenAndPaperCell that = ( PenAndPaperCell ) o;
        return getX() == that.getX() &&
                getY() == that.getY() &&
                getLevel() == that.getLevel();
    }

    @Override
    public String toString() {
        return "PenAndPaperCell{" +
                "x=" + x +
                ", y=" + y +
                ", level=" + level +
                '}';
    }
}
