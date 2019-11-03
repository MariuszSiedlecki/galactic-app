package com.galacticapp.commons.extras;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.galacticapp.commons.Naming.LOCAL_PATH;

public class DirectoryCreator {
    public static void createDirectory() {
        if (Files.notExists(Paths.get(LOCAL_PATH))) {
            try {
                Files.createDirectory(Paths.get(LOCAL_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
