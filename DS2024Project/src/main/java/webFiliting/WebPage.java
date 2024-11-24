package webFiliting;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class WebPage {
    public String url;
    public String name;
    public WordCounter counter;
    public double score;

    public WebPage(String url, String name){
        this.url = url;
        this.name = name;
        this.counter = new WordCounter(url);
    }

    public void setScore(ArrayList<Keyword> keywords){
        score = 0;
        System.out.println("Calculating score for URL: " + this.url);
        try {
            for(Keyword k : keywords) {
                System.out.println("Counting keyword: " + k.name);
                int keywordCount = this.counter.countKeyword(k.name);
                System.out.println("Keyword count for '" + k.name + "': " + keywordCount);
                double s = k.weight * keywordCount;
                score += s;
                System.out.printf("%s-%f ; ", k.name, s);
            }
        } catch (UnknownHostException e) {
            System.out.printf("UnknownHostException, skip\n");
        } catch (IOException e) {
            System.out.printf("IOException, skip\n");
        }
        System.out.println();
        System.out.println("Total score: " + this.score);
    }
}