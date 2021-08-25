package code.challenge.authorizer.parser;

import code.challenge.authorizer.model.Operation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.TimeZone;

public class Parser {
  private ObjectReader objectReader;
  private ObjectWriter objectWriter;

  public Parser() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
    objectReader = objectMapper.readerFor(Operation.class);
    objectWriter = objectMapper.writerFor(Operation.class);
  }

  public Operation read(String jsonString) throws IOException {
    return objectReader.readValue(jsonString, Operation.class);
  }

  public String write(Operation operation) throws IOException {
    String result = objectWriter.writeValueAsString(operation);
    if (Objects.isNull(operation.getAccount())) {
      result = "{\"account\":{}," + result.substring(1);
      // this is ugly, I didn't wanted to spend a lot of time figuring out how to accomplish this
      // with jackson
    }
    return result;
  }
}
