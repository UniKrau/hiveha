package com.agoda.hadoop.datamining;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by lhao on 6/3/17.
 */
public class SONAprioris {

    private static ArrayList<String> preprocess_elements (String filename){
        ArrayList<String> elements = new ArrayList<String>();

        File file = new File(filename);
        if(file.exists()){
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                while (bufferedReader.ready()){

                    String line = bufferedReader.readLine();
                    String regex = "\"\\\\s*\"";
                    if (line.matches(regex)) continue;
                    StringTokenizer tokenizer = new StringTokenizer(line," ");

                    while (tokenizer.hasMoreTokens()) {
                        elements.add(tokenizer.nextToken());
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return elements;
    }
    public static void splitFile(File f,File f2) throws IOException {
        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
        //you can change it to 0 if you want 000, 001, ...

        int sizeOfFiles = 1024 * 1024;// 1MB
        byte[] buffer = new byte[sizeOfFiles];

         BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f)) ;
            String name = f.getName();
            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                File newFile = new File(f2, name + "."
                        + String.format("%03d", partCounter++));
                FileOutputStream out = new FileOutputStream(newFile);
                    out.write(buffer, 0, tmp);//tmp is chunk size
        }
    }

    private static ArrayList<String> calculate(String filename,double minsup){

        ArrayList<String> elements = preprocess_elements(filename);


        ArrayList<String> frequentSize = new ArrayList<String>();

        return frequentSize;
    }

    public static void main(String[] args)throws IOException {

        String filename = "src/main/data/";
        String outputfile = "src/main/chunkdata/";
        double minSup = 0.8;
        File Dir = new File(filename);
        if(Dir.isDirectory()) {

            File[] listFile = Dir.listFiles();

            for (File file : listFile) {

                if (file.getName().endsWith(".dat")) ;
                {
                    splitFile(file.getAbsoluteFile(),new File(outputfile));
                }
            }
        }

    }
}
