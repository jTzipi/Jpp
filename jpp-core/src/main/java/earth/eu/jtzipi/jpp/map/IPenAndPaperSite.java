package earth.eu.jtzipi.jpp.map;

import java.util.List;
import java.util.Set;

/**
 * Pen and Paper Site.
 * <p>
 *     A site contains one or more maps.;
 * </p>
 */
public interface IPenAndPaperSite {

    String getName();

    List<IPenAndPaperMap> getLevelMaps();

}
