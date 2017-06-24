package concurrency.cookbook.chapter3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

/**
 * Created by lhao on 6/19/17.
 */
public class FileSearch implements Runnable {

    private String initPath;
    private String end;
    private List<String> results;
    private Phaser phaser;

    public FileSearch(String initPath, String end, Phaser phaser) {
        this.initPath = initPath;
        this.end = end;
        this.results = new ArrayList<String>();
        this.phaser = phaser;
    }

    private void directoryProcess(File file){

        File list [] = file.listFiles();
        if ( list != null){
            for (int i = 0; i < list.length ; i++) {
                if ( list[i].isDirectory()){
                    directoryProcess(list[i]);
                }else {
                    fileProcess(list[i]);
                }
            }
        }
    }

    private void fileProcess(File file) {
        if (file.getName().endsWith(end)) {
            results.add(file.getAbsolutePath());
        }
    }
    public void run() {

    }
}
