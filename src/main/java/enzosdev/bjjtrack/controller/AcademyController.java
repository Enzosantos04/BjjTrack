package enzosdev.bjjtrack.controller;


import enzosdev.bjjtrack.dto.AcademyRequest;
import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.dto.AcademyUpdateRequest;
import enzosdev.bjjtrack.dto.UserResponse;
import enzosdev.bjjtrack.service.AcademyService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academy")
public class AcademyController {

    private final AcademyService academyService;

    public AcademyController(AcademyService academyService) {
        this.academyService = academyService;
    }


    @GetMapping
    public ResponseEntity<Page<AcademyResponse>> findAllAcademies(Pageable pageable){
        Page<AcademyResponse> academies = academyService.findAllAcademies(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(academies);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAcademyById(@PathVariable Long id){
        academyService.deleteAcademyById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AcademyResponse> updateAcademyById(@PathVariable Long id,@Valid @RequestBody AcademyUpdateRequest academyRequest){
        AcademyResponse academy= academyService.updateAcademyById(id, academyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(academy);
    }


    @GetMapping("/{id}/users")
    public ResponseEntity<Page<UserResponse>> findAllUsersByAcademyId(@PathVariable Long id, Pageable pageable){
        Page<UserResponse> users = academyService.listUsersByAcademyId(id,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
