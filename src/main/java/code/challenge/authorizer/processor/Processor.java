package code.challenge.authorizer.processor;

import code.challenge.authorizer.model.Account;
import code.challenge.authorizer.model.Operation;
import code.challenge.authorizer.model.Transaction;
import code.challenge.authorizer.validation.Validator;
import java.util.List;
import java.util.Objects;

public class Processor {

  private Validator validator;

  public Processor(Validator validator) {
    this.validator = validator;
  }

  public boolean process(Operation operation, List<Transaction> transactions, Account account) {
    if (!validator.validate(operation, transactions, account)) {
      return false;
    }

    if (Objects.nonNull(operation.getTransaction())) {
      transactions.add(operation.getTransaction());
      account.setAvailableLimit(
          account.getAvailableLimit() - operation.getTransaction().getAmount());
      operation.setTransaction(null);
      operation.setAccount(account);
    }

    return true;
  }
}
