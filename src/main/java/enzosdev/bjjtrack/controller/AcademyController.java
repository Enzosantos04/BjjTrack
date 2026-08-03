package enzosdev.bjjtrack.controller;


import enzosdev.bjjtrack.config.JwtUtils;
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
import enzosdev.bjjtrack.config.annotations.CanManageAcademy;
import enzosdev.bjjtrack.config.annotations.IsPlatformAdmin;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academy")
public class AcademyController {

    private final AcademyService academyService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AcademyController(AcademyService academyService, UserService userService, JwtUtils jwtUtils) {
        this.academyService = academyService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;

    }


    @IsPlatformAdmin
    @GetMapping
    public ResponseEntity<Page<AcademyResponse>> findAllAcademies(Pageable pageable){
        Page<AcademyResponse> academies = academyService.findAllAcademies(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(academies);

    }

    @IsPlatformAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAcademyById(@PathVariable Long id){
        academyService.deleteAcademyById(id);
        return ResponseEntity.noContent().build();
    }

    @CanManageAcademy
    @PatchMapping("/{id}")
    public ResponseEntity<AcademyResponse> updateAcademyById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt, @Valid @RequestBody AcademyUpdateRequest academyRequest){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        AcademyResponse academy= academyService.updateAcademyById(id, academyIdLogged, isPlatformAdmin, academyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(academy);
    }

    @CanManageAcademy
    @GetMapping("{academyId}/users")
    public ResponseEntity<Page<UserResponse>> findAllUsersByAcademyId(@PathVariable Long academyId, @AuthenticationPrincipal Jwt jwt, Pageable pageable){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        Page<UserResponse> users = userService.listUsersByAcademyId(academyId, academyIdLogged, isPlatformAdmin, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @CanManageAcademy
    @GetMapping("/{id}")
    public ResponseEntity<AcademyResponse> findAcademyById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        AcademyResponse academy = academyService.findAcademyById(id, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.status(HttpStatus.FOUND).body(academy);
    }


    @CanManageAcademy
    @GetMapping("/slug/{slug}")
    public ResponseEntity<AcademyResponse> findAcademyBySlug(@PathVariable String slug, @AuthenticationPrincipal Jwt jwt){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        AcademyResponse academy = academyService.findAcademyBySlug(slug, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.status(HttpStatus.FOUND).body(academy);
    }
}
