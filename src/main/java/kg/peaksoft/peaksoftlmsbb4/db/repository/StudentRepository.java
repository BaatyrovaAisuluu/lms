package kg.peaksoft.peaksoftlmsbb4.db.repository;

import kg.peaksoft.peaksoftlmsbb4.db.enums.StudyFormat;
import kg.peaksoft.peaksoftlmsbb4.db.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {

    @Query("select s from Student s where s.studentName like %?1%")
    List<Student> findByStudentName(@Param("studentName") String studentName);

    Page<Student> findStudentByStudyFormat(StudyFormat studyFormat, Pageable pageable);

    Student findStudentByUser_Email(String email);

    @Query("select s from Student s")
    Page<Student> findAllByStudent(Pageable pageable);
}
