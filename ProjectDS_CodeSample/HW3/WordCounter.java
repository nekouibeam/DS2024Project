package HW3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class WordCounter {
	private String urlStr;
	private String content;

	public WordCounter(String urlStr) {
		this.urlStr = urlStr;
	}

	private String fetchContent() throws IOException {
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String retVal = "";

		String line = null;

		while ((line = br.readLine()) != null) {
			retVal = retVal + line + "\n";
		}

		return retVal;
	}

	public int BoyerMoore(String T, String P) {
		int n = T.length(), m = P.length();
		int i = P.length() - 1, j = P.length() - 1, l;
		int retVal = 0;
		while (i <= (n - m)) {
			if (T.charAt(i) == P.charAt(j)) {
				if (j == 0) {
					retVal++;
					i = i + m;
					j = m - 1;
				} else {
					i = i - 1;
					j = j - 1;
				}
			} else {
				l = this.last(T.charAt(i), P);
				i = i + m - this.min(j, 1 + l);
				j = m - 1;
			}
		}
		return retVal;
	}

	public int last(char c, String P) {
		for (int j = P.length() - 1; j >= 0; j--) {
			if(P.charAt(j) == c) {
				return j;
			}
		}
		return -1;
	}

	public int min(int a, int b) {
		if (a < b)
			return a;
		else if (b < a)
			return b;
		else
			return a;
	}

	public int countKeyword(String keyword) throws IOException {
		if (content == null) {
			content = fetchContent();
		}

		// To do a case-insensitive search, we turn the whole content and keyword into
		// upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int retVal = 0;
		// 1. calculates appearances of keyword (Bonus: Implement Boyer-Moore Algorithm)
		retVal = this.BoyerMoore(content, keyword);
		return retVal;
	}
}
