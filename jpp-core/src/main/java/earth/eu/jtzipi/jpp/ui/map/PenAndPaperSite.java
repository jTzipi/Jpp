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

import earth.eu.jtzipi.jpp.map.IPenAndPaperMap;
import earth.eu.jtzipi.jpp.map.IPenAndPaperSite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PenAndPaperSite implements IPenAndPaperSite {

    private String name;
    private List<IPenAndPaperMap> levelMapL;

    PenAndPaperSite( final String nameStr, final List<IPenAndPaperMap> penAndPaperMapList ) {
        this.name = nameStr;
        this.levelMapL = penAndPaperMapList;
    }


    PenAndPaperSite ( final String nameStr ) {
        this(nameStr, new ArrayList<>() );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<IPenAndPaperMap> getLevelMaps() {
        return levelMapL;
    }

    public static PenAndPaperSite of( String nameStr ) {

        return new PenAndPaperSite( nameStr );
    }

    @Override
    public void removeMap( IPenAndPaperMap map ) {
        if( null == map ) {


        }

        this.levelMapL.remove( map );
    }

    /**
     * Add a new map to this site.
     * @param map map
     * @throws NullPointerException if {@code map}
     */
    @Override
    public void addMap(final IPenAndPaperMap map) {
        Objects.requireNonNull( map );
        this.levelMapL.add( map );

    }
}
