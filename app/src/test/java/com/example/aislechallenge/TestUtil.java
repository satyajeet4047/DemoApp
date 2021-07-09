package com.example.aislechallenge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtil {

    public static String readJson(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get("/Users/satyajeetmohalkar/AndroidStudioProjects/AisleChallenge/app/src/test/" +
                    "java/com/example/aislechallenge/"+path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }
}
