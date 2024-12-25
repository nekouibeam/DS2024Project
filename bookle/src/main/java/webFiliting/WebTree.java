package webFiliting;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import com.example.demo.GoogleQuery;

public class WebTree {
	private WebNode root;
	private ArrayList<Keyword> keywords;
	private String rootName;

	public WebTree(WebPage rootPage) throws SocketTimeoutException {
		this.setRoot(new WebNode(rootPage));
		this.rootName = rootPage.name;
		keywords = GoogleQuery.keywordList;
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
		eularPrintTree();
	}

	public void getSubWebPage(WebNode rootNode) throws IOException {
	   SubWebpageGetter getter = new SubWebpageGetter();
	   getter.getSubWebPage(rootNode);
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