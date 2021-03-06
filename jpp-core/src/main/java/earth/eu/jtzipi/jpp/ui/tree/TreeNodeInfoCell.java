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

package earth.eu.jtzipi.jpp.ui.tree;

import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;

/**
 * Renders a {@link ITreeNodeInfo} tree item.
 *
 * @author jTzipi
 */
public class TreeNodeInfoCell extends TreeCell<ITreeNodeInfo> {

    /**
     * TreeNodeInfoCell.
     */
    public TreeNodeInfoCell() {

    }
    @Override
    protected void updateItem( ITreeNodeInfo item, boolean empty ) {
        super.updateItem( item, empty );

        if( null == item ) {
            return;
        }

        if( empty ) {
            setText( "" );
            setGraphic( null );
            setTooltip( null );
        }
        else {
            setText( item.getText() );
            setTooltip( new Tooltip(item.getDescription()) );
            setGraphic( item.getGraphic() );
        }
    }
}
