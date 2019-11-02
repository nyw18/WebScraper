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
  private static Set<String> accessedUrlSet;
  private static Set<String> unaccessedUrlSet;
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
    while (!unaccessedUrlSet.isEmpty() && urlListSize < MAX_URLS) {
      String currentUrlString = getNextUrl();
      try {
        URL currentUrl = new URL(currentUrlString);
        String pageContent = getPageContent(currentUrl);
        if (pageContent != null) {
          getLinks(pageContent, currentUrlString);
        }
      } catch (MalformedURLException e) {
        System.out.println("Attempted access to invalid url" + currentUrlString);
      }
    }
  }

  private static String getNextUrl() {
    String nextUrl = unaccessedUrlSet.iterator().next();
    unaccessedUrlSet.remove(nextUrl);
    accessedUrlSet.add(nextUrl);
    return nextUrl;
  }

  private static void init(String url) {
    accessedUrlSet = new HashSet<>();
    unaccessedUrlSet = new HashSet<>();
    unaccessedUrlSet.add(url);
    urlListSize = 0;
  }

  private static String getPageContent(URL url) {
    try {
      URLConnection htmlPage = url.openConnection();
      Scanner pageScanner = new Scanner(htmlPage.getInputStream());
      return Utils.scannerToString(pageScanner);
    } catch (IOException e) {
      System.out.println("Error getting page content of url:" + url.toString());
    }
    return null;
  }

  private static void getLinks(String page, String currentUrl) {
    // put url in list and set if unique and printed++
    String[] lines = page.split(">");
    Pattern linkPattern = Pattern.compile(".*<a.+href=\"([^\\\"]+)\"");
    for (String line : lines) {
      if (urlListSize > MAX_URLS) {
        break;
      }
      Matcher linkMatcher = linkPattern.matcher(line);
      if (linkMatcher.find()) {
        String thisUrl = Utils.fixRelativeUrls(linkMatcher.group(1), currentUrl);
        if (thisUrl != null && !unaccessedUrlSet.contains(thisUrl) && !accessedUrlSet.contains(thisUrl)) {
          unaccessedUrlSet.add(thisUrl);
          urlListSize++;
          System.out.println(thisUrl);
        }
      }
    }
  }
}
