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
    if (!url.startsWith("http")) {
      // remove front slash to prevent double slash in url
      if (!url.startsWith("/")) {
        url = url.substring(1);
      }
      url = currentPage + url.substring(1);
    }
    return url;
  }
}
