package message;

import color.TextColor;
import directory.Directory;

public class FailureMessage {
    public static void fileCreationFailure(String fileName, String current){
        System.out.println(TextColor.ANSI_RED + "In Directory '" + current + "': a file with name '" + fileName + "' already exists!" + TextColor.ANSI_RESET + "\n");
    }

    public static void dirCreationFailure(String dirName, String current){
        System.out.println(TextColor.ANSI_RED + "In Directory '" + current + "': a directory with name '" + dirName + "' already exists!" + TextColor.ANSI_RESET + "\n");
    }

    public static void fileDeletionFailure(String fileName, String current){
        System.out.println(TextColor.ANSI_RED + "In given Directory '" + current + "': File: '" + fileName + "' does not exists!" + TextColor.ANSI_RESET + "\n");
    }

    public static void dirChangeFailure(String dirName, String current){
        System.out.println(TextColor.ANSI_RED + "No such directory '" + dirName + "' present in '" + current + "'" + TextColor.ANSI_RESET + "\n");
    }

    public static void fileMovementFailure(boolean invalidSource){
        System.out.println(TextColor.ANSI_RED + "Unable to move because " + (invalidSource ? "source" : "target") + " path does not exists" + TextColor.ANSI_RESET + "\n");
    }

    public static void noSuchObjectToMoveError(String fileName, String directoryPath, String type){
        System.out.println(TextColor.ANSI_RED + "No " + type + " named '" + fileName + "' present in directory '" + directoryPath + "'\n" + TextColor.ANSI_RESET);
    }

    public static void noParentDirectoryMessage(){
        System.out.println(TextColor.ANSI_RED + "'root' is the top most directory and has no parent" + TextColor.ANSI_RESET);
    }

    public static void sameDirectoryErrorMessage(String path){
        System.out.println(TextColor.ANSI_RED + "Same Source and Target Directory: " + path + TextColor.ANSI_RESET + "\n");
    }

    public static void invalidModeFailure(String mode){
        System.out.println("Invalid mode: '" + mode + "', there are only two modes: READ & WRITE");
    }
}
