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

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import earth.eu.jtzipi.jpp.IO;
import earth.eu.jtzipi.jpp.map.*;
import earth.eu.jtzipi.jpp.ui.tree.ITreeNodeInfo;
import earth.eu.jtzipi.jpp.ui.tree.TreeNodeInfoCell;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.controlsfx.control.StatusBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;


/**
 * Main panel.
 *
 * @author jTzipi
 */
public final class MainPane extends Pane {

    private static final Logger LOG = LoggerFactory.getLogger( "MainPane" );

    /**
     * Single main pane instance.
     */
    private static final MainPane SINGLETON = new MainPane();

    // pen and paper realm
    private IPenAndPaperRealm ppRealm;
    // main pane
    private BorderPane mainPane;
    // Dimension of grid spinner
    private Spinner<Short> xDimSpin;
    private Spinner<Short> yDimSpin;

    private NumberBinding mainPanePrefWidthBind;
    private NumberBinding mainPanePrefHeightBind;

    private ScrollPane sp;
    private MapGeoPropVO mapGeo;
    /**
     * MainPane.
     */
    private MainPane() {
        createMainPane();
    }

    /**
     * Single instance of main pane.
     *
     * @return main pane
     */
    public static MainPane create() {
        return SINGLETON;
    }

    private void createMainPane() {


        // TileProperties.setLength( 70D );

        ppRealm = PenAndPaperRealm.of( "D&D" );
        IPenAndPaperSite pp = PenAndPaperSite.of( "Tzipi" );
        ppRealm.addSite( pp );

        IPenAndPaperMap pplm = PenAndPaperLevelMap.of( 10, 17, 0, "Gysi" );

        pp.addMap( pplm );
        MapGeoPropVO mgp = MapGeoPropVO.of( pplm );
        MapPane mapP = new MapPane( mgp );
        MapEdgePane westEdgePane = MapEdgePane.westEdge( mgp );
        MapEdgePane northEdgePane = MapEdgePane.northEdge( mgp );

        ToolBar toolBar = createMainToolbar();
        toolBar.prefWidthProperty().bind( prefWidthProperty() );

        TreeView<ITreeNodeInfo> ppRealmTree = new TreeView<>();
        ppRealmTree.setCellFactory( callback ->
                new TreeNodeInfoCell()
        );

        TreeItem<ITreeNodeInfo> root = createRealmTreeRoot();

        ppRealmTree.setRoot( root );

        xDimSpin = new Spinner<>( pplm.getDimX(), Short.MAX_VALUE, pplm.getDimX() );
        xDimSpin.setPrefWidth( 77D );

        yDimSpin = new Spinner<>( pplm.getDimY(), Short.MAX_VALUE, pplm.getDimY() );
        yDimSpin.setPrefWidth( 77D );

        // xDimSpin.valueProperty().addListener( iv -> mapP.mapPropVO().setTileX( xDimSpin.getValue() ) );
        // yDimSpin.valueProperty().addListener( iv -> mapP.mapPropVO().setTileY( yDimSpin.getValue() ) );

        mgp.fxDimYProp().bind( yDimSpin.valueProperty() );
        mgp.fxDimXProp().bind( xDimSpin.valueProperty() );

        Label xTileLab = new Label( "X" );
        Label yTileLab = new Label( "Y" );
        toolBar.getItems().addAll( xTileLab, xDimSpin, yTileLab, yDimSpin );

        StatusBar statusBar = new StatusBar();

        mainPanePrefWidthBind = PenAndPaperPropertiesFX.WINDOW_WIDTH_PROP_FX.subtract( 100D );
        mainPanePrefHeightBind = PenAndPaperPropertiesFX.WINDOW_HEIGHT_PROP_FX.subtract( 100D );
        AvivPane aviP = new AvivPane( mgp );
        StackPane map = new StackPane( mapP, aviP );

        // Scrollpane for map pane
        sp = new ScrollPane( map );
        sp.setPannable( true );
        sp.prefWidthProperty().bind( mainPanePrefWidthBind );
        sp.prefHeightProperty().bind( mainPanePrefHeightBind );
        MapPropertiesFX.FX_MAP_PANE_VIEWPORT_BOUNDS_PROP.bind( sp.viewportBoundsProperty() );


        sp.hvalueProperty().addListener( ( obs, ivo, ivn ) -> System.out.println( "" + ( ivn.doubleValue() * ( mapP.getPrefWidth() - sp.getViewportBounds().getWidth() ) ) ) );



        // main pane
        mainPane = new BorderPane();
        //mainPane.setPrefSize( 700D, 700D );
        mainPane.prefWidthProperty().bind( mainPanePrefWidthBind );
        mainPane.prefHeightProperty().bind( mainPanePrefHeightBind );
        mainPane.setCenter( sp );
        mainPane.setTop( toolBar );
        mainPane.setBottom( statusBar );

        Image bg = null;
        try {
            bg = IO.loadImageFromRes( Paths.get( "bg/bg_01.jpg" ) );
        } catch ( IOException e ) {

        }


        BackgroundImage bgIm = new BackgroundImage( bg, null, null, null, null );
        setBackground( new Background( bgIm ) );


        // getChildren().setAll(  t1, t );

        getChildren().setAll( mainPane );
    }


    private IPenAndPaperRealm createRealm() {

        return PenAndPaperRealm.of( "Gadi!" );
    }

    private ToolBar createMainToolbar() {
        // north tool bar
        ToolBar toolBar = new ToolBar();


        DoubleProperty twProp = MapPropertiesFX.FX_TILE_WIDTH_PROP;
        BooleanProperty edgeProp = MapPropertiesFX.FX_SHOW_MAP_EDGE_PROP;

        // increase grid size
        MaterialDesignIcon plusIcon = MaterialDesignIcon.PLUS_CIRCLE_OUTLINE;
        // decrease grid size
        MaterialDesignIcon minusIcon = MaterialDesignIcon.MINUS_CIRCLE_OUTLINE;

        // show grid
        MaterialDesignIcon gridIcon = MaterialDesignIcon.GRID;
        // show edge
        MaterialDesignIcon edgeIcon = MaterialDesignIcon.IMAGE_FILTER_NONE;

        RoundLabel gysi = new RoundLabel();


        //gysi.setFill( new Color( 0D,0D,0D,0D ) );
        //gysi.setStroke( Color.grayRgb( 117 ) );
        //gysi.setStrokeWidth( 1D );

        MaterialDesignIconView plus = new MaterialDesignIconView( plusIcon );
        MaterialDesignIconView mi = new MaterialDesignIconView( minusIcon );
        MaterialDesignIconView grid = new MaterialDesignIconView( gridIcon );
        MaterialDesignIconView edge = new MaterialDesignIconView( edgeIcon );

        plus.setGlyphSize( 29D );
        mi.setGlyphSize( 29D );
        grid.setGlyphSize( 29D );
        edge.setGlyphSize( 29D );

        plus.setOnMouseClicked( me -> twProp.setValue( twProp.getValue() + 2D ) );
        mi.setOnMouseClicked( me -> twProp.setValue( twProp.getValue() - 2D ) );

        RoundLabel plusRLabel = new RoundLabel();
        plusRLabel.setGraphic( plus );

        Label tileSizeLab = new Label();
        tileSizeLab.setFont( Font.font( 29D ) );

        tileSizeLab.textProperty().bind( twProp.asString() );

        ToggleButton showEdgeTogB = new ToggleButton();
        showEdgeTogB.setGraphic( edge );
        showEdgeTogB.setSelected( true );
        edgeProp.bind( showEdgeTogB.selectedProperty() );


        toolBar.getItems().setAll( grid, gysi, showEdgeTogB, plusRLabel, mi, tileSizeLab );



        return toolBar;
    }

    private TreeItem<ITreeNodeInfo> createRealmTreeRoot() {

        TreeItem<ITreeNodeInfo> root = new TreeItem<>(MapToTree.realmToInfo( ppRealm ));

        for( IPenAndPaperSite site : ppRealm.getSites() ) {

            TreeItem<ITreeNodeInfo> siteItem = new TreeItem<>( MapToTree.siteToInfo( site ) );
            root.getChildren().add( siteItem );
            // Add all maps
            for( IPenAndPaperMap map : site.getLevelMaps() ){

                siteItem.getChildren().add( new TreeItem<>( MapToTree.mapToInfo( map ) ) );

            }
        }

        return root;
    }
}
