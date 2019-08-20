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

package earth.eu.jtzipi.jpp.ui;


import earth.eu.jtzipi.jpp.cell.PenAndPaperCell;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;

/**
 * Pen and Paper Properties.
 * <p>
 *     Here we can find all application default properties.
 * </p>
 */
public final class PenAndPaperPropertiesFX {

    /** Window width. */
    public static final double WINDOW_WIDTH = 750D;
    /** Window with prop. */
    public static final DoubleProperty WINDOW_WIDTH_PROP_FX = new SimpleDoubleProperty(WINDOW_WIDTH);

    /** Window height. */
    public static final double WINDOW_HEIGHT = 750D;
    public static final DoubleProperty WINDOW_HEIGHT_PROP_FX = new SimpleDoubleProperty(WINDOW_HEIGHT);


    private PenAndPaperPropertiesFX() {

    }


}
