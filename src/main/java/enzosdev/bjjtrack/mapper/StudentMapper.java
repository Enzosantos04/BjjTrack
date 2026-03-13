package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.StudentRequest;
import enzosdev.bjjtrack.dto.StudentResponse;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.entity.Student;
import enzosdev.bjjtrack.entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentRequest studentRequest, Academy academy, User user){
        Student student = new Student();
        student.setAcademy(academy);
        student.setUser(user);
        student.setBelt(studentRequest.getBelt());
        student.setBrithDate(studentRequest.getBirthDate());
        student.setLastPromotion(studentRequest.getLastPromotion());
        return student;


    }


    public StudentResponse toResponse(Student student){
        if(student == null){
            return null;
        }

        StudentResponse dto = new StudentResponse();
        dto.setId(student.getId());
        dto.setName(student.getUser().getName());
        dto.setEmail(student.getUser().getEmail());
        dto.setBirthDate(student.getBrithDate());
        dto.setBelt(student.getBelt());
        dto.setLastPromotion(student.getLastPromotion());
        if (student.getAcademy() != null){
            dto.setAcademyId(student.getAcademy().getId());
        }

        if (student.getUser() != null){
            dto.setUserId(student.getUser().getId());
        }

        return dto;

    }
}
