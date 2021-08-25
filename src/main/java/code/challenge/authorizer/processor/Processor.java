package code.challenge.authorizer.processor;

import code.challenge.authorizer.model.Account;
import code.challenge.authorizer.model.Operation;
import code.challenge.authorizer.validation.Validator;
import java.util.Objects;

public class Processor {

  private Validator validator;

  public Processor() {
    validator = new Validator();
  }

  public boolean process(Operation operation, Account account) {
    if (!validator.validate(operation, account)) {
      return false;
    }

    if (Objects.nonNull(operation.getTransaction())) {
      account.setAvailableLimit(
          account.getAvailableLimit() - operation.getTransaction().getAmount());
      operation.setTransaction(null);
      operation.setAccount(account);
    }

    return true;
  }
}
