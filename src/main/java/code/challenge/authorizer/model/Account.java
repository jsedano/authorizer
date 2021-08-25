package code.challenge.authorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
  @JsonProperty("active-card")
  @NonNull
  private boolean activeCard;

  @JsonProperty("available-limit")
  @NonNull
  private int availableLimit;
}
