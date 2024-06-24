package directory;

import color.TextColor;
import message.SuccessMessage;

import java.util.Vector;


public class Directory {

    public Directory(String name, Directory parent, String spacing){
        this.parent = parent;
        directoryName = name;
        childrenDirectories = new Vector<>();
        childrenFiles = new Vector<>();
        this.spacing = spacing;
    }

    public void setParent(Directory directory){
        parent = directory;
    }
    public Directory getParent(){
        return parent;
    }

    private String directoryName;
    private Vector<File> childrenFiles;
    private Vector<Directory> childrenDirectories;

    private Directory parent;

    public boolean containsDirectory(String directory){
        for (Directory dir: childrenDirectories)
            if (dir.directoryName.equals(directory))
                return true;
        return false;
    }

    public boolean containsFile(String file){
        for (File f: childrenFiles)
            if ((f.fileName + f.extension).equals(file))
                return true;
        return false;
    }

    public void addFile(String file){
        StringBuilder name = new StringBuilder();
        StringBuilder ext = new StringBuilder();

        int idx = 0;
        for (; idx < file.length(); idx++){
            if (file.charAt(idx) == '.')
                break;
            name.append(file.charAt(idx));
        }

        for (; idx < file.length(); idx++){
            ext.append(file.charAt(idx));
        }
        childrenFiles.add(new File(name.toString(), ext.toString(), this));
        SuccessMessage.fileCreationSuccess(file, this);
    }

    private void addFile(File file){
        childrenFiles.add(file);
    }

    private void addDirectory(Directory directory){
        childrenDirectories.add(directory);
    }

    public void deleteFile(String file){
        childrenFiles.removeIf(f -> (f.fileName + f.extension).equals(file));
        SuccessMessage.fileDeletionSuccess(this, file);
    }

    public void addDirectory(String dir, Directory parent){
        childrenDirectories.add(new Directory(dir, parent, spacing + "\t"));
        SuccessMessage.dirCreationSuccess(dir, getCompletePath());
    }

    public void moveFileToDifferentDirectory(String fileName, Directory target){
        File file = getChildFileByName(fileName, true);
        target.addFile(file);
        file.setParent(target);
    }

    public void moveDirToDifferentDirectory(String source, Directory target){
        Directory dir = getChildDirectoryByName(source, true);
        target.addDirectory(dir);
        dir.setParent(target);
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public Directory getChildDirectoryByName(String name, boolean removeDir){
        for (Directory dir: childrenDirectories)
            if (dir.getDirectoryName().equals(name)) {
                if (removeDir) {
                    childrenDirectories.remove(dir);
                }
                return dir;
            }
        return new Directory("", null, spacing + "\t");
    }

    private File getChildFileByName(String fileName, boolean remove){
        for (File file: childrenFiles){
            if ((file.fileName + file.extension).equals(fileName)){
                System.out.println(file.fileName);
                if (remove)
                    childrenFiles.remove(file);
                return file;
            }
        }
        return new File("", "", null);
    }

    public void printChildren(){
        System.out.println("\n***** Following are the children of this directory: '" + getCompletePath() + "' *****");
        System.out.println(this);
    }

    private final String spacing;

    @Override
    public String toString(){
        return "\n" + TextColor.ANSI_DIR + spacing + "---------------" + TextColor.ANSI_RESET + "\n" +
                spacing + "TYPE: Directory\n" +
                spacing + "NAME: " + directoryName + "\n" +
                spacing + "CHILDREN: " +
                printChildFilesAndDirs() + "\n" +
                TextColor.ANSI_DIR + spacing + "---------------" + TextColor.ANSI_RESET + "\n";

    }

    private String printChildFilesAndDirs(){
        StringBuilder children = new StringBuilder();

        for (File file: childrenFiles)
            children.append(file);


        for (Directory directory: childrenDirectories) {
            children.append(directory);
        }


        return children.toString();
    }

    public String getCompletePath(){
        StringBuilder path = new StringBuilder();
        Directory dir = this;
        while (dir.parent != null){
            path.insert(0, dir.directoryName);
            path.insert(0, "/");
            dir = dir.parent;
        }
        path.insert(0, "root");
        return path.toString();
    }

    private static class File{
        private final String fileName;
        private final String extension;
        private String content;

        private String spacing = "";

        public Directory getParent() {
            return parent;
        }

        public void setParent(Directory parent) {
            this.parent = parent;
        }

        private Directory parent;

        public File(String fileName, String extension, Directory parent){
            this.parent = parent;
            this.fileName = fileName;
            this.extension = extension;
            content = "";
            spacing += "\t" + parent.spacing;
        }

        public String getCompletePath(){
            StringBuilder path = new StringBuilder();
            path.append(fileName).append(extension);
            Directory dir = parent;
            while (dir.parent != null){
                path.insert(0, "/");
                path.insert(0, dir.directoryName);
                dir = dir.parent;
            }
            path.insert(0, "root/");
            return path.toString();
        }

        public String getFileName() {
            return fileName;
        }

        public String getExtension() {
            return extension;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        @Override
        public String toString(){
            return "\n" + TextColor.ANSI_FILE + spacing + "---------------" + TextColor.ANSI_RESET + "\n" +
                    spacing + "TYPE      : 'File'\n" +
                    spacing + "NAME      : '" + fileName + "'\n" +
                    spacing + "EXTENSION : '" + extension + "'\n" +
                    TextColor.ANSI_FILE + spacing + "---------------" + TextColor.ANSI_RESET;

        }

        public void appendContent(String text){
            content += text;
        }

        public void writeAtIndex(String text, int idx){
            String before = content.substring(0, idx - 1);
            String after = content.substring(idx);
            content = before + text + after;
        }

        public String getContentByIndexAndSize(int idx, int size){
            if (size < 0)
                return "";
            return content.substring(idx, idx + size);
        }
    }

    public void writeToFile(String fileName, int idx, String text){
        File file = getChildFileByName(fileName, false);
        if (idx < 0 || idx >= file.content.length())
            file.appendContent(text);
        else
            file.writeAtIndex(text, idx);
    }

    public void readFromFile(String fileName, int idx, int size){
        File file = getChildFileByName(fileName, false);
        if (idx < 0)
            System.out.println(file.content);
        else
            System.out.println(file.getContentByIndexAndSize(idx, size));
    }
    public String getAllChildrenNames(){
        StringBuilder string = new StringBuilder();
        for (Directory directory: childrenDirectories){
            string.append("DIRECTORY:").append(directory.getCompletePath()).append("\n");
            string.append(directory.getAllChildrenNames());
        }
        for (File file: childrenFiles){
            string.append("FILE:").append(file.getCompletePath()).append("\n");
        }
        return string.toString();
    }
}
