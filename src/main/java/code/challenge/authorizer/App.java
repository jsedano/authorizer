package code.challenge.authorizer;

import code.challenge.authorizer.model.Account;
import code.challenge.authorizer.model.Operation;
import code.challenge.authorizer.parser.Parser;
import code.challenge.authorizer.processor.Processor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class App {

  public static void main(String[] args) throws IOException {
    Processor processor = new Processor();
    Parser parser = new Parser();
    Account account = null;

    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.isBlank()) {
          Operation operation = parser.read(line);

          if (processor.process(operation, account)) {
            account = operation.getAccount();
          }

          System.out.println(parser.write(operation));

        } else {
          break;
        }
      }
    }
    System.out.println("end");
  }
}
