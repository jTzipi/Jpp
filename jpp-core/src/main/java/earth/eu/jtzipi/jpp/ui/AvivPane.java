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

package earth.eu.jtzipi.jpp.ui;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Pane above Map pane to display info.
 */
public class AvivPane extends Pane {


    private MapGeoPropVO geoPropVO;

    private ObjectProperty<Color> fxColorGridProp;

    AvivPane( final MapGeoPropVO geoPropertyVO ) {
        this.geoPropVO = geoPropertyVO;
        init();
        create();
    }

    private void init() {
// bind width and height

        prefHeightProperty().bind( geoPropVO.fxHeightBinding() );
        prefWidthProperty().bind( geoPropVO.fxWidthBinding() );
        // on change
        geoPropVO.fxDimXProp().addListener( iv -> create() );
        geoPropVO.fxDimYProp().addListener( iv -> create() );
    }


    private void create() {
        getChildren().clear();
        // tile width
        double tw = MapPropertiesFX.FX_TILE_WIDTH_PROP.doubleValue();
        // offset
        double offX = geoPropVO.fxOffsetXBinding().get();
        double offY = geoPropVO.fxOffsetYBinding().get();
        double w = geoPropVO.fxWidthBinding().get();
        double h = geoPropVO.fxHeightBinding().get();

        // all column
        for ( int ix = 0; ix < geoPropVO.fxDimXProp().get(); ix++ ) {

            double px = offX + ix * tw;
            Line lx = new Line( px, 0D, px, h );
            getChildren().add( lx );
        }
        // all row
        for ( int iy = 0; iy < geoPropVO.fxDimYProp().get(); iy++ ) {

            double py = offY + iy * tw;
            Line ly = new Line( 0D, py, w, py );
            getChildren().add( ly );
        }


    }


    public ObjectProperty<Color> getGridColorPropFX() {
        return this.fxColorGridProp;
    }
}
