package webFiliting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.demo.GoogleQuery;

public class WebTree {
	private WebNode root;
	private ArrayList<Keyword> keywords = GoogleQuery.keywordList;
	private String rootName;

	public WebTree(WebPage rootPage) {
		this.setRoot(new WebNode(rootPage));
		this.rootName = rootPage.name;
		keywords = new ArrayList<Keyword>();
		try {
			getSubWebPage(this.getRoot());
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

	private String fetchContent(String url) throws IOException {
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		// set HTTP header
		//當程式需要以自動化方式訪問網頁時，使用 User-Agent 模擬瀏覽器，讓伺服器誤認為這是一個真實的瀏覽器訪問。
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while ((line = bufReader.readLine()) != null) {
			retVal += line;
		}
		return retVal;
	}

	public void getSubWebPage(WebNode rootNode) throws IOException {
		String rootContentString;
		// Document doc = Jsoup.connect(rootUrlString).get();
		rootContentString = fetchContent(rootNode.webPage.url);
		Document doc = Jsoup.parse(rootContentString);

		Elements links = doc.select("a[href]");
		int subWebNum = 0;
		for (Element link : links) {
			String href = link.attr("href");
			href = URLDecoder.decode(href, "UTF-8");
			// Check if the href is a valid URL
			if (subWebNum > 1)
				break;
			try {
				subWebNum++;
				new URL(href);
				this.getRoot().addChild(new WebNode(new WebPage(href, link.text())));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Skipping invalid URL: " + href);
			}
		}
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException {
		setPostOrderScore(getRoot(), keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {

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
		eularPrintTree(getRoot());
	}

	private void eularPrintTree(WebNode startNode) {
		int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1)
			System.out.print("\n" + repeat("\t", nodeDepth - 1));

		System.out.print("(");
		System.out.print(startNode.webPage.name + "," + startNode.nodeScore);

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

	public String getRootName() {
		// TODO Auto-generated method stub
		return this.rootName;
	}

	public WebNode getRoot() {
		return root;
	}

	public void setRoot(WebNode root) {
		this.root = root;
	}
}
