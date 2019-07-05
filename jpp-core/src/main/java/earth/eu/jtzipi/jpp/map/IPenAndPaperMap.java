package earth.eu.jtzipi.jpp.map;

import earth.eu.jtzipi.jpp.cell.IPenAndPaperCell;

import java.util.List;

/**
 * Pen And Paper Level map.
 *
 *
 *
 */
public interface IPenAndPaperMap {

    String getName();

    int getDimX();

    int getDimY();

    int getLevel();

    IPenAndPaperCell getCell( int row, int column );

    List<IPenAndPaperCell> getCells();
}
