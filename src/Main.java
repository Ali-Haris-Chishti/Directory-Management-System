import color.TextColor;
import directory.DirFunctions;
import directory.Directory;
import io.StructureReader;
import io.StructureWriter;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final static Directory root = StructureReader.readStructureFromFile();
    private static Directory currentDir;

    public static void main(String[] args) {
        currentDir = root;
        optionsLoop();
    }

    private static void optionsLoop(){
        int option = 10;
        while (option != 0){
            Scanner scanner = new Scanner(System.in);
            printOptions();
            option = scanner.nextInt();
            switch (option){
                case 0 ->{
                    StructureWriter.writeStructureToFile(root);
                    System.out.println(TextColor.ANSI_RED + "Exiting..." + TextColor.ANSI_RESET);
                    System.exit(0);
                }
                case 1 ->{
                    createFile();
                }
                case 2 ->{
                    deleteFile();
                }
                case 3 ->{
                    createDirectory();
                }
                case 4 ->{
                    changeDirectory();
                }
                case 5 ->{
                    moveFileOrDirectory();
                }
                case 6 ->{
                    openFile();
                }
                case 7 ->{
                    listContent();
                }
            }
        }
    }

    private static void printCurrentDirectory(){
        System.out.println("\n" + TextColor.ANSI_SEA_GREEN + "Current Directory: '" + currentDir.getCompletePath() + "'" + TextColor.ANSI_RESET);
    }

    private static void printOptions(){
        printCurrentDirectory();
        System.out.println("\n" + TextColor.ANSI_BLUE + "Choose an Option:" + TextColor.ANSI_RESET);
        System.out.println("\t1: Create File");
        System.out.println("\t2: Delete File");
        System.out.println("\t3: Create Directory");
        System.out.println("\t4: Change Directory");
        System.out.println("\t5: Move File or Directory");
        System.out.println("\t6: Open File");
        System.out.println("\t7: List Folder's Content");
        System.out.println("\t0: EXIT");
        System.out.print("Enter Option: ");
    }

    private static void createFile(){
        System.out.print("\nEnter Name of File: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        DirFunctions.createFile(name, currentDir);
    }

    private static void deleteFile(){
        System.out.print("\nEnter Name of File: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        DirFunctions.deleteFile(name, currentDir);
    }

    private static void createDirectory(){
        System.out.print("\nEnter Name of Directory: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        DirFunctions.makeDirectory(name, currentDir);
    }

    private static void changeDirectory(){
        System.out.print("\nEnter Name of Directory (enter '..' to shift to parent): ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Optional<Directory> dir = DirFunctions.changeDirectory(name, currentDir);
        dir.ifPresent(directory -> currentDir = directory);
    }

    private static void moveFileOrDirectory(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Name of File/Directory to be moved: ");
        String name = scanner.nextLine();
        System.out.print("Enter Complete New path: ");
        String path = scanner.nextLine();
        DirFunctions.moveFile(name, path, currentDir, root);
    }

    private static void openFile(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Name of File: ");
        String name = scanner.nextLine();
        System.out.print("Enter MODE (READ/WRITE): ");
        String mode = scanner.nextLine();
        DirFunctions.openFile(name, mode, currentDir);
    }

    private static void listContent(){
        currentDir.printChildren();
    }
}
