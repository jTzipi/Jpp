package earth.eu.jtzipi.jpp;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Resource bundle.
 */
public final class Resources {

    private static final Font FONT_DEF = Font.getDefault();
    private static final Map<String, Image> IMG_MAP = new HashMap<>();
    private static final Map<String, Font> FONT_MAP = new HashMap<>();

    private Resources() {
        // denied
    }

    public static Font getFont( final String nameStr ) {
        return FONT_MAP.getOrDefault( nameStr, Font.getDefault() );
    }

    static void putAllBG() {

    }

    static void putAllFonts( final Map<String, Font> fontMap ) {
        Objects.requireNonNull( fontMap );
        FONT_MAP.putAll( fontMap );
    }
}
