package pt.ipp.estg.files;

import java.io.File;

public class Import {

    public static void importFile(String filePath) {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist!");
        }



    }

}
