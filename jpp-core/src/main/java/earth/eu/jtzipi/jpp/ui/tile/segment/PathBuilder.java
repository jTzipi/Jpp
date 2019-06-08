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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class PathBuilder implements IBuilder<Path> {


    private double lastX;
    private double lastY;
    private Path path;


    private PathBuilder(  ) {
        this.lastX = 0D;
        this.lastY = 0D;
        this.path = new Path( new MoveTo( 0D, 0D ) );
    }

    public PathBuilder strokeWidth( final double strokeWidth )
    {
        this.path.setStrokeWidth( strokeWidth );

        return this;
    }

    public static PathBuilder create() {
        return new PathBuilder();
    }

    public PathBuilder lx( final double x ) {
        this.path.getElements().add( new LineTo( x, lastY ) );
        this.lastX = x;
        return this;
    }

    public PathBuilder ly( final double y ) {
        this.path.getElements().add( new LineTo( lastX,y ) );
        this.lastY = y;
        return this;
    }
    public PathBuilder lxy( final double x, final double y ) {
        this.path.getElements().add( new LineTo( x, y ) );
        this.lastX = x;
        this.lastY = y;
        return this;
    }

    public PathBuilder mx( final double x ) {
        this.path.getElements().add( new MoveTo( x, lastY ) );
        this.lastX = x;
        return this;
    }

    public PathBuilder my( final double y ) {
        this.path.getElements().add( new MoveTo( lastX,y ) );
        this.lastY = y;
        return this;
    }

    public PathBuilder mxy( final double x, final double y ) {
        this.path.getElements().add( new LineTo( x, y ) );
        this.lastX = x;
        this.lastY = y;
        return this;
    }



    @Override
    public Path build() {
        return path;
    }
}
