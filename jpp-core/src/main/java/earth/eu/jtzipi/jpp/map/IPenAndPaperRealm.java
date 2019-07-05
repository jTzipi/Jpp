package earth.eu.jtzipi.jpp.map;

import java.util.List;
import java.util.Map;

/**
 * Pen and Paper Realm.
 * <p>
 *     A pen and paper realm is a complete set of sites and
 *     level maps.
 * </p>
 */
public interface IPenAndPaperRealm {

    String getDescription();

    List<IPenAndPaperSite> getSites();

}
