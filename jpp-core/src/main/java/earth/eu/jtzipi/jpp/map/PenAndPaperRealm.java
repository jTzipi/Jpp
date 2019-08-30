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

package earth.eu.jtzipi.jpp.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PenAndPaperRealm implements IPenAndPaperRealm {

    private String name;
    private List<IPenAndPaperSite> siteL;

    private PenAndPaperRealm( final String nameStr, final List<IPenAndPaperSite> siteList ) {
        this.name = nameStr;
        this.siteL = null == siteList ? new ArrayList<>() : siteList;
    }


    public static PenAndPaperRealm of(String nameStr) {
        return new PenAndPaperRealm( Objects.requireNonNull( nameStr ), null );
    }

    @Override
    public void addSite( final IPenAndPaperSite penAndPaperSite ) {
        this.siteL.add( penAndPaperSite );
    }

    @Override
    public String getDescription() {
return name;

    }

    @Override
    public List<IPenAndPaperSite> getSites() {
        return siteL;
    }

    @Override
    public void removeSite(final IPenAndPaperSite penAndPaperSite ) {

    }

}
