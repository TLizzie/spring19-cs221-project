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


/**
 * Project 1, task 2: Implement a Dynamic-Programming based Word-Break Tokenizer.
 *
 * Word-break is a problem where given a dictionary and a string (text with all white spaces removed),
 * determine how to break the string into sequence of words.
 * For example:
 * input string "catanddog" is broken to tokens ["cat", "and", "dog"]
 *
 * We provide an English dictionary corpus with frequency information in "resources/cs221_frequency_dictionary_en.txt".
 * Use frequency statistics to choose the optimal way when there are many alternatives to break a string.
 * For example,
 * input string is "ai",
 * dictionary and probability is: "a": 0.1, "i": 0.1, and "ai": "0.05".
 *
 * Alternative 1: ["a", "i"], with probability p("a") * p("i") = 0.01
 * Alternative 2: ["ai"], with probability p("ai") = 0.05
 * Finally, ["ai"] is chosen as result because it has higher probability.
 *
 * Requirements:
 *  - Use Dynamic Programming for efficiency purposes.
 *  - Use the the given dictionary corpus and frequency statistics to determine optimal alternative.
 *      The probability is calculated as the product of each token's probability, assuming the tokens are independent.
 *  - A match in dictionary is case insensitive. Output tokens should all be in lower case.
 *  - Stop words should be removed.
 *  - If there's no possible way to break the string, throw an exception.
 *
 */

public class WordBreakTokenizer implements Tokenizer {

    private HashMap<String, Double> dictmap;

    public WordBreakTokenizer() {
        try {
            // load the dictionary corpus
            URL dictResource = WordBreakTokenizer.class.getClassLoader().getResource("1cs221_frequency_dictionary_en.txt");
            List<String> dictLines = Files.readAllLines(Paths.get(dictResource.toURI()));

            Double total = 0.0;

            dictmap = new HashMap<>();

            for(int i = 0; i < dictLines.size(); ++i){
                String tmp = dictLines.get(i);
                if (tmp.startsWith("\uFEFF")) {
                    tmp = tmp.substring(1);
                }

                List<String> contains = Arrays.asList(tmp.split("\\s+"));
                String key = contains.get(0).toLowerCase();
                Double value = Double.valueOf(contains.get(1));
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
            throw  new RuntimeException("The original text can not be broken into words");
        }

        int j = text.length() -1;
        while(j >= 0){
            int b = dp[0][j];
            while(dp[b][j] != b)b = dp[b][j];
            if(!StopWords.stopWords.contains(text.substring(b, j+1)))
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

