package gradle.junit.selenium.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse extends BaseResponse{
    private AuthToken authentication;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AuthToken {
        private String token;
        private int bid;
        @JsonProperty("umail")
        private String email;
    }
}

