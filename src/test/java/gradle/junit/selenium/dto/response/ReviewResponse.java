package gradle.junit.selenium.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewResponse {
    private String status;
    private List<ReviewData> data;

    @Getter
    @Setter
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReviewData {
        private String message;
        private String author;
        private int product;
        private int likesCount;
        private List<String> likedBy;
        private String _id;
    }
}