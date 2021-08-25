package code.challenge.authorizer.validation;

import code.challenge.authorizer.model.Account;
import code.challenge.authorizer.model.Operation;
import code.challenge.authorizer.model.Transaction;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Validator {

  public boolean validate(Operation operation, List<Transaction> transactions, Account account) {
    List<String> violations = new LinkedList<String>();

    if (Objects.nonNull(operation.getAccount())) {
      if (Objects.nonNull(account)) {
        violations.add("account-already-initialized");
        return setViolations(operation, account, violations);
      }
    }

    if (Objects.nonNull(operation.getTransaction())) {
      if (Objects.isNull(account)) {
        violations.add("account-not-initialized");
        return setViolations(operation, account, violations);
      }

      if (!account.isActiveCard()) {
        violations.add("card-not-active");
        return setViolations(operation, account, violations);
      }

      Transaction transaction = operation.getTransaction();
      if (transaction.getAmount() > account.getAvailableLimit()) {
        violations.add("insufficient-limit");
      }
      if (hasHighFrequency(transaction, transactions)) {
        violations.add("high-frequency-small-interval");
      }
      if (hasDoubledTransaction(transaction, transactions)) {
        violations.add("doubled-transaction");
      }

      if (!violations.isEmpty()) {
        return setViolations(operation, account, violations);
      }
    }

    operation.setViolations(new String[0]);
    return true;
  }

  private boolean hasHighFrequency(Transaction transaction, List<Transaction> transactions) {
    if (transactions.size() < 3) { // should get this from properties
      return false;
    }
    Date date = transactions.get(transactions.size() - 3).getTime();
    return betweenInterval(date, transaction.getTime(), 2);
  }

  private boolean hasDoubledTransaction(Transaction transaction, List<Transaction> transactions) {

    if (transactions.isEmpty()) {
      return false;
    }

    for (int i = transactions.size() - 1; i >= 0; i--) {
      if (!betweenInterval(transactions.get(i).getTime(), transaction.getTime(), 2)) {
        return false;
      }
      if (transaction.getMerchant().equals(transactions.get(i).getMerchant())
          && transaction.getAmount() == transactions.get(i).getAmount()) {
        return true;
      }
    }
    return false;
  }

  private boolean betweenInterval(Date before, Date after, int intervalInMinutes) {
    long difference = after.getTime() - before.getTime();
    return difference <= 1000 * 60 * intervalInMinutes;
  }

  private boolean setViolations(Operation operation, Account account, List<String> violations) {
    operation.setTransaction(null);
    operation.setAccount(account);
    operation.setViolations(violations.toArray(new String[0]));
    return false;
  }
}
