package webFiliting;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.jsoup.HttpStatusException;

public class WebPage {
    public String url;
    public String name;
    public WordCounter counter;
    public double score;
    public String htmlString;
    private FetchHtmlContent fetchHtmlContent;

    public WebPage(String url, String name) throws IOException, SocketTimeoutException, HttpStatusException{
        this.url = url;
        this.name = name;
        this.fetchHtmlContent = new FetchHtmlContent();
        htmlString = fetchHtmlContent.fetchContent(url); //SocketTimeoutException
        this.counter = new WordCounter(htmlString);
    }

    public void setScore(ArrayList<Keyword> keywords){
        score = 0;
        //System.out.println("Calculating score for URL: " + this.url);
        try {
        	if (keywords.isEmpty()) {
        	    //System.out.println("No keywords provided.");
        	}

            for(Keyword k : keywords) {
                //System.out.println("Counting keyword: " + k.name);
                int keywordCount = this.counter.countKeyword(k.name);
                //System.out.println("Keyword count for '" + k.name + "': " + keywordCount);
                double s = k.weight * keywordCount;
                score += s;
                //System.out.printf("%s-%f ; \n", k.name, s);
            }
        } catch (UnknownHostException e) {
            System.out.printf("UnknownHostException, skip\n");
        } catch (IOException e) {
            System.out.printf("IOException, skip\n");
        } catch (Exception e) {
            e.printStackTrace();  // 捕獲所有其他異常並打印
        }
    }
    
    public void setHtmlString(String g) {
		this.htmlString = g;
	}
}