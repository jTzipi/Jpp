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

    int MIN_X = 1;
    int MIX_Y = 1;
    int MIN_LEVEL = -999;
    int MAX_LEVEL = 1_000;
    /**
     * Name of map.
     * @return name
     */
    String getName();


    String getDescription();;
    /**
     * X dimension.
     * @return dim x
     */
    int getDimX();

    /**
     * Y dimension.
     * @return dim y
     */
    int getDimY();

    /**
     * Level of this map.
     * @return level
     */
    int getLevel();

    IPenAndPaperCell getCell( int row, int column );

    /**
     * Cells of this map.
     * @return cell list
     */
    List<IPenAndPaperCell> getCells();
}
