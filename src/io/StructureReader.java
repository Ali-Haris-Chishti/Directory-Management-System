package io;

import color.TextColor;
import directory.DirFunctions;
import directory.Directory;

import java.io.IOException;
import java.util.Scanner;

public class StructureReader implements IO{
    public static Directory readStructureFromFile(){
        try {

            System.out.println(TextColor.ANSI_BLUE + "*******************************************");
            System.out.println("********Loading Directory Structure********");
            System.out.println("*******************************************" + TextColor.ANSI_RESET + "\n");
            Scanner scanner = new Scanner(file);

            // First Directory is always root
            String rt = scanner.nextLine();
            Directory root = new Directory(rt.substring(rt.indexOf(':') + 1), null, "");

            Directory current = root;

            // Used for directory tracking
            String prevObj = root.getCompletePath();

            while (scanner.hasNext()){
                String obj = scanner.nextLine();
                // Checks if movement is needed inwards or outwards
                if (!obj.contains(current.getCompletePath()))
                    current = DirFunctions.changeDirectory("..", current).get();
                if (obj.contains("DIRECTORY")) {
                    String sub = obj.substring(obj.lastIndexOf('/') + 1);
                    current.addDirectory(sub, current);
                    current = current.getChildDirectoryByName(sub, false);
                }
                else {
                    current.addFile(obj.substring(obj.lastIndexOf('/') + 1));
                }

                prevObj = obj.substring(obj.indexOf(':') + 1);
            }
            System.out.println(TextColor.ANSI_BLUE + "*******************************************");
            System.out.println("*************Loading Successful************");
            System.out.println("*******************************************" + TextColor.ANSI_RESET + "\n");
            return root;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
