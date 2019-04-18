package edu.uci.ics.cs221.analysis;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Set;
import java.util.ArrayList;
import java.lang.Math;


/*
Testcase: src/test/java/edu/uci/ics/cs221/analysis/wordbreak/Team8JWordBreakTokenizerTest.java
*/

public class JWordBreakTokenizer extends WordBreakTokenizer implements Tokenizer {

    private HashMap<String, Double> dictmap;

    public JWordBreakTokenizer() {
        try {
            // load the dictionary corpus
            URL dictResource = JWordBreakTokenizer.class.getClassLoader().getResource("JP_dictionary.txt");
            List<String> dictLines = Files.readAllLines(Paths.get(dictResource.toURI()));

            Double total = 0.0;

            dictmap = new HashMap<>();

            for(int i = 0; i < dictLines.size(); ++i){
                String tmp = dictLines.get(i);
                if (tmp.startsWith("\uFEFF")) {
                    tmp = tmp.substring(1);
                }

                List<String> contains = Arrays.asList(tmp.split("\\s+"));
                String key = contains.get(4).toLowerCase();
                Double value = Double.valueOf(contains.get(6));
               //value = Math.abs(Math.log(value));

                total += value;
                dictmap.put(key,value);
            }
            Set<String> keys = dictmap.keySet();
            for(String key : keys){
                dictmap.replace(key, Math.log10(dictmap.get(key)/total));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> tokenize(String text) {

        List<String> res = new ArrayList<>();

        if(text.isEmpty()){
            //throw  new IllegalArgumentException("The intput text is empty.");
            System.out.println("ATTENTION: The input is empty.");
            return res;
        }

        text = text.toLowerCase();

        //Create DP matrix
        double [][] pos = new double[text.length()][text.length()];
        int[][] dp = new int[text.length()][text.length()];

        for(int i=0; i < dp.length; i++){
            for(int j=0; j < dp[i].length ; j++){
                dp[i][j] = -1; //-1 indicates string between i to j cannot be split
                pos[i][j] = -Double.MAX_VALUE;
            }
        }

        //Break string using Dynamic Programming
        for(int l = 1; l <= text.length(); l++){
            for(int i=0; i < text.length() -l + 1 ; i++){
                int j = i + l-1;
                String str = text.substring(i,j+1);

                if(dictmap.containsKey(str)){
                    dp[i][j] = i;
                    pos[i][j] = dictmap.get(str);
                }

                //Double maxpos = 0.0;
                for(int k=i+1; k <= j; k++){
                    if(dp[i][k-1] != -1 && dp[k][j] != -1){
                        if(pos[i][k-1] + pos[k][j] > pos[i][j]){
                            dp[i][j] = k;
                            pos[i][j] = pos[i][k-1] + pos[k][j];
                        }
                    }
                }

            }
        }

        if(dp[0][text.length() - 1] == -1){
            //throw  new IllegalArgumentException("The original text can not be broken into words");
            throw  new RuntimeException("The original text can not be broken into words");
        }

        int j = text.length() -1;
        while(j >= 0){
            int b = dp[0][j];
            while(dp[b][j] != b)b = dp[b][j];
            if(!JPStopWords.stopWords.contains(text.substring(b, j+1)))
                res.add(0,text.substring(b, j + 1));
            j = b - 1;
        }


        if(res.isEmpty()){
            //throw new IllegalArgumentException("Result is empty after filtering out the stop words");
            System.out.println("ATTENTION: The output is empty after filtering out the stop words.");
        }

        return res;


    }

}

