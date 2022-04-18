package kg.peaksoft.peaksoftlmsbb4.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CourseRequest {
    @NotBlank
    @Size(min = 2, max = 20, message = "course name should be between 2 and 20 characters")
    private String courseName;
    @NotBlank
    private String image;
    @NotBlank
    private String description;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfStart;

}