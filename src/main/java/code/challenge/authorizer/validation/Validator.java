package code.challenge.authorizer.validation;

import code.challenge.authorizer.model.Account;
import code.challenge.authorizer.model.Operation;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Validator {

  public boolean validate(Operation operation, Account account) {
    List<String> violations = new LinkedList<String>();

    if (Objects.nonNull(operation.getAccount())) {
      if (Objects.nonNull(account)) {
        violations.add("account-already-initialized");
      } else {
        operation.setViolations(new String[0]);
        return true;
      }
    }

    operation.setTransaction(null);
    operation.setAccount(account);
    operation.setViolations(violations.toArray(new String[0]));
    return false;
  }
}
