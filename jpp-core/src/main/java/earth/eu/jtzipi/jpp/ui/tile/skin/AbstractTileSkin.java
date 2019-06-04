package earth.eu.jtzipi.jpp.ui.tile.skin;

import earth.eu.jtzipi.jpp.ui.tile.Tile;
import earth.eu.jtzipi.jpp.util.FXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SkinBase;

/**
 *
 * @author jTzipi
 */
public abstract class AbstractTileSkin  {

    // Width
    static double W_MIN = 16;
    static double W_PREF = 64;
    static double W_MAX = 512;


    // Height
    static double H_MIN = 16;
    static double H_PREF = 64;
    static double H_MAX = 512;

    /** Width of tile painting. */
    double w;
    /** Height of tile painting. */
    double h;

    /**
     *
     * @param control
     */
    protected AbstractTileSkin( final Tile control ) {

    }

    /**
     * Ensure that this tile have a width and height.
     */
    protected void ensureWidthAndHeight() {
        Tile t = null;

        boolean wset = FXUtils.isWidthSet(t);
        boolean pwset = FXUtils.isPrefWidthSet(t);
        boolean hset = FXUtils.isHeightSet( t );
        boolean phset = FXUtils.isPrefHeightSet( t );

        // if one of width or height are not set
        if( !wset || !hset || !pwset || !phset ) {

            // set width and height
            this.w = pwset ? t.getPrefWidth() : W_PREF;
            this.h = phset ? t.getPrefHeight() : H_PREF;

            t.setPrefSize( w, h );
        } else {

            this.w = t.getWidth();
            this.h = t.getHeight();
        }
    }

    double getHeight() {
        return h;
    }

    double getWidth() {
        return w;
    }

    /**
     * Init size properties and so on .
     */
    protected abstract void init();
    /**
     * Triggered when resize.
     */
    protected abstract void resize();

    /**
     * Triggered when redrawed.
     */
    protected abstract void redraw();

/*    @Override
    protected double computeMinWidth( double height, double topInset, double rightInset, double bottomInset, double leftInset ) {
        return W_MIN;
    }

    @Override
    protected double computeMinHeight( double width, double topInset, double rightInset, double bottomInset, double leftInset ) {
        return H_MIN;
    }

    @Override
    protected double computeMaxWidth( double height, double topInset, double rightInset, double bottomInset, double leftInset ) {
        return W_MAX;
    }

    @Override
    protected double computeMaxHeight( double width, double topInset, double rightInset, double bottomInset, double leftInset ) {
        return H_MAX;
    }

*/

}
