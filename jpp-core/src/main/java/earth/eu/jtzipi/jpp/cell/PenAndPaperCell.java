package earth.eu.jtzipi.jpp.cell;

import earth.eu.jtzipi.jpp.ui.tile.PenAndPaperPos;

import java.util.Set;

public class PenAndPaperCell implements ICellPenAndPaper {

    int level;
    int x;
    int y;

    private PenAndPaperCell( final int level, final int xPos, final int yPos ) {
        this.level = level;
        this.x = xPos;
        this.y = yPos;
    }


    public static PenAndPaperCell of( int x, int y ) {
        return new PenAndPaperCell( 0, x, y );
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public ICellQuad getNeighbour( PenAndPaperPos penAndPaperPos ) {
        return null;
    }

    @Override
    public void link( ICellQuad cell, boolean bidiProp ) {

    }

    @Override
    public void unlink( ICellQuad cell, boolean bidiProp ) {

    }

    @Override
    public Set<ICellQuad> getLinkedCells() {
        return null;
    }

    @Override
    public Set<ICellQuad> getNeighbours() {
        return null;
    }

    @Override
    public boolean isLinked( ICell cell ) {
        return false;
    }
}
