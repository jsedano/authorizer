package code.challenge.authorizer;

import code.challenge.authorizer.model.Account;
import code.challenge.authorizer.model.Operation;
import code.challenge.authorizer.model.Transaction;
import code.challenge.authorizer.parser.Parser;
import code.challenge.authorizer.processor.Processor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App {

  public static void main(String[] args) throws IOException {
    Processor processor = new Processor();
    Parser parser = new Parser();
    List<Transaction> transactions = new ArrayList<Transaction>();
    Account account = null;
    List<String> output = new LinkedList<String>();

    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.isBlank()) {
          Operation operation = parser.read(line);

          if (processor.process(operation, transactions, account)) {
            account = operation.getAccount();
          }

          output.add(parser.write(operation));

        } else {
          for (String o : output) {
            System.out.println(o);
          }
          break;
        }
      }
    }
  }
}
