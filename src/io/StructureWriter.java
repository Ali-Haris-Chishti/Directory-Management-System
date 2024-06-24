package io;

import directory.Directory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StructureWriter implements IO{
    public static void writeStructureToFile(Directory root){
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("DIRECTORY: " + root.getDirectoryName() + "\n");
            String children = root.getAllChildrenNames();
            writer.write(children.substring(0, children.length() - 1));

            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
