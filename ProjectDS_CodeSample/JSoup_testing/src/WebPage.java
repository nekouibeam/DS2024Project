

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

	//d.
	public void setScore(ArrayList<Keyword> keywords){
		score = 0;
		// YOUR TURN
		// 1. calculate the score of this webPage
		System.out.println(this.url + " :");
		try {
			for(Keyword k : keywords) {
				double s = k.weight * this.counter.countKeyword(k.name);
				score += s;
				System.out.printf("%s-%f ; ",k.name,s);
				//score += k.weight * this.counter.countKeyword(k.name);
			}
		} catch (UnknownHostException e) {
			// TODO: handle exception
			System.out.printf("UnknownHostException, skip");
		} catch (IOException e) {
			// TODO: handle exception
			System.out.printf("IOException, skip");
		}
		System.out.println();
		System.out.println("Total score:" + this.score);
	}
}
