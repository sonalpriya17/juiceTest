package gradle.junit.selenium.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReviewRequest {
    private String message;
    private String author;

}