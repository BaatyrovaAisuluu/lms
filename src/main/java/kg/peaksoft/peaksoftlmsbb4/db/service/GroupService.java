package kg.peaksoft.peaksoftlmsbb4.db.service;

import kg.peaksoft.peaksoftlmsbb4.db.dto.group.AssignGroupRequest;
import kg.peaksoft.peaksoftlmsbb4.db.dto.group.GroupRequest;
import kg.peaksoft.peaksoftlmsbb4.db.dto.group.GroupResponse;
import kg.peaksoft.peaksoftlmsbb4.db.dto.group.GroupResponsePagination;
import kg.peaksoft.peaksoftlmsbb4.db.dto.student.StudentResponse;
import kg.peaksoft.peaksoftlmsbb4.db.dto.teacher.AssignTeacherRequest;
import kg.peaksoft.peaksoftlmsbb4.db.model.Group;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public interface GroupService {
    GroupResponse saveGroup(GroupRequest groupRequest);

    List<GroupResponse> findAllGroup();
    GroupResponsePagination getAllForPagination(int page,int size);

    GroupResponse findById(Long id);

    String deleteById(Long id);

    GroupResponse update(Long id, GroupRequest groupRequest);

    Group findBy(Long id);

    List<StudentResponse> getAllStudentByGroupId(Long id);

    void assignGroupToCourse(AssignGroupRequest assignGroupRequest, Long groupId);

}
