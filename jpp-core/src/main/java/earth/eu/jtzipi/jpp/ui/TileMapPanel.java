package earth.eu.jtzipi.jpp.ui;

import earth.eu.jtzipi.jpp.ui.tile.PenAndPaperPos;
import earth.eu.jtzipi.jpp.ui.tile.segment.ISegment;
import earth.eu.jtzipi.jpp.ui.tile.segment.PathBuilder;
import earth.eu.jtzipi.jpp.ui.tile.segment.Wall;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


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
        Rectangle tileBG = new Rectangle( w, 50 );
        tileBG.setLayoutX( 0D );


        Rectangle clipFadeWest = new Rectangle( 50, 50 );
        clipFadeWest.setLayoutX( 0D );

        Rectangle clipFadeEast = new Rectangle( 50, 50 );
        clipFadeEast.setLayoutX( w - 50D );


        //Shape clip = Shape.union( tileScroller, clipFadeEast );
        //clip = Shape.union( clip, clipFadeWest );

        Color bgMain = Color.rgb( 255, 255, 255, 0.5D );
        Color bgTopBottom = Color.rgb( 0, 0, 75, 0.75D );

        Color gcOpaque = Color.rgb( 10, 10, 75, 1D );
        Color gcTransparent = Color.rgb( 10, 10, 75, 0.1D );

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


        getChildren().addAll( tileBG, createTilePane(), ScrollKnob.ofWest( 50, 50 ), clipFadeWest );
    }


    private Group createTilePane() {

        Group group = new Group();


        double xoff = -50D;
        for ( ISegment w : Wall.WallSegments.values() ) {
            Shape shape = w.createPath();
            if ( shape != null ) {
                shape.setLayoutX( xoff );
                shape.setLayoutY( 10D );
                shape.setStroke( Color.RED );
                shape.setScaleY( 2D );


                group.getChildren().add( shape );
                xoff += 50;
            }
        }

        return group;
    }

    public static class ScrollKnob extends Group {

        DoubleProperty fxWidthProp;
        DoubleProperty fxHeightProp;
        Shape arrow;
        Rectangle bg;
        PenAndPaperPos ppp;

        ScrollKnob( PenAndPaperPos paperPos, double width, double height ) {
            this.fxHeightProp = new SimpleDoubleProperty( this, "", height );
            this.fxWidthProp = new SimpleDoubleProperty( this, "", width );

            createScrollKnob();
        }

        static ScrollKnob ofWest( double w, double h ) {

            return new ScrollKnob( PenAndPaperPos.W, w, h );
        }

        void createScrollKnob() {

            bg = new Rectangle();
            bg.widthProperty().bind( fxWidthProp );
            bg.heightProperty().bind( fxHeightProp );
            arrow = PathBuilder.create().mxy( 46D, 5D ).lxy( 10D, 25D ).lxy( 46D, 45D ).build();
            arrow.setFill( Color.LIGHTBLUE );

            Color gcOpaque = Color.rgb( 10, 10, 75, 1D );
            Color gcTransparent = Color.rgb( 10, 10, 75, 0.1D );

            LinearGradient westFade = new LinearGradient( 0D, 0D, 1D, 0D, true, CycleMethod.NO_CYCLE, new Stop( 0D, gcOpaque ), new Stop( 1D, gcTransparent ) );

            bg.setFill( westFade );
            getChildren().addAll( bg, arrow );
        }
    }
}
