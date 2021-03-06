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

/**
 * Base cell.
 *
 * @author langhammer
 */
public interface ICell {
    /**
     * Return whether this cell linked to other cell.
     *
     * @param cell other cell
     * @return {@code true} if this cell is linked to {@code cell}
     */
    boolean isLinked( ICell cell );

}
