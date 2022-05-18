package kg.peaksoft.peaksoftlmsbb4.db.service.impl;

import kg.peaksoft.peaksoftlmsbb4.db.dto.student.*;
import kg.peaksoft.peaksoftlmsbb4.db.enums.Role;
import kg.peaksoft.peaksoftlmsbb4.db.enums.StudyFormat;
import kg.peaksoft.peaksoftlmsbb4.db.mapper.student.StudentMapper;
import kg.peaksoft.peaksoftlmsbb4.db.model.Course;
import kg.peaksoft.peaksoftlmsbb4.db.model.Group;
import kg.peaksoft.peaksoftlmsbb4.db.model.Student;
import kg.peaksoft.peaksoftlmsbb4.db.model.User;
import kg.peaksoft.peaksoftlmsbb4.db.repository.CourseRepository;
import kg.peaksoft.peaksoftlmsbb4.db.repository.GroupRepository;
import kg.peaksoft.peaksoftlmsbb4.db.repository.StudentRepository;
import kg.peaksoft.peaksoftlmsbb4.db.repository.UserRepository;
import kg.peaksoft.peaksoftlmsbb4.db.service.StudentService;
import kg.peaksoft.peaksoftlmsbb4.exceptions.BadRequestException;
import kg.peaksoft.peaksoftlmsbb4.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public StudentResponse saveStudent(StudentRequest studentRequest) {
        String email = studentRequest.getEmail();
        if (studentRepository.existsStudentByUserEmail((email))) {
            log.error("there is such a student with email :{}", email);
            throw new BadRequestException(
                    String.format("There is such a student with email = %s", email)
            );
        }
        Student student = studentMapper.convert(studentRequest);
        Student student1 = studentRepository.save(student);

        log.info("successful save  student:{}", student1);
        return studentMapper.deConvert(student1);

    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest studentRequest) {
        Student student = getById(id);
        if (!student.getStudentName().equals(studentRequest.getStudentName())) {
            student.setStudentName(studentRequest.getStudentName());
        }
        if (!student.getLastName().equals(studentRequest.getLastName())) {
            student.setLastName(studentRequest.getLastName());
        }
        if (!student.getPhoneNumber().equals(studentRequest.getPhoneNumber())) {
            student.setPhoneNumber(studentRequest.getPhoneNumber());
        }
        if (!student.getStudyFormat().equals(studentRequest.getStudyFormat())) {
            student.setStudyFormat(studentRequest.getStudyFormat());
        }
        if (!student.getUser().getEmail().equals(studentRequest.getEmail())) {
            student.getUser().setEmail(studentRequest.getEmail());
        }
        log.info("successful update student with id:{}", id);
        return studentMapper.deConvert(student);
    }

    @Override
    public StudentResponse findById(Long id) {
        log.info("successfully find student by id:{}", id);
        return studentMapper.deConvert(getById(id));
    }

    private Student getById(Long id) {
        log.info("successful get student by id:{}", id);
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException
                (String.format("student with id = %s does not exists ", id)));

    }

    @Override
    public Long deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            log.error(" student not found with id:{}", id);
            throw new BadRequestException(String.format("Student with id = %s does not exists", id));
        }
        log.info("successful delete student by id:{}", id);
        studentRepository.deleteById(id);
        return id;
    }

    @Override
    public StudentPaginationResponse getAll(int page, int size, StudyFormat studyFormat) {
        Pageable pageable = PageRequest.of(page, size);
        StudentPaginationResponse studentPaginationResponse = new StudentPaginationResponse();
        if (studyFormat.equals(StudyFormat.ALL)) {
            studentPaginationResponse.setPages((studentRepository.findAll(pageable).getTotalPages()));
            studentPaginationResponse.setCurrentPage(pageable.getPageNumber());
            studentPaginationResponse.setStudents(studentMapper.deConvert(studentRepository.findAllByStudent(pageable).getContent()));
        }else {
            studentPaginationResponse.setPages((studentRepository.findStudentByStudyFormat(studyFormat, pageable).getTotalPages()));
            studentPaginationResponse.setCurrentPage(pageable.getPageNumber());
            studentPaginationResponse.setStudents(studentMapper.deConvert(studentRepository.findStudentByStudyFormat(studyFormat, pageable).getContent()));
        }
        return studentPaginationResponse;
    }

    @Override
    public String assignStudentToCourse(AssignStudentRequest assignStudentRequest) {
        Course course = courseRepository.findById(assignStudentRequest.getCourseId())
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format("Not found course with id=%s",
                                        assignStudentRequest.getCourseId())));
        Student student = studentRepository.getById(assignStudentRequest.getStudentId());
        course.addStudent(student);
        log.info("successfully assign student to course by student id:{}", assignStudentRequest.getStudentId());
        return String.format("%s added to %s course", student.getStudentName(), course.getCourseName());
    }

    @Override
    public List<StudentResponse> importExcelFile(MultipartFile files, Long id) throws IOException {

        List<Student> students = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet wordSheet = workbook.getSheetAt(0);

        for (int index = 0; index<wordSheet.getPhysicalNumberOfRows(); index++){
            if (index>0){
                Student student = new Student();
                XSSFRow row = wordSheet.getRow(index);
                student.setStudentName(row.getCell(0).getStringCellValue());
                student.setLastName(row.getCell(1).getStringCellValue());
                student.setPhoneNumber(String.valueOf((int)row.getCell(2).getNumericCellValue()));
                student.setStudyFormat(StudyFormat.valueOf(row.getCell(3).getStringCellValue()));

                User user = new User();
                user.setEmail(row.getCell(4).getStringCellValue());
                user.setPassword(passwordEncoder.encode(row.getCell(5).getStringCellValue()));
                user.setRole(Role.STUDENT);

                String email = user.getEmail();


                student.setUser(user);
                students.add(student);
            }
        }

        for (Student student: students){
            Group groupEntity = groupRepository.getById(id);
            student.setGroup(groupEntity);
            studentRepository.save(student);
        }

        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            studentResponses.add(studentMapper.deConvert(student));
        }
        log.info("Found {} students ", studentResponses.size());
        return studentResponses;
    }

    @Override
    public List<StudentResponse> findByStudentName(String name) {
        log.info("successfully filter students by name:{}", name);
        return studentMapper.deConvert(studentRepository.findByStudentName(name));
    }


}
