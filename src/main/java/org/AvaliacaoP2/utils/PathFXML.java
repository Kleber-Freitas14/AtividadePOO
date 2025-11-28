package org.AvaliacaoP2.utils;

import java.nio.file.Paths;

public class PathFXML {
    public static String pathBase(){

        return Paths.get("src/main/java/org/view").toAbsolutePath().toString();
    }
}
