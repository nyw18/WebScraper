import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class WebScraper {

  private final int MAX_URLS = 100;
  private int printed = 0;

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Wrong number of parameters.");
      return;
    }
    try {
      String startUrl = args[0];
      URL url = new URL(startUrl);
      System.out.println(getPageContent(url));
    } catch (MalformedURLException e) {
      System.out.println("Invalid start Url passed.");
    }
  }

  private static String getPageContent(URL url) {
    try {
      URLConnection htmlPage = url.openConnection();
      Scanner pageScanner = new Scanner(htmlPage.getInputStream());
      return Utils.scannerToString(pageScanner);
    } catch (IOException e) {
      System.out.println("Error getting page content.");
    }
    return null;
  }
}
