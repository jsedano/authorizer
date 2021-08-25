package code.challenge.authorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Account {
  @JsonProperty("active-card")
  @NonNull
  private boolean activeCard;

  @JsonProperty("available-limit")
  @NonNull
  private int availableLimit;
}
