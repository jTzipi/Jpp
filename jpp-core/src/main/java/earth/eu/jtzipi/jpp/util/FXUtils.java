/*
 *    Copyright 2019 (c) Tim Langhammer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package earth.eu.jtzipi.jpp.util;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * Utilities for JavaFX related.
 */
public final class FXUtils {


    public static final Insets INSETS_2PX = new Insets( 2D );

    // No Instance
    private FXUtils() { throw new AssertionError(); }


    public static Background createColorBG( Color color ) {
        return new Background( new BackgroundFill( color, CornerRadii.EMPTY, Insets.EMPTY ) );
    }

    public static Background bgOf( Color color, double radii, Insets insets ) {
        return new Background( new BackgroundFill( color, new CornerRadii( radii ), insets ) );
    }


    /**
     * Test whether {@code node} has a preferred width {@literal > 0}.
     * @param node region
     * @return {@code true} if {@code node}'s pref width is {@literal > 0}
     */
    public static boolean isPrefWidthSet( final Region node ) {
        return Double.compare( node.getPrefWidth(), 0D ) > 0;
    }

    /**
     * Test whether {@code node} has a width {@literal > 0}.
     * @param node region
     * @return {@code true} if {@code node}'s width is {@literal > 0}
     */
    public static boolean isWidthSet( final Region node) {
        return Double.compare( node.getWidth(), 0D ) > 0;
    }


    /**
     * Test whether {@code node} has a height {@literal > 0}.
     * @param node region
     * @return {@code true} if {@code node}'s height is {@literal > 0}
     */
    public static boolean isHeightSet(final Region node) {
        return Double.compare( node.getHeight(), 0D ) > 0;
    }

    /**
     * Test whether {@code node} has a preferred height {@literal > 0}.
     * @param node region
     * @return {@code true} if {@code node}'s pref height is {@literal > 0}
     */
    public static boolean isPrefHeightSet( final Region node ) {
        return Double.compare( node.getPrefHeight(), 0D ) > 0;
    }
}
