package directory;

import color.TextColor;
import message.FailureMessage;
import message.SuccessMessage;

import java.util.Optional;
import java.util.Scanner;

public class DirFunctions {
    public static void createFile(String fileName, Directory current){
        if (!current.containsFile(fileName))
            current.addFile(fileName);
        else
            FailureMessage.fileCreationFailure(fileName, current.getCompletePath());
    }

    public static void deleteFile(String fileName, Directory current){
        if (current.containsFile(fileName))
            current.deleteFile(fileName);
        else
            FailureMessage.fileDeletionFailure(fileName, current.getCompletePath());
    }

    public static void makeDirectory(String dirName, Directory current){
        if (!current.containsDirectory(dirName))
            current.addDirectory(dirName, current);
        else
            FailureMessage.dirCreationFailure(dirName, current.getCompletePath());
    }

    public static Optional<Directory> changeDirectory(String dirName, Directory current){
        if (!current.containsDirectory(dirName)) {
            if (dirName.equals("..")){
                if (current.getParent() != null) {
                    SuccessMessage.dirChangeSuccess(current.getCompletePath(), current.getParent().getCompletePath());
                    return Optional.of(current.getParent());
                }
                else{
                    FailureMessage.noParentDirectoryMessage();
                    return Optional.empty();
                }
            }
            FailureMessage.dirChangeFailure(dirName, current.getCompletePath());
            return Optional.empty();
        }
        else {
            String oldDir = current.getCompletePath();
            current = current.getChildDirectoryByName(dirName, false);
            String newDir = current.getCompletePath();
            SuccessMessage.dirChangeSuccess(oldDir, newDir);
            return Optional.of(current);
        }
    }

    public static void moveFile(String sourceName, String targetName, Directory current, Directory root){
        Optional<Directory> dir = checkValidPath(targetName, root);
        if (dir.isEmpty()){
            FailureMessage.fileMovementFailure(false);
            return;
        }
        if (dir.get().getCompletePath().equals(current.getCompletePath())){
            FailureMessage.sameDirectoryErrorMessage(current.getCompletePath());
            return;
        }
        if (sourceName.contains(".")){
            if (current.containsFile(sourceName)){
                current.moveFileToDifferentDirectory(sourceName, dir.get());
                SuccessMessage.fileMovementSuccess(sourceName, current.getCompletePath(), dir.get().getCompletePath(), "File");
            }
            else FailureMessage.noSuchObjectToMoveError(sourceName, current.getCompletePath(), "file");
        }
        else {
            if (current.containsDirectory(sourceName)) {
                current.moveDirToDifferentDirectory(sourceName, dir.get());
                SuccessMessage.fileMovementSuccess(sourceName, current.getCompletePath(), dir.get().getCompletePath(), "Directory");
            }
            else FailureMessage.noSuchObjectToMoveError(sourceName, current.getCompletePath(), "directory");
        }
    }

    public static void openFile(String fileName, String mode, Directory current){
        if (current.containsFile(fileName)){
            if (mode.equalsIgnoreCase("READ")){
                openReadMode(fileName, current);
            }
            else if (mode.equalsIgnoreCase("WRITE")){
                openWriteMode(fileName, current);
            }
            else FailureMessage.invalidModeFailure(mode);
        }
        else FailureMessage.noSuchObjectToMoveError(fileName, current.getCompletePath(), "file");
    }

    public static void openReadMode(String fileName, Directory current){
        System.out.println("\n" + TextColor.ANSI_GREEN + "File Opened in READ mode" + TextColor.ANSI_RESET);
        System.out.println("\n" + TextColor.ANSI_BLUE + "Choose an Option:" + TextColor.ANSI_RESET);
        System.out.println("\t1: Read All");
        System.out.println("\t2: Read Particular");
        System.out.println("\t3: Close File");
        System.out.print("Enter Option: ");
        Scanner scanner = new Scanner(System.in);
        int opt = scanner.nextInt();
        if (opt == 1) {
            readFromFile(fileName, current, -1, 0);
            openReadMode(fileName, current);
        }
        else if (opt == 2){
            System.out.print("Enter index: ");
            int idx = scanner.nextInt();
            System.out.print("Enter size: ");
            int size = scanner.nextInt();
            readFromFile(fileName, current, idx, size);
            openReadMode(fileName, current);
        }
        else if (opt == 3){
            SuccessMessage.fileClosureSuccessful(fileName);
        }
    }

    public static void openWriteMode(String fileName, Directory current){
        System.out.println("\n" + TextColor.ANSI_GREEN + "File Opened in WRITE mode" + TextColor.ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + TextColor.ANSI_BLUE + "Choose an Option:" + TextColor.ANSI_RESET);
        System.out.println("\t1: Write at Start");
        System.out.println("\t2: Write at Particular Index");
        System.out.println("\t3: Close File");
        System.out.print("Enter Option: ");
        int opt = scanner.nextInt();
        if (opt == 1) {
            System.out.print("Enter Text: ");
            String text = scanner.next();
            writeToFile(fileName, current, -1, text);
            openWriteMode(fileName, current);
        }
        else if (opt == 2){
            System.out.print("Enter Text: ");
            String text = scanner.next();
            System.out.print("Enter index: ");
            int idx = scanner.nextInt();
            writeToFile(fileName, current, idx, text);
            openWriteMode(fileName, current);
        }
        else if (opt == 3){
            SuccessMessage.fileClosureSuccessful(fileName);
        }
    }

    public static void writeToFile(String fileName, Directory current, int idx, String text){
        if (current.containsFile(fileName)){
            current.writeToFile(fileName, idx, text);
        }
        else {
            FailureMessage.noSuchObjectToMoveError(fileName, current.getCompletePath(), "file");
        }
    }

    public static void readFromFile(String fileName, Directory current, int idx, int size){
        if (current.containsFile(fileName)){
            current.readFromFile(fileName, idx, size);
        }
        else {
            FailureMessage.noSuchObjectToMoveError(fileName, current.getCompletePath(), "file");
        }
    }

    private static Optional<Directory> checkValidPath(String path, Directory root){
        String [] paths = path.split("/");
        for (int i = 1; i < paths.length; i++){
            if (root.containsDirectory(paths[i]))
                root = root.getChildDirectoryByName(paths[i], false);
            else{
                return Optional.empty();
            }
        }
        return Optional.of(root);
    }
}
