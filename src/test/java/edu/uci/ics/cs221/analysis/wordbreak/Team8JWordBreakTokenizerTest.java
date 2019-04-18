package edu.uci.ics.cs221.analysis.wordbreak;

import edu.uci.ics.cs221.analysis.JWordBreakTokenizer;
import edu.uci.ics.cs221.analysis.WordBreakTokenizer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class Team8JWordBreakTokenizerTest {


    @Test
    public void test1() {
        String text = "私はあなたを愛しています";
        List<String> expected = Arrays.asList("私", "あなた", "愛", "し", "てい", "ます");

        JWordBreakTokenizer tokenizer = new JWordBreakTokenizer();

        assertEquals(expected, tokenizer.tokenize(text));

    }

    @Test
    public void test2(){
        String text = "今夜は月明かりが美しい";
        List<String> expected = Arrays.asList("今夜", "月", "明かり", "美しい");

        JWordBreakTokenizer tokenizer = new JWordBreakTokenizer();

        assertEquals(expected, tokenizer.tokenize(text));
    }

    @Test
    public void test3(){
        String text = "ここは静かです";
        List<String> expected = Arrays.asList("ここ", "静か", "です");

        JWordBreakTokenizer tokenizer = new JWordBreakTokenizer();

        assertEquals(expected, tokenizer.tokenize(text));
    }

    @Test
    public void test4(){
        String text = "机は教室にあります";
        List<String> expected = Arrays.asList("机", "教室", "あり", "ます");

        JWordBreakTokenizer tokenizer = new JWordBreakTokenizer();

        assertEquals(expected, tokenizer.tokenize(text));
    }

    @Test
    public void test5(){
        String text = "ドラえもんとりわけ";
        List<String> expected = Arrays.asList("ドラえもん", "とりわけ");

        JWordBreakTokenizer tokenizer = new JWordBreakTokenizer();

        assertEquals(expected, tokenizer.tokenize(text));
    }
    
    @Test(expected = RuntimeException.class)
    public void test6(){
    	String text = "这不是日语";
        //throw exception
    	JWordBreakTokenizer tokenizer = new JWordBreakTokenizer();
        tokenizer.tokenize(text);

    }

}
