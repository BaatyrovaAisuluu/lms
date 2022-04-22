package kg.peaksoft.peaksoftlmsbb4.service;

import kg.peaksoft.peaksoftlmsbb4.dto.course.CourseRequest;
import kg.peaksoft.peaksoftlmsbb4.dto.course.CourseResponse;
import kg.peaksoft.peaksoftlmsbb4.dto.student.StudentResponse;
import kg.peaksoft.peaksoftlmsbb4.dto.teacher.TeacherResponse;
import kg.peaksoft.peaksoftlmsbb4.model.Course;

import java.util.List;

public interface CourseService {

    CourseResponse saveCourse(CourseRequest courseRequest);

    List<CourseResponse> findAll();

    CourseResponse findById(Long id);

    CourseResponse update(Long id, CourseRequest courseRequest);

    void delete(Long id);

    List<StudentResponse> getAllStudentsByCourseId(Long id);

    List<TeacherResponse> getAllTeacherByCourseId(Long id);

    Course getById(Long id);
}
