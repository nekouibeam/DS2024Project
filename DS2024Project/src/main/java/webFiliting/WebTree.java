package webFiliting;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.demo.GoogleQuery;

public class WebTree {
	public WebNode root;
	public ArrayList<Keyword> keywords = GoogleQuery.keywordList;

	public WebTree(WebPage rootPage) {
		this.root = new WebNode(rootPage);
		keywords = new ArrayList<Keyword>();
		try {
			getSubWebPage(this.root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			setPostOrderScore(keywords);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//UndoTask: 過濾(處理)不可爬蟲之網站
	public void getSubWebPage(WebNode rootNode) throws IOException {
		String rootUrlString = rootNode.webPage.url;
	    Document doc = Jsoup.connect(rootUrlString).get();
	    Elements links = doc.select("a[href]");
	    int subWebNum = 0;
	    for (Element link : links) {
	        String href = link.attr("href");
	        href = URLDecoder.decode(href, "UTF-8");
	        // Check if the href is a valid URL
	        if(subWebNum >10) break;
	        try {
	        	subWebNum++;
	            new URL(href);
	            // If valid, add the child node
	            this.root.addChild(new WebNode(new WebPage(href, link.text())));
	        } catch (MalformedURLException e) {
	        	try {
	        		String href2 = rootNode.webPage.url + href;
	        		new URL(href2);
	        		this.root.addChild(new WebNode(new WebPage(href2, link.text())));
	        	}catch (MalformedURLException e1) {
	        		// If not valid, print a message and skip
		            System.out.println("Skipping invalid URL: " + href);
				} 
	        }
	    }
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException {
		setPostOrderScore(root, keywords);
	}

	// b.
	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {
		// YOUR TURN
		// 3. compute the score of children nodes via post-order, then setNodeScore for
		// startNode
		if (startNode.children.isEmpty() == true) {
			startNode.setNodeScore(keywords);
		} else {
			for (WebNode w : startNode.children) {
				setPostOrderScore(w, keywords);
			}
			startNode.setNodeScore(keywords);
		}
	}

	public void eularPrintTree() {
		eularPrintTree(root);
	}

	// f.
	private void eularPrintTree(WebNode startNode) {
		int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1)
			System.out.print("\n" + repeat("\t", nodeDepth - 1));

		System.out.print("(");
		System.out.print(startNode.webPage.name + "," + startNode.nodeScore);
		// YOUR TURN
		// 4. print child via pre-order
		if (startNode.children.isEmpty() == false) {
			for (WebNode w : startNode.children) {
				eularPrintTree(w);
			}
		}
		System.out.print(")");

		if (startNode.isTheLastChild())
			System.out.print("\n" + repeat("\t", nodeDepth - 2));
	}

	private String repeat(String str, int repeat) {
		String retVal = "";
		for (int i = 0; i < repeat; i++) {
			retVal += str;
		}
		return retVal;
	}
}
