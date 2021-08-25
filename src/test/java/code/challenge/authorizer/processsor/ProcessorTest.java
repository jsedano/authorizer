package code.challenge.authorizer.processsor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import code.challenge.authorizer.model.Account;
import code.challenge.authorizer.model.Operation;
import code.challenge.authorizer.model.Transaction;
import code.challenge.authorizer.processor.Processor;
import code.challenge.authorizer.validation.Validator;
import java.util.List;
import org.junit.jupiter.api.Test;

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

  @Test
  public void validatorReturningTrueTest() {
    Validator validator = mock(Validator.class);

    when(validator.validate(any(), any(), any())).thenReturn(true);

    Processor processor = new Processor(validator);

    Operation operation = mock(Operation.class);
    Transaction transaction = mock(Transaction.class);
    List<Transaction> transactions = mock(List.class);
    Account account = mock(Account.class);

    when(operation.getTransaction()).thenReturn(transaction);
    when(account.getAvailableLimit()).thenReturn(100);
    when(transaction.getAmount()).thenReturn(50);
    assertTrue(processor.process(operation, transactions, account));

    verify(transactions, times(1)).add(transaction);
    verify(account, times(1)).setAvailableLimit(50);
    verify(account, times(1)).getAvailableLimit();
    verify(transaction, times(1)).getAmount();
    verify(operation, times(1)).setTransaction(null);
    verify(operation, times(1)).setAccount(account);
  }
}
