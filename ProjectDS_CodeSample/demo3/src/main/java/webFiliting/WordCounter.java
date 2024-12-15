package webFiliting;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WordCounter {
	private String content;

	public WordCounter(String content) {
		this.content = content;
	}

	public int countKeyword(String keyword) throws IOException, UnknownHostException {

		// Use Jsoup to parse HTML and extract <body> content
		Document doc = Jsoup.parse(content);
		Element body = doc.body();

		if (body == null) {
			System.out.println("No <body> content found in the URL: ");
			return 0;
		}

		String bodyText = body.text(); // Extract plain text from <body>
		bodyText = bodyText.toUpperCase();
		keyword = keyword.toUpperCase();

		int retVal = 0;
		int fromIdx = 0;
		int found;

		// Count keyword occurrences in the <body> text
		while ((found = bodyText.indexOf(keyword, fromIdx)) != -1) {
			retVal++;
			fromIdx = found + keyword.length();
		}

		System.out.println("Keyword '" + keyword + "' found " + retVal + " times in <body> of URL.");

		return retVal;
	}
}
