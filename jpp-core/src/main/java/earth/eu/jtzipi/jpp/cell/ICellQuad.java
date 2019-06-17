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

import java.util.Collections;
import java.util.Set;

/**
 * A quadratic cell used for 2D grids.
 *
 * @author langhammer
 */
public interface ICellQuad extends ICell {


    /**
     * A coordinate undefined .
     */
    int UNDEFINED = -1;

    /**
     * X Coordinate of this tile.
     * @return lx coordinate
     */
    int getX();

    /**
     * Y Coordinate of this tile.
     * @return
     */
    int getY();

    /**
     * Return the neighbour if present.
     *
     * @param position2D direction
     * @return neighbour or {@linkplain UnknownCell}
     */
    ICellQuad getNeighbour( Position2D position2D );

    /**
     * See if this cell has a neighbour.
     *
     * @param position2D direction
     * @return {@code true} if a neighbour is present
     */
    default boolean isNeighbour( Position2D position2D ) {
        return getNeighbour( position2D ) != UnknownCell.SINGLETON;
    }

    /**
     * Link this cell with other cell, optional both side.
     *
     * @param cell     other cell
     * @param bidiProp link other cell with this too
     */
    void link( ICellQuad cell, boolean bidiProp );

    /**
     * Unklink this cell with other cell.
     *
     * @param cell     other cell
     * @param bidiProp unlink other cell with this too (if linked)
     * @see #link(ICellQuad, boolean)
     */
    void unlink( ICellQuad cell, boolean bidiProp );

    /**
     * Return set of linked cells.
     *
     * @return set of linked cells
     */
    Set<ICellQuad> getLinkedCells();

    /**
     * Return all neighbours which are not undefined.
     *
     * @return set of all non {@code UNKNOWN_CELL} neighbour
     */
    Set<ICellQuad> getNeighbours();


    /**
     * NullValue of ICellQuad.
     */
    enum UnknownCell implements ICellQuad {

        SINGLETON;

        UnknownCell() {
            // 
        }

        @Override
        public int getX() {
            return UNDEFINED;
        }

        @Override
        public int getY() {
            return UNDEFINED;
        }

        @Override
        public ICellQuad getNeighbour( Position2D position2D ) {
            return null;
        }

        @Override
        public void link(ICellQuad cell, boolean bidiProp) {
            throw new UnsupportedOperationException("You can not link a cell to unknown cell.");
        }

        @Override
        public void unlink(ICellQuad cell, boolean bidiProp) {
            throw new UnsupportedOperationException("You can not unlink a cell from this unknown cell.");
        }


        @Override
        public Set<ICellQuad> getLinkedCells() {
            return Collections.emptySet();
        }

        @Override
        public boolean isLinked(ICell cell) {
            return false;
        }

        @Override
        public Set<ICellQuad> getNeighbours() {
            return Collections.emptySet();
        }
    }
}
