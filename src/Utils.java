import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Utils {

  static String scannerToString(Scanner scanner) {
    StringBuilder scannedText = new StringBuilder();
    while (scanner.hasNext()) {
      scannedText.append(scanner.nextLine());
    }
    return scannedText.toString();
  }

  static String fixRelativeUrls(String url, String currentPage) {
    assert (url.length() > 0);
    if (!url.startsWith("http")) {
      if (!url.contains(":")) {
        // modify urls as necessary
        if (!url.startsWith("/") && !currentPage.endsWith("/")) {
          // relative url from current page
          url = currentPage + "/" + url;
        } else if (url.startsWith("/")) {
          // relative url from root
          Pattern rootPattern = Pattern.compile("(https?:\\/\\/[^\\/\\n]+)");
          Matcher rootMatcher = rootPattern.matcher(currentPage);
          rootMatcher.find();
          String rootUrl = rootMatcher.group(1);
          url = rootUrl + url;
        } else {
          url = currentPage + url;
        }
      } else {
        return null;
      }
    }
    return url;
  }
}
