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

package earth.eu.jtzipi.jpp.ui.tile.segment;

import earth.eu.jtzipi.jpp.IBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for 'path' like shapes.
 *
 * @author jTzipi
 */
public class PathBuilder implements IBuilder<Shape> {


    private final List<PathElement> pelementL = new ArrayList<>();
    private double lastX = 0D;
    private double lastY = 0D;
    private StrokeLineJoin sJoin = StrokeLineJoin.ROUND;
    private StrokeType sType = StrokeType.CENTERED;
    private StrokeLineCap sLineCap = StrokeLineCap.ROUND;
    private Paint fill = Color.gray( 0.7D );
    private boolean smooth = true;
    private double sw = 1D;              // stroke width


    private PathBuilder(  ) {

        pelementL.add( new MoveTo( lastX, lastY ) );
    }

    /**
     * Create a instance of this builder.
     *
     * @return created
     */
    public static PathBuilder create() {
        return new PathBuilder();
    }

    /**
     * Set width of stroke.
     *
     * @param strokeWidth stroke width [0,1]
     * @return {@code this}
     */
    public PathBuilder strokeWidth( final double strokeWidth ) {
        this.sw = strokeWidth ;

        return this;
    }

    /**
     * Stroke join.
     *
     * @param join stroke join
     * @return {@code }
     */
    public PathBuilder strokeJoin( final StrokeLineJoin join ) {
        this.sJoin = join;
        return this;
    }

    /**
     *
     * @param x
     * @return
     */
    public PathBuilder lx( final double x ) {
        this.pelementL.add( new LineTo( x, lastY ) );
        this.lastX = x;
        return this;
    }

    /**
     *
     * @param y
     * @return
     */
    public PathBuilder ly( final double y ) {
        this.pelementL.add( new LineTo( lastX,y ) );
        this.lastY = y;
        return this;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public PathBuilder lxy( final double x, final double y ) {
        this.pelementL.add( new LineTo( x, y ) );
        this.lastX = x;
        this.lastY = y;
        return this;
    }

    public PathBuilder mx( final double x ) {
        this.pelementL.add( new MoveTo( x, lastY ) );
        this.lastX = x;
        return this;
    }

    public PathBuilder my( final double y ) {
        this.pelementL.add( new MoveTo( lastX,y ) );
        this.lastY = y;
        return this;
    }

    public PathBuilder mxy( final double x, final double y ) {
        this.pelementL.add( new MoveTo( x, y ) );
        this.lastX = x;
        this.lastY = y;
        return this;
    }

    public PathBuilder quadTo( final double x, final double y, final double ctrX, final double ctrY ) {
        this.pelementL.add( new QuadCurveTo( ctrX, ctrY, x, y ) );
        this.lastY = y;
        this.lastX = x;
        return this;
    }

    public PathBuilder close() {
        this.pelementL.add( new ClosePath() );
        return this;
    }

    @Override
    public Path build() {
        Path p = new Path( pelementL );

        p.setSmooth( smooth );
        p.setStrokeWidth( sw );
        p.setStrokeLineJoin( sJoin );
        p.setStroke( fill );
        p.setStrokeLineCap( sLineCap );
        p.setStrokeType( sType );

        return p;
    }
}
