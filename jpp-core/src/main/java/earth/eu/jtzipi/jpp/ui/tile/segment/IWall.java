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

import earth.eu.jtzipi.jpp.IBuilder;
import earth.eu.jtzipi.jpp.ui.tile.Position2D;
import javafx.geometry.Pos;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * A WallSegments is a custom segment.
 * <p>
 *     Typically each tile contains walls.
 * </p>
 */
public interface IWall {

    /**
     * Types of wall.
     * <p>All walls are supposed to be prototypes directed north of a tile.</p>
     */
    enum Type {


    }

    /**
     * Position of wall.
     * @param pos2D position
     * @return {@code this}
     */
    IWall pos( Position2D pos2D );

    /**
     * Type of wall.
     * @param type type
     * @return
     */
    IWall type( Type type );




    }
