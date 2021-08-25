package code.challenge.authorizer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Operation {
  @JsonProperty("account")
  private Account account;

  @JsonProperty("transaction")
  private Transaction transaction;

  @JsonProperty("violations")
  private String[] violations;
}
