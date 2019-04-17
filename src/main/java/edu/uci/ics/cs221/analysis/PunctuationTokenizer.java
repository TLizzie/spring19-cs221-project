package edu.uci.ics.cs221.analysis;

import edu.uci.ics.cs221.analysis.StopWords;

import java.util.*;

/**
 * Project 1, task 1: Implement a simple tokenizer based on punctuations and white spaces.
 *
 * For example: the text "I am Happy Today!" should be tokenized to ["happy", "today"].
 *
 * Requirements:
 *  - White spaces (space, tab, newline, etc..) and punctuations provided below should be used to tokenize the text.
 *  - White spaces and punctuations should be removed from the result tokens.
 *  - All tokens should be converted to lower case.
 *  - Stop words should be filtered out. Use the stop word list provided in `StopWords.java`
 *
 */
public class PunctuationTokenizer implements Tokenizer {

    public static Set<String> punctuations = new HashSet<>();
    static {
        punctuations.addAll(Arrays.asList(",", ".", ";", "?", "!"));
    }

    public PunctuationTokenizer() {}

    public List<String> tokenize(String text) {
        //throw new UnsupportedOperationException("Punctuation Tokenizer Unimplemented");
        //List<String> tmp = Arrays.asList(text.toLowerCase().split("\\s+"));
        text = text.toLowerCase();
        char[] tmp = text.toCharArray();
        punctuations.add(" ");
        punctuations.add("\t");
        punctuations.add("\n");
        punctuations.add("\r");


        StopWords stopwords = new StopWords();

        List<String> res = new ArrayList<>();


        int begin = 0, length = 0;

        for(int i = 0; i <= text.length(); ++i){
            if(i == text.length()||punctuations.contains(String.valueOf(tmp[i]))){
                if(length != 0){
                    if(!stopwords.stopWords.contains(text.substring(begin, begin + length))){
                        res.add(text.substring(begin, begin + length));
                    }
                    length = 0;
                }
            }
            else{
                if(length == 0){
                    begin = i;
                }
                ++length;
            }

        }
        if(res.isEmpty())System.out.println("Attention the output is empty");
        return res;

    }

}

