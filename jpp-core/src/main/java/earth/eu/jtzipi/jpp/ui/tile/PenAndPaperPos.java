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

/**
 * 2D Position.
 */
public enum PenAndPaperPos {

    /**
     * North.
     */
    N( "North" ),
    /**
     * East.
     */
    E( "East"),
    /**
     * Center.
     */
    C( "Center" ),
    U( "Up" ),
    D( "Down" ),
    /**
     * West.
     */
    W( "West"),
    /**
     * South.
     */
    S( "South");

    private final String dn;

    PenAndPaperPos( final String str ) {
        this.dn = str;
    }
}
