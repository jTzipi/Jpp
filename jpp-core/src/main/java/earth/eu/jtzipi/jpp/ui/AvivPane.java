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


import earth.eu.jtzipi.jpp.cell.IPenAndPaperCell;
import earth.eu.jtzipi.jpp.ui.tile.segment.PathBuilder;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pane above Map pane to display info.
 *
 * @author jTzipi
 */
public class AvivPane extends Pane {

    private static final String FX_GRID_COLOR_PROP = "FX_GRID_COLOR_PROP";
    private static final Color DEF_COLOR_GRID = Color.RED;

    private MapGeoPropVO geoPropVO; // geo prop


    private ObjectProperty<Color> fxColorGridProp = new SimpleObjectProperty<>( this, FX_GRID_COLOR_PROP, DEF_COLOR_GRID ); // grid color
    private ObjectProperty<Color> fxColorBGRectProp; // background rect color


    private Shape hover;    // Shape to indicate hover position
    private Shape clicked;  // Shape to indicate clicked position
    private FadeTransition clickedFT;

    /**
     * Avi
     *
     * @param geoPropertyVO
     */
    AvivPane( final MapGeoPropVO geoPropertyVO ) {
        this.geoPropVO = geoPropertyVO;


        setMouseTransparent( true );
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
        // on tile change
        MapPropertiesFX.FX_TILE_WIDTH_PROP.addListener( iv -> create() );
        MapPropertiesFX.FX_CLICKED_PPC_PROP.addListener( ( obs, cellOld, cellNew ) ->
                onClicked( cellOld, cellNew )
        );


    }


    private void onClicked( IPenAndPaperCell cellOld, IPenAndPaperCell cellNew ) {


        //Logger log = LoggerFactory.getLogger( "Gadi" );
        //log.error( " " + cellOld + " " + cellNew );


        if ( null != cellNew ) {
            clicked.setLayoutX( MapPropertiesFX.FX_TILE_HOVER_OFF_X_BIND.subtract( 2D ).doubleValue() );
            clicked.setLayoutY( MapPropertiesFX.FX_TILE_HOVER_OFF_Y_BIND.subtract( 2D ).doubleValue() );
            clicked.setVisible( true );
        } else {


            clicked.setVisible( false );

        }

    }

    private void create() {
        getChildren().clear();

        // FX_TILE_WIDTH_PROP.
        DoubleProperty twProp = MapPropertiesFX.FX_TILE_WIDTH_PROP;
        // tile width
        double tw = twProp.doubleValue();
        // offset
        double offX = geoPropVO.fxOffsetXBinding().get();
        double offY = geoPropVO.fxOffsetYBinding().get();
        double w = geoPropVO.fxWidthBinding().get();
        double h = geoPropVO.fxHeightBinding().get();

        // all column
        for ( int ix = 0; ix < geoPropVO.fxDimXProp().get(); ix++ ) {

            double px = offX + ix * tw;
            Line lx = new Line( px, offY, px, h );

            lx.setStroke( fxColorGridProp.getValue() );
            lx.setStrokeWidth( 0.1D );
            getChildren().add( lx );
        }
        // all row
        for ( int iy = 0; iy < geoPropVO.fxDimYProp().get(); iy++ ) {

            double py = offY + iy * tw;
            Line ly = new Line( offX, py, w, py );

            ly.setStrokeWidth( 0.1D );
            ly.setStroke( fxColorGridProp.getValue() );
            getChildren().add( ly );
        }

        double ls = MapPropertiesFX.SEGMENT_LEN_SMALL.doubleValue();
        double width = twProp.doubleValue();

        double ps1 = ls + 2D;
        double ps2 = width + 2D - ls;
        double ps3 = width + 4D;

        // hover path
        hover = PathBuilder.create().strokeWidth( 1.6D ).mx( 0D ).my( 0D )
                .lx( ps1 ).mx( ps2 ).lx( ps3 ) // north
                .ly( ps1 ).my( ps2 ).ly( ps3 ) // east
                .lx( ps2 ).mx( ps1 ).lx( 0D ) // south
                .ly( ps2 ).my( ps1 ).ly( 0D ) // west
                .build();

        hover.setStroke( Color.rgb( 254, 52, 52 ) );
        hover.layoutXProperty().bind( MapPropertiesFX.FX_TILE_HOVER_OFF_X_BIND.subtract( 2D ) );
        hover.layoutYProperty().bind( MapPropertiesFX.FX_TILE_HOVER_OFF_Y_BIND.subtract( 2D ) );

        clicked = PathBuilder.create().strokeWidth( 1.6D ).mx( 0D ).my( 0D )
                .lx( ps1 ).mx( ps2 ).lx( ps3 ) // north
                .ly( ps1 ).my( ps2 ).ly( ps3 ) // east
                .lx( ps2 ).mx( ps1 ).lx( 0D ) // south
                .ly( ps2 ).my( ps1 ).ly( 0D ) // west
                .build();

        clicked.setStroke( Color.RED );
        clicked.setVisible( false );


//        bgR = new Rectangle();
//        bgR.setArcHeight( 10D );
//        bgR.setArcWidth( 10D  );
//        bgR.fillProperty().bind( fxColorBGRectProp );
//        bgR.widthProperty().bind( twProp.subtract( 4D ) );
//        bgR.heightProperty().bind( twProp.subtract( 4D ) );
//        bgR.layoutXProperty().bind( MapPropertiesFX.FX_TILE_HOVER_OFF_X_BIND.add( 2D ) );
//        bgR.layoutYProperty().bind( MapPropertiesFX.FX_TILE_HOVER_OFF_Y_BIND.add( 2D ) );
//
//
        getChildren().add( hover );
        getChildren().add( clicked );
    }

    /**
     * Return grid Color property.
     *
     * @return grid color property
     */
    public final ObjectProperty<Color> getGridColorPropFX() {

        return this.fxColorGridProp;
    }
}
