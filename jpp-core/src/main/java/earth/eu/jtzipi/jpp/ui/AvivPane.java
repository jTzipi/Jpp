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
import earth.eu.jtzipi.jpp.util.FXUtils;
import javafx.beans.property.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;

import java.util.EnumMap;

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


    private EnumMap<PenAndPaperPos, BorderMask> overflowEMap; // Shape displaying a zigzag line to indicate overflow of scroll pane




    /**
     * Avi
     *
     * @param geoPropertyVO
     */
    AvivPane( final MapGeoPropVO geoPropertyVO ) {
        this.geoPropVO = geoPropertyVO;
        this.overflowEMap = new EnumMap<>( PenAndPaperPos.class );

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

        overflowEMap.put( PenAndPaperPos.W, BorderMask.of( 50, 50, PenAndPaperPos.W ) );
        overflowEMap.put( PenAndPaperPos.E, BorderMask.of( 50, 50, PenAndPaperPos.E ) );
        overflowEMap.put( PenAndPaperPos.S, BorderMask.of( 50, 50, PenAndPaperPos.S ) );
        overflowEMap.put( PenAndPaperPos.N, BorderMask.of( 50, 50, PenAndPaperPos.N ) );
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
        double gapWest = MapPropertiesFX.FX_GAP_EDGE_WEST_PROP.doubleValue();
        double gapNorth = MapPropertiesFX.FX_GAP_EDGE_NORTH_PROP.doubleValue();
        // tile width
        double tw = twProp.doubleValue();
        // offset
        double offX = geoPropVO.fxOffsetXBinding().get();
        double offY = geoPropVO.fxOffsetYBinding().get();
        double w = geoPropVO.fxWidthBinding().subtract( gapWest ).get();
        double h = geoPropVO.fxHeightBinding().subtract( gapNorth ).get();

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
        getChildren().add( overflowEMap.get( PenAndPaperPos.N ).getShape() );
        getChildren().add( overflowEMap.get( PenAndPaperPos.E ).getShape() );
        getChildren().add( overflowEMap.get( PenAndPaperPos.S ).getShape() );
    }
    /**
     * Return grid Color property.
     *
     * @return grid color property
     */
    public final ObjectProperty<Color> getGridColorPropFX() {

        return this.fxColorGridProp;
    }

    static final class BorderMask {

        public static final double FENCE_MAX = 100D;
        public static final double FENCE_DEF = 51D;
        public static final double FENCE_MIN = 7D;
        /**
         * is mask visible
         */
        BooleanProperty fxEnabledProp = new SimpleBooleanProperty( this, "FX_MASK_ENABLED_PROP", false );
        private DoubleProperty fxFenceHeightProp;
        private double fence;
        private int pike;
        private DoubleProperty fxOffsetXProp = new SimpleDoubleProperty( this, "", -1000D );
        private DoubleProperty fxOffsetYProp = new SimpleDoubleProperty( this, "", -1000D );
        private PenAndPaperPos pos;
        // mask
        private Shape mask;

        private BorderMask( double fenceHeight, int pikes, PenAndPaperPos ppp ) {
            this.fence = fenceHeight;
            this.pike = pikes;
            this.pos = ppp;
            this.fxFenceHeightProp = new SimpleDoubleProperty( this, "FX_FENCE_HEIGHT_PROP", fenceHeight );
        }

        static BorderMask of( double fenceHeight, int pike, PenAndPaperPos pos ) {
            fenceHeight = FXUtils.clamp( fenceHeight, FENCE_MIN, FENCE_MAX );
            pike = FXUtils.clamp( pike, 1, 1000 );

            BorderMask borderMask = new BorderMask( fenceHeight, pike, pos );
            borderMask.init();

            return borderMask;
        }

        /**
         * Create mask with 'fence-shape' for clipping.
         *
         * @param pp          position
         * @param pike        pikes
         * @param fenceHeight height
         * @return fence shape
         */
        private static Shape createOverflow( PenAndPaperPos pp, int pike, double fenceHeight ) {

            // create a new empty path starting [0,0]
            PathBuilder pb = PathBuilder.create();

            double fence = 25D; // fence distance
            double step = 24D;  // step distance

            pb.lx( fenceHeight ); //  fence height
            double length = pike * step;

            // about 2400 pixel
            for ( int i = 0; i < pike; i++ ) {

                double x = i % 2 == 0 ? fenceHeight + fence : fenceHeight;
                double ym = i * step;

                pb.lxy( x, ym );
            }
            // finally
            pb.lx( 0D ); // move back to start
            pb.ly( 0D );
            pb.close();


            Shape raw = pb.build();
            // transform for position

            //System.err.println( "PW " + getPrefWidth() + " " + getPrefHeight() );
            switch ( pp ) {

                case W: // nothing todo
                    break;
                // east we need to scale and translate
                case E:

                    raw.setScaleX( -1D );
                    raw.setTranslateX( 70D );
                    raw.setFill( Color.color( 0.12D, 0.1D, 1D, 0.7D ) );
                    break;
                // south we need to rotate and scale
                case S:

                    raw.setRotate( 270D );
                    raw.setTranslateX( 500D );
                    raw.setStroke( Color.rgb( 11, 11, 248 ) );
                    break;

                case N:
                    // north we need scale and rotate
                    Affine affine = new Affine();
                    affine.appendScale( -1D, 1D );          // second we mirror around y axis
                    affine.appendRotation( 90D );             // first we rotate 90°
                    //affine.appendRotation( 90D );


                    raw.getTransforms().add( affine );
                    raw.setFill( Color.DARKSEAGREEN );
                    // raw.setStroke( Color.CORNSILK );
                    break;
                default:
                    ;
            }


            return raw;
        }

        private void init() {
            this.mask = createOverflow( pos, pike, fence );
        }

        private Shape getShape() {
            return this.mask;
        }

        BooleanProperty getFxEnabledProp() {
            return this.fxEnabledProp;
        }
    }
}
