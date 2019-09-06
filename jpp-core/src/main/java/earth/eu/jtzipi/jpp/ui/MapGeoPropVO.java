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
import earth.eu.jtzipi.jpp.cell.ICellQuad;
import earth.eu.jtzipi.jpp.map.IPenAndPaperMap;
import earth.eu.jtzipi.jpp.ui.tile.PenAndPaperPos;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Placeholder for geometry related properties of map .
 */
public final class MapGeoPropVO {
    private static final Set<KeyCode> KEY_CODE = new HashSet<>();


    private static final String FX_PROP_PANE_WIDTH = "FX_PROP_PANE_WIDTH";
    private static final String FX_PROP_PANE_HEIGHT = "FX_PROP_PANE_HEIGHT";
    private static final String FX_PROP_OFFSET_X = "FX_PROP_OFFSET_X";
    private static final String FX_PROP_OFFSET_Y = "FX_PROP_OFFSET_Y";
    private static final String FX_PROP_TILE_X = "FX_PROP_TILE_X";
    private static final String FX_PROP_TILE_Y = "FX_PROP_TILE_Y";


    /**
     * Width of pane binding.
     */
    private DoubleBinding fxWidthBinding;
    /**
     * Height of pane binding.
     */
    private DoubleBinding fxHeightBinding;
    /**
     * X offset to draw tiles .
     */
    private DoubleBinding fxOffsetXBinding;
    /**
     * Y offset to draw tiles .
     */
    private DoubleBinding fxOffsetYBinding;

    /**
     * tiles x.
     */
    private IntegerProperty fxDimXProp;
    /**
     * tile y.
     */
    private IntegerProperty fxDimYProp;
    /**
     * content of map.
     */
    private IPenAndPaperMap ppMap;
    private ObservableMap<ICellPenAndPaper, PenAndPaperPos> hlPPM = FXCollections.observableHashMap();
    private PenAndPaperPos sel;

    private DoubleProperty fxWestOverflowProp;
    private DoubleProperty fxEastOverflowProp;

    static {
        KEY_CODE.add( KeyCode.LEFT );
    }

    private MapGeoPropVO( IPenAndPaperMap penAndPaperMap ) {
        this.ppMap = penAndPaperMap;
    }

    /**
     * Create a geo prop.
     *
     * @param penAndPaperMap pen and paper property vo
     * @return new instance of pen and paper map
     */
    public static MapGeoPropVO of( IPenAndPaperMap penAndPaperMap ) {
        Objects.requireNonNull( penAndPaperMap );

        MapGeoPropVO mgp = new MapGeoPropVO( penAndPaperMap );

        mgp.init();

        return mgp;
    }

    private void init() {

        final DoubleBinding bindOffset = MapPropertiesFX.FX_TILE_OFFSET_BIND;
        final DoubleProperty gapNorth = MapPropertiesFX.FX_GAP_EDGE_NORTH_PROP;
        final DoubleProperty gapWest = MapPropertiesFX.FX_GAP_EDGE_WEST_PROP;
        // tile width
        final DoubleProperty tw = MapPropertiesFX.FX_TILE_WIDTH_PROP;
        // tile x and y dimension
        this.fxDimXProp = new SimpleIntegerProperty( this, FX_PROP_TILE_X, ppMap.getDimX() );
        this.fxDimYProp = new SimpleIntegerProperty( this, FX_PROP_TILE_Y, ppMap.getDimY() );
        // width = rows * tile size + offset + left gap
        this.fxWidthBinding = tw.multiply( fxDimXProp ).add( bindOffset ).add( gapWest ).add( gapWest );
        // height = rows * tile size + offset + top gap
        this.fxHeightBinding = tw.multiply( fxDimYProp ).add( bindOffset ).add( gapNorth );

        this.fxOffsetXBinding = bindOffset.add( gapWest );
        this.fxOffsetYBinding = bindOffset.add( gapNorth );

        MapPropertiesFX.FX_CLICKED_PPC_PROP.addListener( ( obs, oldPPC, newPPC ) -> {
            hlPPM.put( newPPC, PenAndPaperPos.C );
        } );

        MapPropertiesFX.FX_KEY_EVENT_PROP.addListener( ( o, ok, nk ) -> onKeyTyped( ok, nk ) );
    }

    /**
     * Width of map binding.
     *
     * @return width binding
     */
    DoubleBinding fxWidthBinding() {
        return this.fxWidthBinding;
    }

    DoubleBinding fxHeightBinding() {
        return this.fxHeightBinding;
    }

    IntegerProperty fxDimXProp() {
        return this.fxDimXProp;
    }

    IntegerProperty fxDimYProp() {
        return this.fxDimYProp;
    }

    DoubleBinding fxOffsetXBinding() {
        return this.fxOffsetXBinding;
    }

    DoubleBinding fxOffsetYBinding() {
        return this.fxOffsetYBinding;
    }

    DoubleProperty fxEastOverflowProp() {
        return this.fxEastOverflowProp;
    }

    ICellQuad getNeighbour( ICellQuad cell, PenAndPaperPos pos2D ) {
        Objects.requireNonNull( cell );
        int posX = cell.getX();
        int posY = cell.getY();
        switch ( pos2D ) {
            case E:
                posX++;
                break;
            case W:
                posX--;
                break;
            case S:
                posY--;
                break;
            case N:
                posY++;
                break;


        }

        return ppMap.isCell( posX, posY ) ? ppMap.getCell( posX, posY ) : ICellQuad.UnknownCell.SINGLETON;
    }

    private void onKeyTyped( KeyEvent oldKeyEv, KeyEvent kevNew ) {


        KeyCode kcode = kevNew.getCode();
        if ( KEY_CODE.contains( kcode ) ) {

            switch ( kcode ) {
                case LEFT:
                    // go left from this ppc
                    if ( PenAndPaperPos.W == sel ) {
                        // set new selected ppc

                    }
            }

        }

    }
}
