package code.challenge.authorizer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
  @JsonProperty("merchant")
  @NonNull
  private String merchant;

  @JsonProperty("amount")
  @NonNull
  private int amount;

  @JsonProperty("time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @NonNull
  private Date time;
}
