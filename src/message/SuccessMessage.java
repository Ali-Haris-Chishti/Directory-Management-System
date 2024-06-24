package message;

import color.TextColor;
import directory.Directory;

public class SuccessMessage {
    public static void fileCreationSuccess(String fileName, Directory current){
        System.out.println(TextColor.ANSI_GREEN + "File '" + fileName + "' added successfully to Directory '" + current.getDirectoryName() + "'" + TextColor.ANSI_RESET + "\n");
    }

    public static void fileDeletionSuccess(Directory current, String fileName){
        System.out.println(TextColor.ANSI_GREEN + "File '" + current.getCompletePath() + "/" + fileName + "' removed successfully." + TextColor.ANSI_RESET + "\n");
    }

    public static void dirCreationSuccess(String dirName, String path){
        System.out.println(TextColor.ANSI_GREEN + "File '" + dirName + "' added successfully to Directory '" + path + "'" + TextColor.ANSI_RESET + "\n");
    }

    public static void dirChangeSuccess(String oldDir, String newDir){
        System.out.println(TextColor.ANSI_GREEN + "Directory Successfully changed from '" + oldDir + "' to '" + newDir + "'" + TextColor.ANSI_RESET + "\n");
    }

    public static void fileMovementSuccess(String name, String source, String destination, String type){
        System.out.println(TextColor.ANSI_GREEN + type + " '" + name + "' moved successfully from '" + source + "' to '" + destination + "'" + TextColor.ANSI_GREEN + "\n");
    }

    public static void fileClosureSuccessful(String file){
        System.out.println(TextColor.ANSI_GREEN + "File: '" + file + "' closed Successfully" + TextColor.ANSI_GREEN);
    }
}
