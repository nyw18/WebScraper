import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WebScraper {

  private static final int MAX_URLS = 100;
  private static int urlListSize;
  private static Set<String> accessedUrlSet;
  private static Set<String> unaccessedUrlSet;
  private static List<String> searchList;

  public static void main(String[] args) {
    // check args are valid
    if (args.length != 1) {
      System.out.println("This program only takes 1 parameter (the starting url),");
      return;
    }
    init(args[0]);
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

  // Return a url from the unaccessedUrlSet adn then place it in the accessed set
  private static String getNextUrl() {
    String nextUrl = unaccessedUrlSet.iterator().next();
    unaccessedUrlSet.remove(nextUrl);
    accessedUrlSet.add(nextUrl);
    return nextUrl;
  }

  // Set everything up with the passed in url as the first url to visit
  private static void init(String url) {
    accessedUrlSet = new HashSet<>();
    unaccessedUrlSet = new HashSet<>();
    unaccessedUrlSet.add(url);
    urlListSize = 1;
  }

  // Return a string containing the html of a page or null if an error is encountered
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

  // Store the links into the url sets and print if necessary
  private static void getLinks(String pageHtml, String currentUrl) {
    // split up the html passed into lines by >
    String[] lines = pageHtml.split(">");
    // match text after "<a" and "href=", which is a link
    Pattern linkPattern = Pattern.compile(".*<a.+href=\"([^\\\"]+)\"");
    for (String line : lines) {
      // only print MAX_URLS number of urls
      if (urlListSize > MAX_URLS) {
        break;
      }
      Matcher linkMatcher = linkPattern.matcher(line);
      // add each matching line to the unaccessed url set if on neither set
      if (linkMatcher.find()) {
        String thisUrl = Utils.fixRelativeUrls(linkMatcher.group(1), currentUrl);
        if (thisUrl != null
            && !unaccessedUrlSet.contains(thisUrl)
            && !accessedUrlSet.contains(thisUrl)) {
          unaccessedUrlSet.add(thisUrl);
          urlListSize++;
          System.out.println(thisUrl);
        }
      }
    }
  }
}
