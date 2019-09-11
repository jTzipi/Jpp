package earth.eu.jtzipi.jpp.ui;

import earth.eu.jtzipi.jpp.ui.tile.PenAndPaperPos;
import earth.eu.jtzipi.jpp.ui.tile.segment.ISegment;
import earth.eu.jtzipi.jpp.ui.tile.segment.PathBuilder;
import earth.eu.jtzipi.jpp.ui.tile.segment.Wall;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;


/**
 * @author jTzipi
 */
public class TileMapPanel extends Pane {


    TileMapPanel() {
        setPrefHeight( 100D );

        createTileMapPanel();
    }

    private void createTileMapPanel() {

        double w = PenAndPaperPropertiesFX.WINDOW_WIDTH - 100D;
        double h = 100D;

        setPrefHeight( h );
        setPrefWidth( w );
        Rectangle tileBG = new Rectangle( w - 100D, 50 );
        tileBG.setLayoutX( 50D );


        Rectangle clipFadeWest = new Rectangle( 50, 50 );
        clipFadeWest.setLayoutX( 50D );

        Rectangle clipFadeEast = new Rectangle( 50, 50 );
        clipFadeEast.setLayoutX( w - 100D );




        Color bgMain = Color.rgb( 255, 255, 255, 0.5D );
        Color bgTopBottom = Color.rgb( 0, 0, 75, 0.75D );

        Color gcOpaque = Color.rgb( 10, 10, 75, 1D );
        Color gcTransparent = Color.rgb( 10, 10, 75, 0D );

        LinearGradient westFade = new LinearGradient( 0D, 0D, 1D, 0D, true, CycleMethod.NO_CYCLE, new Stop( 0D, gcOpaque ), new Stop( 1D, gcTransparent ) );

        LinearGradient eastFade = new LinearGradient( 0D, 0D, 1D, 0D, true, CycleMethod.NO_CYCLE, new Stop( 0D, gcTransparent ), new Stop( 1D, gcOpaque ) );

        LinearGradient bgG = new LinearGradient( 0D, 0D, 0D, 1D, true, CycleMethod.NO_CYCLE,

                new Stop( 0D, bgTopBottom ),
                new Stop( 0.1D, bgMain ),
                new Stop( 0.9D, bgMain ),
                new Stop( 1D, bgTopBottom ) );

        tileBG.setFill( bgG );

        clipFadeEast.setFill( eastFade );

        clipFadeWest.setFill( westFade );
        Group tilePane = createTilePane();
        Rectangle clip = new Rectangle( w - 100D, h );
        clip.setLayoutX( 50D );
        tilePane.setClip( clip );

        ScrollKnob wknob = ScrollKnob.of( PenAndPaperPos.W, 50D, 50D );
        ScrollKnob eKnob = ScrollKnob.of( PenAndPaperPos.E, 50D, 50D );
        eKnob.setLayoutX( w - 50 );
        getChildren().addAll( wknob, tileBG, tilePane, clipFadeWest, clipFadeEast, eKnob );
    }



    private Group createTilePane() {

        Group group = new Group();


        double xoff = 0D;

        for ( ISegment w : Wall.WallSegments.values() ) {
            Shape shape = w.createPath();
            if ( shape != null ) {
                shape.setLayoutX( xoff );
                shape.setLayoutY( 10D );
                shape.setStroke( Color.RED );
                shape.setScaleY( 2D );

                Line sepa = new Line( xoff, 0D, xoff, 50D );
                sepa.setStrokeWidth( 0.5D );
                sepa.setStroke( Color.rgb( 0, 0, 75, 0.75D ) );
                group.getChildren().add( shape );
                group.getChildren().add( sepa );
                xoff += 50;
            }
        }

        return group;
    }

    /**
     * A scroll knob control.
     */
    public static class ScrollKnob extends Group {

        public static final double WIDTH_DEF = 50D;
        public static final double HEIGHT_DEF = 50D;
        Rectangle bg;               // knob background
        Arc arc;                    // arc border
        PenAndPaperPos ppp;         // position
        Color fill = Color.rgb( 10, 10, 75, 1D );
        Color arrowNormal = Color.rgb( 60, 60, 125, 1D );
        Color arrowHighlight = Color.LIGHTBLUE;
        private double w;           // width
        private double h;           // height
        private Shape arrow;                // arrow


        ScrollKnob( PenAndPaperPos paperPos, double width, double height ) {
            this.w = width;
            this.h = height;
            this.ppp = paperPos;
            createScrollKnob();
        }

        /**
         * Create a knob for position.
         *
         * @param penAndPaperPos pos
         * @param w              width
         * @param h              height
         * @return scroll knob
         */
        static ScrollKnob of( PenAndPaperPos penAndPaperPos, double w, double h ) {

            return new ScrollKnob( penAndPaperPos, w, h );
        }

        static ScrollKnob of( PenAndPaperPos penAndPaperPos ) {

            return of( penAndPaperPos, ScrollKnob.WIDTH_DEF, ScrollKnob.HEIGHT_DEF );
        }

        void createScrollKnob() {


            double mid = w / 2D;
            double halfmid = mid / 2D;
            double dist = 12D;      // offset
            double xUp = w - dist;
            double yUp = dist;
            double yMid = mid;
            double xMid = dist;
            double xDown = xUp;
            double yDown = h - dist;

            // create arrow
            arrow = PathBuilder.create().mxy( xUp, yUp ).lxy( yMid, yMid ).lxy( xDown, yDown ).strokeWidth( 4D ).strokeJoin( StrokeLineJoin.ROUND ).build();
            arrow.setStroke( Color.LIGHTBLUE );
            // background
            bg = new Rectangle( w - halfmid, h );
            bg.setFill( fill );
            // rounded border
            arc = new Arc( dist, mid, dist, mid, 90, 180D );
            arc.setFill( fill );
            // rotate
            switch ( ppp ) {

                case E:
                    bg.setLayoutX( 0D );
                    arc.setRotate( 180D );
                    arc.setLayoutX( w - halfmid );
                    arrow.setRotate( 180D );
                    break;

                case W:
                    // set layout +1 because of 1 pixel gap between arc and bg
                    arc.setLayoutX( 0D );
                    bg.setLayoutX( halfmid );
                    arrow.setTranslateX( -halfmid );
                    break;
                case S:
                    arc.setRotate( 90D );
                    bg.setLayoutY( 0D );
                    arc.setLayoutY( h - halfmid );
                    arrow.setRotate( 90D );
                    break;
                case N:
                    arc.setRotate( 270D );
                    bg.setLayoutY( mid );
                    arc.setLayoutY( dist );
                    arrow.setRotate( 270D );
                    break;
                default:
                    break;
            }


            getChildren().addAll( arc, bg, arrow );
        }
    }
}
