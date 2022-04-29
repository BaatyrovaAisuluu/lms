package kg.peaksoft.peaksoftlmsbb4.db.dto.link;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LinkRequest {
    @NotBlank
    private String text;
    @NotBlank
    private String link;
    @JsonProperty("lesson_id")
    private Long lessonId;

}