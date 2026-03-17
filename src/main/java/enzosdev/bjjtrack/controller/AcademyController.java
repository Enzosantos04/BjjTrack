package enzosdev.bjjtrack.controller;


import enzosdev.bjjtrack.dto.response.AcademyResponse;
import enzosdev.bjjtrack.dto.request.AcademyUpdateRequest;
import enzosdev.bjjtrack.dto.response.UserResponse;
import enzosdev.bjjtrack.service.AcademyService;
import enzosdev.bjjtrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academy")
public class AcademyController {

    private final AcademyService academyService;
    private final UserService userService;

    public AcademyController(AcademyService academyService, UserService userService) {
        this.academyService = academyService;
        this.userService = userService;

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

    @GetMapping("{academyId}/users")
    public ResponseEntity<Page<UserResponse>> findAllUsersByAcademyId(@PathVariable Long academyId, Pageable pageable){
        Page<UserResponse> users = userService.listUsersByAcademyId(academyId,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AcademyResponse> findAcademyById(@PathVariable Long id){
        AcademyResponse academy = academyService.findAcademyById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(academy);
    }


    @GetMapping("/slug/{slug}")
    public ResponseEntity<AcademyResponse> findAcademyBySlug(@PathVariable String slug){
        AcademyResponse academy = academyService.findAcademyBySlug(slug);
        return ResponseEntity.status(HttpStatus.FOUND).body(academy);
    }
}
