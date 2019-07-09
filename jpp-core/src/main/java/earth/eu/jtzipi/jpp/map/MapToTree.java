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

import earth.eu.jtzipi.jpp.ui.tree.ITreeNodeInfo;
import javafx.scene.Node;

/**
 * Helper to create {@linkplain ITreeNodeInfo} .
 *
 */
public final class MapToTree {



    private MapToTree() {

    }

    /**
     *
     * @param penAndPaperMap
     * @return
     */
    public static ITreeNodeInfo mapToInfo( final IPenAndPaperMap penAndPaperMap ) {

        return new ITreeNodeInfo() {
            @Override
            public String getText() {
                return penAndPaperMap.getName();
            }

            @Override
            public String getDescription() {
                return penAndPaperMap.getDescription();
            }

            @Override
            public Node getGraphic() {
                return null;
            }

            @Override
            public boolean isLeaf() {
                return true;
            }
        };
    }

    /**
     *
     * @param penAndPaperSite
     * @return
     */
    public static ITreeNodeInfo siteToInfo(final IPenAndPaperSite penAndPaperSite ) {

        return new ITreeNodeInfo() {
            @Override
            public String getText() {
return penAndPaperSite.getName();
            }

            @Override
            public String getDescription() {
                return "";
            }

            @Override
            public Node getGraphic() {
                return null;
            }

            @Override
            public boolean isLeaf() {
                return false;
            }
        };
    }

    /**
     *
     * @param penAndPaperRealm
     * @return
     */
    public static ITreeNodeInfo realmToInfo( final IPenAndPaperRealm penAndPaperRealm ) {
        return new ITreeNodeInfo() {
            @Override
            public String getText() {
                return penAndPaperRealm.getDescription();
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public Node getGraphic() {
                return null;
            }

            @Override
            public boolean isLeaf() {
                return false;
            }
        };
    }
}
