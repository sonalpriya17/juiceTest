package gradle.junit.selenium.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BaseResponse {
    private int httpStatusCode;
}
