import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) throws IOException {
		// 獲取HTML頁面中的所有鏈接
		Document doc = Jsoup.connect("http://www.yiibai.com").get();
		Elements links = doc.select("a[href]");
		WebPage rootPage = new WebPage("http://www.yiibai.com", "yiibai");		
		WebTree tree = new WebTree(rootPage);
		/*for (Element link : links) {
			tree.root.addChild(new WebNode(new WebPage(link.attr("href"), link.text())));
		}
		for (WebNode childreNode : tree.root.children) {
			System.out.println("link : " + childreNode.webPage.url);
			System.out.println("text : " + childreNode.webPage.name);
		}*/
		ArrayList<Keyword> keywords;
		keywords = new ArrayList<Keyword>();
		keywords.add(new Keyword("Java", 5));
		keywords.add(new Keyword("Spring", 10));
		keywords.add(new Keyword("新手", 20));
		
		tree.setPostOrderScore(keywords);
		tree.eularPrintTree(); 
	}
}