import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScraper {

  private static final int MAX_URLS = 100;
  private static int urlListSize;
  private static Set<String> urlSet;
  private static List<String> searchList;

  public static void main(String[] args) {
    // check args are valid
    if (args.length != 1) {
      System.out.println("Wrong number of parameters.");
      return;
    }
    init(args[0]);
    // while searchList != empty and set < MAX_URLS
    // --get next from searchList and get more urls
    // while end
    // print first MAX_URLS urls

    try {
      String startUrl = args[0];
      URL url = new URL(startUrl);
      String pageContent = getPageContent(url);
      if (pageContent != null) {
        printLinks(pageContent, startUrl);
        // System.out.println(pageContent);
      }
    } catch (MalformedURLException e) {
      System.out.println("Invalid start Url passed.");
    }
  }

  private static void init(String url) {
    urlSet = new HashSet<>();
    searchList = new ArrayList<>();
    searchList.add(url);
    urlListSize = 0;
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

  private static void printLinks(String page, String currentUrl) {
    // split lines with ">"
    // search for <a start and then find href=....
    // put url in list and set if unique and printed++
    String[] lines = page.split(">");
    Pattern linkPattern = Pattern.compile(".*<a.+href=\"([^\\\"]+)\"");
    for (String line : lines) {
      if (urlListSize > MAX_URLS) {
        break;
      }
      Matcher linkMatcher = linkPattern.matcher(line);
      if (linkMatcher.find()) {
        // add and do stuff

        System.out.println(Utils.fixRelativeUrls(linkMatcher.group(1),currentUrl));
      }
    }
  }
}
