import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main_trustAllCertificates {

    public static void main(String[] args) throws IOException {
        // 設置忽略SSL證書
        trustAllCertificates();

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

    private static void trustAllCertificates() {
        try {
            // 創建一個允許所有證書的TrustManager
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
            };

            // 設置SSLContext
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // 設置允許所有主機名的HostNameVerifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}