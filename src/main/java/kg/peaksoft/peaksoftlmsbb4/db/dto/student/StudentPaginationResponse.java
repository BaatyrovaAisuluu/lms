package kg.peaksoft.peaksoftlmsbb4.db.dto.student;

import lombok.Getter;
import lombok.Setter;

import java.util.Deque;

@Getter
@Setter
public class StudentPaginationResponse {
    private int pages;
    private int currentPage;
    private Deque<StudentResponse> students;
}
