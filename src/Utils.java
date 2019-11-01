import java.util.Scanner;

class Utils {

  static String scannerToString(Scanner scanner) {
    StringBuilder scannedText = new StringBuilder();
    while (scanner.hasNext()) {
      scannedText.append(scanner.nextLine());
    }
    return scannedText.toString();
  }
}
