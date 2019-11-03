import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Utils {

  // Returns all the text in a scanner as a string
  static String scannerToString(Scanner scanner) {
    StringBuilder scannedText = new StringBuilder();
    while (scanner.hasNext()) {
      scannedText.append(scanner.nextLine());
    }
    return scannedText.toString();
  }

  // Perform fixes to relative url strings to ensure they are valid urls
  static String fixRelativeUrls(String url, String currentPage) {
    assert (url.length() > 0);

    // only modify non http urls
    if (!url.startsWith("http")) {
      if (!url.contains(":")) {
        // modify urls as necessary

        if (!url.startsWith("/")) {
          // relative url
          if (!currentPage.endsWith("/")) {
            url = "/" + url;
          }
          url = currentPage + url;
        } else {
          // relative url from root
          // find the root url up to (and not including) the first slash
          Pattern rootPattern = Pattern.compile("(https?:\\/\\/[^\\/\\n]+)");
          Matcher rootMatcher = rootPattern.matcher(currentPage);
          rootMatcher.find();
          String rootUrl = rootMatcher.group(1);
          url = rootUrl + url;
        }
      } else {
        // return null if using another protocol
        return null;
      }
    }
    return url;
  }
}
