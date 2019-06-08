package earth.eu.jtzipi.jpp.ui.map;

import com.sun.prism.PhongMaterial;
import earth.eu.jtzipi.jpp.ui.tile.Position2D;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Control;


public class MapEdge extends Control {

    Position2D pos2D;
    EdgeType edgeType;

    int tilesPerEdge;



    private MapEdge(final Position2D position2D, final int tilesPerEdge) {
        this.pos2D = position2D;
        this.tilesPerEdge = tilesPerEdge;
        init();
    }

    private void init() {



    }

    public int getTilesPerEdge() {
        return tilesPerEdge;
    }

    public Position2D getPosition() {
        return pos2D;
    }
}
