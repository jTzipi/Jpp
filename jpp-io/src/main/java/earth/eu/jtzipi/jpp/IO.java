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

package earth.eu.jtzipi.jpp;


import javafx.scene.image.Image;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Utilities for I/O.
 * @author jTzipi
 */
public final class IO {
    /**
     * Path to workdir .
     */
    public static final Path PATH_WORK_DIR = Paths.get( System.getProperty( "user.dir" ) );
    /**
     * Minimal font size .
     */
    public static final double MIN_FONT = 5D;
    /**
     * one kilo byte.
     */
    public static final int KILO_BYTE = 1024;

    // If path can not read
    private static final String PATH_UNKNOWN = "";
    private static final Logger LOG = LoggerFactory.getLogger( "IOGysi" );

    private static final Path PATH_TO_RES = PATH_WORK_DIR.resolve( "jpp-core/src/main/resources/" );
    private IO() {


    }

    /**
     * Try to load all images.
     *
     * @param path path to folder
     * @return Map of images
     * @throws IOException          error io
     * @throws NullPointerException if {@code path} is null
     */
    public static Map<String, Image> loadAllImages( final Path path ) throws IOException {
        Objects.requireNonNull( path );

        List<Path> imgL = readFolder( path, null );

        Map<String, Image> imgM = new HashMap<>();

        for ( Path ip : imgL ) {

            String name = getPathName( ip );
            Image img = loadImage( ip );
            // LOG.warn("Lade bild " + name );
            imgM.put( name, img );
        }

        return imgM;
    }

    /**
     * Load all font's found.
     *
     *
     * @param path path to dir
     * @return map with name/font entries
     * @throws IOException if IOError
     * @throws NullPointerException if {@code path } is null
     */
    public static Map<String, Font> loadAllFont( final Path path ) throws IOException {
        Objects.requireNonNull( path );
        if ( !Files.isReadable( path ) ) {
            throw new IOException( "Failed to read '" + path + "'" );
        }
        if ( !Files.isDirectory( path ) ) {
            return Collections.emptyMap();
        }
        Map<String, Font> fontM = new HashMap<>();

        for ( Path ip : readFolder( path, null ) ) {

            String name = getPathName( ip );
            Font gysi = loadFont( ip, 19D );

            fontM.put( name, gysi );
        }

        return fontM;

    }

    /**
     * Load a font from path.
     *
     * @param path path
     * @param size size
     * @return Font object
     * @throws IOException io loading
     */
    public static Font loadFont( final Path path, final double size ) throws IOException {
        Objects.requireNonNull( path );
        if ( size < MIN_FONT ) {
            throw new IllegalArgumentException( "Font size < " + MIN_FONT );
        }

        Font font;
        try ( InputStream io = Files.newInputStream( path ) ) {

            font = Font.loadFont( io, size );
        } catch ( final IOException ioE ) {
            LOG.error( "Can not read font for path '" + path + "'" );
            font = Font.getDefault();
        }

        return font;

    }

    /**
     * Return the file name of a file denoted by this path.
     * This is the part before the last dot {@literal .}.
     *
     * @param path the path to a file
     * @return the file name without the file ending or suffix
     * @throws IOException          if {@code path} is not readable
     * @throws NullPointerException if {@code path} is null
     */
    public static String getPathName( final Path path ) throws IOException {
        Objects.requireNonNull( path );
        if ( !Files.isReadable( path ) ) {
            throw new IOException( "Path '" + path + "' is not readable" );
        }

        String[] temp = splitFileName( path );
        return 0 == temp.length ? PATH_UNKNOWN : temp[0];
    }

    /**
     * Read path name ignoring io error.
     *
     * @param path path
     * @return name of file or {@linkplain #PATH_UNKNOWN}
     * @throws NullPointerException if {@code path} is null
     */
    public static String getPathNameIgnore( final Path path ) {
        String name;
        try {
            name = getPathName( path );
        } catch ( IOException ioE ) {


            name = PATH_UNKNOWN;
        }

        return name;
    }

    /**
     * Load image for path.
     *
     * @param path path to image
     * @return image
     * @throws IOException          if loading failed
     * @throws NullPointerException if {@code path} is null
     */
    public static Image loadImage( final Path path ) throws IOException {
        Objects.requireNonNull( path );

        try ( InputStream ips = Files.newInputStream( path ) ) {
            Image img = new Image( ips );

            return img;
        } catch ( final IOException ioE ) {
            LOG.error( "Fehler bei lesen des Pic ' " + path + "' " );
            throw ioE;
        }
    }

    /**
     * Load a image from resource path.
     * @param resPath sub path or resources
     * @return image if path is valid
     * @throws IOException image path not valid or io error
     * @throws NullPointerException if {@code resPath} is null
     */
    public static Image loadImageFromRes( final Path resPath ) throws IOException {
        Objects.requireNonNull( resPath );

        Path path = PATH_TO_RES.resolve( resPath );
        if( !Files.isReadable( path )) {
            LOG.warn( "Failed to read image '" + path + "'" );
            throw new IOException( "File not readable '" + path + "'" );

        }

        return loadImage( path );
    }

    /**
     * Split a path file name .
     * @param path  path
     * @return
     */
    private static String[] splitFileName( final Path path ) {
        Path name = path.getFileName();
        // path with no name
        if ( null == name ) {
            return new String[0];
        }

        return name.toString().split( "." );

    }

    private static List<Path> readFolder( final Path path, final String glob ) {

        List<Path> pl = new ArrayList<>();
        // read all from path
        try ( final DirectoryStream<Path> paths = Files.newDirectoryStream( path ) ) {

            Iterator<Path> pit = paths.iterator();
            while ( pit.hasNext() ) {
                pl.add( pit.next() );
            }

        } catch ( final IOException ioE ) {

            LOG.warn( "Failed to read path", ioE );
        }

        return pl;
    }
}
