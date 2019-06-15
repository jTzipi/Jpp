package earth.eu.jtzipi.jpp.ui.map;

import com.sun.prism.PhongMaterial;
import earth.eu.jtzipi.jpp.ui.tile.Position2D;

import earth.eu.jtzipi.jpp.ui.tile.skin.DefaultWallEdgeSkin;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;


public class MapEdge extends Control {

    Position2D pos2D;
    EdgeType edgeType;

    int tilesPerEdge;
    DoubleProperty fxOffsetProp = new SimpleDoubleProperty(this, "", 50D );


    private MapEdge(final Position2D position2D, final int tilesPerEdge) {
        this.pos2D = position2D;
        this.tilesPerEdge = tilesPerEdge;
        init();
    }

    public static MapEdge of( Position2D pos2D, int tilesPerEdge ) {

        return new MapEdge( pos2D, tilesPerEdge );
    }

    private void init() {



    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return DefaultWallEdgeSkin.of( this );
    }

    public int getTilesPerEdge() {
        return tilesPerEdge;
    }

    public Position2D getPosition() {
        return pos2D;
    }
    public final DoubleProperty getOffsetPropFX() {
        return this.fxOffsetProp;
    }
}
