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

public interface ITile {
    /**
     * A coordinate undefined .
     */
    int UNDEFINED = -1;

    /**
     * Default level.
     */
    int LEVEL_DEFAULT = 0;

        /**
         * Level of tile.
         * <p>
         *     Level below 0 should be level beneath earth.
         * </p>
         * @return level
         */
    int getLevel();


    /**
     * X Coordinate of this tile.
     * @return x coordinate
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
     * @return neighbour or {@linkplain UnknownTile}
     */
    ITile getNeighbour( Position2D position2D );
    /**
     * Link this tile with other tile, optional both side.
     *
     * @param tile     other tile
     * @param bidiProp link other tile with this too
     */
    void link( ITile tile, boolean bidiProp );

    /**
     * Unklink this tile with other tile.
     *
     * @param tile     other tile
     * @param bidiProp unlink other tile with this too (if linked)
     * @see #link(earth.eu.jtzipi.jpp.ui.tile.ITile, boolean)
     */
    void unlink( ITile tile, boolean bidiProp );

    /**
     * Is {@code tile} linked with this tile.
     * @param tile other tile
     * @return
     */
    boolean isLinked( ITile tile );

    enum UnknownTile implements ITile {
        /** Singleton instance. */
        SINGLETON;

        @Override
        public int getLevel() {
            return -999_999_999;
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
        public ITile getNeighbour( Position2D position2D ) {
            throw new UnsupportedOperationException("");
        }

        @Override
        public void link( ITile tile, boolean bidiProp ) {
            throw new UnsupportedOperationException("");
        }

        @Override
        public void unlink( ITile tile, boolean bidiProp ) {
            throw new UnsupportedOperationException("");
        }

        @Override
        public boolean isLinked( ITile tile ) {
            return false;
        }
    }
}
