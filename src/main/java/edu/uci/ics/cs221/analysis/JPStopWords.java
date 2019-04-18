package edu.uci.ics.cs221.analysis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A list of stop words.
 * Please use this list and don't change it for uniform behavior in testing.
 */
public class JPStopWords {

    public static Set<String> stopWords = new HashSet<>();
    static {
        stopWords.addAll(Arrays.asList(
                "は",
                "も",
                "が",
                "の",
                "て",
                "から",
                "より",
                "ほど",
                "ね",
                "よ",
                "そうだ",
                "に",
                "と",
                "や",
                "である",
                "ない",
                "を",
                "で",
                "さ",
                "ぜ",
                "のか",
                "のに",
                "かな",
                "わ",
                "もん",
                "わけ",
                "なんか"

        ));
    }

}
