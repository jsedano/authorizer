package code.challenge.authorizer.processsor;

import code.challenge.authorizer.model.Account;
import code.challenge.authorizer.model.Operation;
import code.challenge.authorizer.model.Transaction;
import code.challenge.authorizer.parser.Parser;
import code.challenge.authorizer.processor.Processor;
import code.challenge.authorizer.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ProcessorTest {




  @Test
  public void shouldNotDoAnythingOnValidatorReturningFalseTest() {
    Validator validator = mock(Validator.class);

    when(validator.validate(any(), any(), any())).thenReturn(false);

    Processor processor = new Processor(validator);
    Operation operation = mock(Operation.class);
    List<Transaction> transactions = mock(List.class);
    Account account = mock(Account.class);


    assertFalse(processor.process(operation, transactions, account));

    verifyNoMoreInteractions(operation, transactions, account);
  }
}
