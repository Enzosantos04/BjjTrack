package enzosdev.bjjtrack.controller;


import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.service.AcademyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
