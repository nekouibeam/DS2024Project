
public class BFF_BFB_practice {

	public static void main(String[] args) {

		String s = "ACCGTACATCTT";
		String aim = "ACAT";

		System.out.println("從頭算的方法BFF算了" + methodBFF(s, aim) + "次");
		System.out.println("從後面算的方法BFB算了" + methodBFB(s, aim) + "次");
		System.out.println("博耶-穆爾字串搜尋演算法的方法BM算了" + methodBM(s, aim) + "次");
	}
	// 可以導入Scanner並修改程式碼提升泛用性，此為練習從頭or尾開始找字的練習

	// methodBFF函式次數計算實作
	public static int methodBFF(String s, String aim) {

		int counter = 0;
		int index;

		for (index = 0; index <= s.length() - aim.length(); index++) {

			int j = 0;
			while (j < aim.length()) {
				counter++;
				if (s.charAt(index + j) == aim.charAt(j))
					j++;
				else
					break;
			}

			if (j == aim.length())
				break;
		}
		return counter;
	}

	// methodBFB函式次數計算實作
	public static int methodBFB(String s, String aim) {

		int counter = 0;
		int index;

		for (index = aim.length() - 1; index < s.length(); index++) {

			int j = aim.length() - 1;
			while (j >= 0) {
				counter++;
				if (s.charAt(index + j + 1 - aim.length()) == aim.charAt(j))
					j--;
				else
					break;
			}

			if (j == -1)
				break;
		}
		return counter;
	}

	// methodBM函式次數計算實作，我簡單查了一下，好像還有設定壞字符表，這邊是用我想的到的方法試做，沒有設置壞字符表
	public static int methodBM(String s, String aim) {

		int counter = 0;
		int index;

		for (index = aim.length() - 1; index < s.length(); index++) {

			int j = aim.length() - 1; // 3
			while (j >= 0) {
				counter++;
				if (s.charAt(index + j + 1 - aim.length()) == aim.charAt(j))
					j--;
				else if (s.charAt(index) == 'A' || s.charAt(index) == 'T') {
					index += 0;
					break;
				} else if (s.charAt(index) == 'C') {
					index += 1;
					break;
				} else {
					index += 3;
					break;
				}
			}

			if (j == -1)
				break;
		}
		return counter;
	}
}
