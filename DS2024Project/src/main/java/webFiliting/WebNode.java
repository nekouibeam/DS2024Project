package webFiliting;

import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;
	public double nodeScore;// This node's score += all its children's nodeScore

	public WebNode(WebPage webPage)
	{
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}

	//c.
	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException{
		this.webPage.setScore(keywords);
		this.nodeScore = webPage.score;
		if(this.children.isEmpty() == false) {
			for(WebNode w : this.children) {
				this.nodeScore += w.nodeScore;
			}
		}
	}

	public void addChild(WebNode child){
		// add the WebNode to its children list
		this.children.add(child);
		child.parent = this;
	}

	public boolean isTheLastChild(){
		if (this.parent == null)
			return true;
		ArrayList<WebNode> siblings = this.parent.children;

		return this.equals(siblings.get(siblings.size() - 1));
	}

	public int getDepth(){
		int retVal = 1;
		WebNode currNode = this;
		while (currNode.parent != null)
		{
			retVal++;
			currNode = currNode.parent;
		}
		return retVal;
	}
}
