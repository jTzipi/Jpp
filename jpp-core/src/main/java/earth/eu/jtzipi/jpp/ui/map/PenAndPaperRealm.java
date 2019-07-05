package earth.eu.jtzipi.jpp.ui.map;

import earth.eu.jtzipi.jpp.cell.PenAndPaperCell;
import earth.eu.jtzipi.jpp.map.IPenAndPaperRealm;
import earth.eu.jtzipi.jpp.map.IPenAndPaperSite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PenAndPaperRealm implements IPenAndPaperRealm {

    private String name;
    private List<IPenAndPaperSite> siteL;

    private PenAndPaperRealm( final String nameStr, final List<IPenAndPaperSite> siteList ) {
        this.name = nameStr;
        this.siteL = null == siteList ? new ArrayList<>() : siteList;
    }


    public static PenAndPaperRealm of(String nameStr) {
        return new PenAndPaperRealm( Objects.requireNonNull( nameStr ), null );
    }

    public void addSite( final IPenAndPaperSite penAndPaperSite ) {
        this.siteL.add( penAndPaperSite );
    }

    @Override
    public String getDescription() {
return name;

    }

    @Override
    public List<IPenAndPaperSite> getSites() {
        return siteL;
    }
}
