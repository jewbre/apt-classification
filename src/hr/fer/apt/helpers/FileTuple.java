package hr.fer.apt.helpers;

import java.io.File;

/**
 * Created by vilimstubican on 02/06/16.
 */
public class FileTuple {

    private File filename;
    private Classes fileClass;

    public FileTuple(File filename, Classes fileClass) {
        this.filename = filename;
        this.fileClass = fileClass;
    }

    public File getFile(){
        return this.filename;
    }

    public Classes getFileClass() {
        return fileClass;
    }
}
