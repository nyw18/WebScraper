import java.util.Scanner;

class Utils {

  static String scannerToString(Scanner scanner) {
    StringBuilder scannedText = new StringBuilder();
    while (scanner.hasNext()) {
      scannedText.append(scanner.nextLine());
    }
    return scannedText.toString();
  }

  static String fixRelativeUrls(String url, String currentPage) {
    assert(url.length() > 0);
    if (!url.startsWith("http")) {
      // remove front slash to prevent double slash in url
      if (!url.startsWith("/") && !currentPage.endsWith("/")) {
        url = "/" + url;
      } else if (url.startsWith("/")) {
        //todo:get root url (root domain regex:http.:\/\/.+[\/])
        url = url.substring(1);
      }
      url = currentPage + url;
    }
    return url;
  }
}
