package code.challenge.authorizer.parser;

import static org.junit.jupiter.api.Assertions.*;

import code.challenge.authorizer.model.Operation;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;

public class ParserTest {

  private static final Parser parser = new Parser();

  @Test
  public void readAccountCreationTest() throws IOException {
    Operation operation =
        parser.read("{\"account\": {\"active-card\": true, \"available-limit\": 100}}");

    assertNotNull(operation.getAccountCreation());
    assertTrue(operation.getAccountCreation().isActiveCard());
    assertEquals(100, operation.getAccountCreation().getAvailableLimit());
  }

  @Test
  public void readTransactionAuthorizationTest() throws IOException, ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date date = simpleDateFormat.parse("2019-02-13T11:00:00.000Z");
    Operation operation =
        parser.read(
            "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T11:00:00.000Z\"}}");

    assertNotNull(operation.getTransactionAuthorization());
    assertEquals("Burger King", operation.getTransactionAuthorization().getMerchant());
    assertEquals(20, operation.getTransactionAuthorization().getAmount());
    assertEquals(date, operation.getTransactionAuthorization().getTime());
  }

  @Test
  public void writeValidationResultTest() throws IOException {
    Operation operation =
        parser.read(
            "{\"account\": {\"active-card\": false, \"available-limit\": 100}, \"violations\": [\"card-not-active\"]}");
    assertEquals(
        "{\"account\":{\"active-card\":false,\"available-limit\":100},\"violations\":[\"card-not-active\"]}",
        parser.write(operation));
  }
}
