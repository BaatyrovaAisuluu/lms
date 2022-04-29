package kg.peaksoft.peaksoftlmsbb4.db.dto.lessons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LessonRequest {
    @NotBlank
    private String name;

    @JsonProperty("course_id")
    private Long courseId;
}