package kg.peaksoft.peaksoftlmsbb4.db.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.Deque;
import java.util.List;

@Getter
@Setter
public class CoursePaginationResponse {
    private int pages;
    private int currentPage;
    private Deque<CourseResponse> courses;
}
