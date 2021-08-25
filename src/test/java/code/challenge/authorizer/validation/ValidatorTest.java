package code.challenge.authorizer.validation;

import static org.junit.jupiter.api.Assertions.*;

import code.challenge.authorizer.model.Account;
import code.challenge.authorizer.model.Operation;
import code.challenge.authorizer.model.Transaction;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ValidatorTest {

  private static final Validator validator = new Validator();

  @Test
  public void successfulAccountCreationOperationTest() {
    Operation operation =
        Operation.builder()
            .account(Account.builder().activeCard(true).availableLimit(100).build())
            .build();
    assertTrue(validator.validate(operation, Collections.emptyList(), null));
    assertArrayEquals(new String[0], operation.getViolations());
  }

  @Test
  public void successfulTransactionTest() {
    Operation operation =
        Operation.builder()
            .transaction(
                Transaction.builder().amount(200).merchant("merchant").time(new Date()).build())
            .build();
    assertTrue(
        validator.validate(
            operation,
            Collections.emptyList(),
            Account.builder().activeCard(true).availableLimit(200).build()));
    assertArrayEquals(new String[0], operation.getViolations());
  }

  @Test
  public void failedAccountCreationOperationTest() {
    Operation operation =
        Operation.builder()
            .account(Account.builder().activeCard(true).availableLimit(100).build())
            .build();
    assertFalse(validator.validate(operation, Collections.emptyList(), new Account()));
    assertArrayEquals(new String[] {"account-already-initialized"}, operation.getViolations());
  }

  @Test
  public void cardNotActiveTest() {
    Operation operation =
        Operation.builder()
            .transaction(
                Transaction.builder().amount(200).merchant("merchant").time(new Date()).build())
            .build();
    assertFalse(
        validator.validate(
            operation,
            Collections.emptyList(),
            Account.builder().activeCard(false).availableLimit(100).build()));
    assertArrayEquals(new String[] {"card-not-active"}, operation.getViolations());
  }

  @Test
  public void insufficientLimitTest() {
    Operation operation =
        Operation.builder()
            .transaction(
                Transaction.builder().amount(200).merchant("merchant").time(new Date()).build())
            .build();
    assertFalse(
        validator.validate(
            operation,
            Collections.emptyList(),
            Account.builder().activeCard(true).availableLimit(100).build()));
    assertArrayEquals(new String[] {"insufficient-limit"}, operation.getViolations());
  }

  @Test
  public void highFrequencySmallIntervalTest() {
    List<Transaction> transactionList = new LinkedList<Transaction>();
    transactionList.add(
        Transaction.builder().amount(200).merchant("merchant1").time(new Date()).build());
    transactionList.add(
        Transaction.builder().amount(200).merchant("merchant2").time(new Date()).build());
    transactionList.add(
        Transaction.builder().amount(200).merchant("merchant3").time(new Date()).build());

    Operation operation =
        Operation.builder()
            .transaction(
                Transaction.builder().amount(200).merchant("merchant4").time(new Date()).build())
            .build();
    assertFalse(
        validator.validate(
            operation,
            transactionList,
            Account.builder().activeCard(true).availableLimit(200).build()));
    assertArrayEquals(new String[] {"high-frequency-small-interval"}, operation.getViolations());
  }

  @Test
  public void doubledTransactionTest() {
    List<Transaction> transactionList = new LinkedList<Transaction>();
    transactionList.add(
        Transaction.builder().amount(200).merchant("merchant").time(new Date()).build());

    Operation operation =
        Operation.builder()
            .transaction(
                Transaction.builder().amount(200).merchant("merchant").time(new Date()).build())
            .build();
    assertFalse(
        validator.validate(
            operation,
            transactionList,
            Account.builder().activeCard(true).availableLimit(200).build()));
    assertArrayEquals(new String[] {"doubled-transaction"}, operation.getViolations());
  }

  @Test
  public void multipleViolationTest() {
    List<Transaction> transactionList = new LinkedList<Transaction>();
    transactionList.add(
        Transaction.builder().amount(200).merchant("merchant1").time(new Date()).build());
    transactionList.add(
        Transaction.builder().amount(200).merchant("merchant2").time(new Date()).build());
    transactionList.add(
        Transaction.builder().amount(200).merchant("merchant3").time(new Date()).build());

    Operation operation =
        Operation.builder()
            .transaction(
                Transaction.builder().amount(200).merchant("merchant3").time(new Date()).build())
            .build();

    assertFalse(
        validator.validate(
            operation,
            transactionList,
            Account.builder().activeCard(true).availableLimit(100).build()));
    assertArrayEquals(
        new String[] {"insufficient-limit", "high-frequency-small-interval", "doubled-transaction"},
        operation.getViolations());
  }
}
