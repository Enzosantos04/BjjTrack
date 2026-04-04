package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.request.StudentRequest;
import enzosdev.bjjtrack.dto.response.StudentProfileUpdateResponse;
import enzosdev.bjjtrack.dto.response.StudentPromotionResponse;
import enzosdev.bjjtrack.dto.response.StudentResponse;
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
        student.setStripes(0);
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
        if (student.getAcademy() != null){
            dto.setAcademyId(student.getAcademy().getId());
        }
        return dto;

    }

    public StudentPromotionResponse toPromotionResponse(Student student) {
        StudentPromotionResponse response = new StudentPromotionResponse();
        response.setStudentId(student.getId());
        response.setStripes(student.getStripes());
        response.setLastPromotion(student.getLastPromotion());
        response.setBelt(student.getBelt());
        return response;
    }


    public StudentProfileUpdateResponse toProfileUpdateResponse(Student student) {
        StudentProfileUpdateResponse response = new StudentProfileUpdateResponse();
        response.setBirthDate(student.getBrithDate());
        return response;
    }


}
