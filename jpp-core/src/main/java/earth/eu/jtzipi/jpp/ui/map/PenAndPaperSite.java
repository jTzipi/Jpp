package earth.eu.jtzipi.jpp.ui.map;

import earth.eu.jtzipi.jpp.map.IPenAndPaperMap;
import earth.eu.jtzipi.jpp.map.IPenAndPaperSite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PenAndPaperSite implements IPenAndPaperSite {

    private String name;
    private List<IPenAndPaperMap> levelMapL;

    PenAndPaperSite( final String nameStr, final List<IPenAndPaperMap> penAndPaperMapList ) {
        this.name = nameStr;
        this.levelMapL = penAndPaperMapList;
    }


    PenAndPaperSite ( final String nameStr ) {
        this(nameStr, new ArrayList<>() );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<IPenAndPaperMap> getLevelMaps() {
        return levelMapL;
    }

    public static PenAndPaperSite of( String nameStr ) {

        return new PenAndPaperSite( nameStr );
    }

    /**
     * Add a new map to this site.
     * @param map
     */
    public void addMap(final IPenAndPaperMap map) {



    }
}
