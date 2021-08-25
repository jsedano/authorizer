package code.challenge.authorizer;

import code.challenge.authorizer.parser.Parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class App {

  public static void main(String[] args) throws IOException {
    Parser parser = new Parser();
    System.out.println("start");
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.isBlank()) {
          System.out.println("Read: " + line);
          System.out.println(parser.write(parser.read(line)));
        } else {
          break;
        }
      }
    }
    System.out.println("end");
  }
}
