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


import earth.eu.jtzipi.jpp.cell.ICellPenAndPaper;
import earth.eu.jtzipi.jpp.ui.tile.PenAndPaperPos;
import earth.eu.jtzipi.jpp.ui.tile.segment.PathBuilder;
import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

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
    private EnumMap<PenAndPaperPos, Shape> overflowEMap; // Shape displaying a zigzag line to indicte overflow of scroll pane


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

    private static Shape createOverflow( PenAndPaperPos pp, int pike, double fenceHeight ) {

        // create a new empty path starting [0,0]
        PathBuilder pb = PathBuilder.create();

        double fence = 25D; // fence distance
        double step = 24D;  // step distance
        //  fence height
        pb.lx( fenceHeight );

        // about 2400 pixel
        for ( int i = 0; i < pike; i++ ) {

            double x = i % 2 == 0 ? fenceHeight + fence : fenceHeight;
            double ym = i * step;

            pb.lxy( x, ym );
        }
        pb.lx( 0D );
        pb.ly( 0D );
        pb.close();
        Shape raw = pb.build();
        // transform for position
        List<Transform> tL = new ArrayList<>();

        switch ( pp ) {
            // east we have to rotate and translate
            case E:
                //tL.add( Transform.rotate( 180D, 0D, 500D ) );
                tL.add( Transform.translate( 280D, 100D ) );
                raw.setFill( Color.RED );
                break;
            case S:

                tL.add( Transform.translate( 180D, 500D ) );

                break;
            case N:
                //tL.add( Transform.rotate( 90D, 0D, 0D ) );
                break;
            default:
                ;
        }
        // if transformation
        if ( !tL.isEmpty() ) {
            raw.getTransforms().addAll( tL );
        }


        return raw;
    }

    private void onClicked( ICellPenAndPaper cellOld, ICellPenAndPaper cellNew ) {


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
        getChildren().add( createOverflow( PenAndPaperPos.S, 50, 55D ) );
        getChildren().add( createOverflow( PenAndPaperPos.N, 50, 55D ) );
        getChildren().add( createOverflow( PenAndPaperPos.E, 50, 55D ) );
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
