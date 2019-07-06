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

import java.util.List;


/**
 * Pen and Paper Site.
 * <p>
 *     A site contains one or more maps.;
 * </p>
 */
public interface IPenAndPaperSite {

    /**
     *
     * @return
     */
    String getName();

    List<IPenAndPaperMap> getLevelMaps();

}
