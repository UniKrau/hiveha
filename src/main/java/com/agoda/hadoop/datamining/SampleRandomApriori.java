package com.agoda.hadoop.datamining;

/**
 * Created by lhao on 6/3/17.
 */

import java.io.*;
import java.util.*;

public class SampleRandomApriori {

    private List<Integer[]> itemsets;
    private String transaFile;
    private Integer numItems;
    private Integer numTransactions;
    private double minSup;

    public static void main(String[] args) throws Exception {
        new SampleRandomApriori();
    }

    public SampleRandomApriori() throws Exception {
        File Dir = new File("src/main/data");
        if(Dir.isDirectory()){

           File [] listFile =  Dir.listFiles();

           for(File file:listFile){
                   System.out.println(file.getName());
                   init(file.getAbsolutePath());
                   long startTime = System.currentTimeMillis();
                   int toalNumber = getTotalLines(file);
                   Calculate(0,toalNumber);
                   long end = System.currentTimeMillis()-startTime;
                   System.out.println("EndTime  "+end);
           }
        }
    }

    static int getTotalLines(File fileName) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName)));
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }

    public void init(String transafile) throws IOException {
        transaFile = transafile;
        minSup = 0.9;
        numItems = 0;
        numTransactions = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(transaFile));
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            if (line.matches("\\s*")) continue;
            numTransactions++;
            StringTokenizer t = new StringTokenizer(line, " ");
            while (t.hasMoreTokens()) {
                int x = Integer.parseInt(t.nextToken());
                if (x + 1 > numItems) numItems = x + 1;
            }
        }
    }

    /**
     * kick of the algorithm
     */
    private void Calculate(int startline, int endline) throws IOException {

        calculateItemsSize();
        int itemsetNumber = 1;
        int nbFrequentSets = 0;

        while (itemsets.size() > 0) {
            calculateFrequentItemsets(startline,endline);

            if (itemsets.size() != 0) {
                nbFrequentSets += itemsets.size();
                createNewSetsBasePrevious();
            }
            itemsetNumber++;
        }
    }

    private void searchFrequentItemSet(Integer[] itemset, int support) {
        System.out.println(Arrays.toString(itemset) + "  (" + ((support / (double) numTransactions)) + " " + support + ")");
    }

    private void calculateItemsSize() {
        itemsets = new ArrayList<Integer[]>();
        for (int i = 0; i < numItems; i++) {
            Integer[] cand = {i};
            itemsets.add(cand);
        }
    }

    private void createNewSetsBasePrevious() {
        int currentSize = itemsets.get(0).length;
        HashMap<String, Integer[]> tmp = new HashMap<String, Integer[]>(); //temporary candidates
        for (int k = 0; k < itemsets.size(); k++) {
            for (int h = k + 1; h < itemsets.size(); h++) {
                Integer[] X = itemsets.get(k);
                Integer[] Y = itemsets.get(h);

                //make a string of the first n-2 tokens of the strings
                Integer[] newCandidate = new Integer[currentSize + 1];
                for (int s = 0; s < newCandidate.length - 1; s++) {
                    newCandidate[s] = X[s];
                }

                int differ = 0;
                for (int i = 0; i < Y.length; i++) {
                    boolean found = false;
                    for (int j = 0; j < X.length; j++) {
                        if (X[j] == Y[i]) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        differ++;
                        newCandidate[newCandidate.length - 1] = Y[i];
                    }

                }
                if (differ == 1) {
                    Arrays.sort(newCandidate);
                    tmp.put(Arrays.toString(newCandidate), newCandidate);
                }
            }
        }
        itemsets = new ArrayList<Integer[]>(tmp.values());
    }

    private void BooleanMatcher(String line, boolean[] trans) {
        Arrays.fill(trans, false);

            StringTokenizer stFile = new StringTokenizer(line, " ");
            while (stFile.hasMoreTokens()) {
                int parsedVal = Integer.parseInt(stFile.nextToken());
                trans[parsedVal] = true;
        }

    }

    private void calculateFrequentItemsets(int startline,int toalline) throws IOException {
        List<Integer[]> frequentCandidates = new ArrayList<Integer[]>(); //the frequent candidates for the current itemset

        boolean match;
        int count[] = new int[itemsets.size()];
        BufferedReader bufferedReader = new BufferedReader(new FileReader(transaFile));

        boolean[] trans = new boolean[numItems];

        // for each transaction
        for (int i = 0; i < numTransactions; i++) {

            int num = (int)(toalline*new Random().nextFloat());

            for(int j = 0 ;j< num;j++){

                int lineN = (int)(new Random().nextDouble()*num);

                int linecount = 0;
                while (bufferedReader.ready()){

                    if(lineN == linecount++ )
                    continue;

                    BooleanMatcher(bufferedReader.readLine(), trans);
                }

            }

            // check each candidate
            for (int c = 0; c < itemsets.size(); c++) {
                match = true;
                Integer[] cand = itemsets.get(c);
                for (int newValue : cand) {
                    if (trans[newValue] == false) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    count[c]++;
                }
            }

        }

        bufferedReader.close();

        for (int i = 0; i < itemsets.size(); i++) {
            if ((count[i] / (double) (numTransactions)) >= minSup) {
                searchFrequentItemSet(itemsets.get(i), count[i]);
                frequentCandidates.add(itemsets.get(i));
            }
        }
        itemsets = frequentCandidates;
    }
}
