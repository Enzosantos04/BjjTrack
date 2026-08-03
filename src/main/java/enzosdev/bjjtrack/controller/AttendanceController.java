package enzosdev.bjjtrack.controller;

import enzosdev.bjjtrack.config.JwtUtils;
import enzosdev.bjjtrack.dto.request.AttendanceRequest;
import enzosdev.bjjtrack.dto.response.AttendanceResponse;
import enzosdev.bjjtrack.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import enzosdev.bjjtrack.config.annotations.CanReadAttendance;
import enzosdev.bjjtrack.config.annotations.CanWriteAttendance;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final JwtUtils jwtUtils;

    public AttendanceController(AttendanceService attendanceService, JwtUtils jwtUtils) {
        this.attendanceService = attendanceService;
        this.jwtUtils = jwtUtils;
    }

    @CanWriteAttendance
    @PostMapping
    public ResponseEntity<AttendanceResponse> createAttendance(@Valid @RequestBody AttendanceRequest attendanceRequest, @AuthenticationPrincipal Jwt jwt) {
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        AttendanceResponse response = attendanceService.createAttendance(attendanceRequest, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @CanReadAttendance
    @GetMapping
    public ResponseEntity<Page<AttendanceResponse>> findAllAttendances(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        Page<AttendanceResponse> response = attendanceService.findAllAttendances(academyIdLogged, isPlatformAdmin, pageable);
        return ResponseEntity.ok(response);
    }

    @CanReadAttendance
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> findAttendanceById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        AttendanceResponse response = attendanceService.findAttendanceById(id, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.ok(response);
    }

    @CanReadAttendance
    @GetMapping("/academy/{id}")
    public ResponseEntity<Page<AttendanceResponse>> findAttendancesByAcademyId(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        Page<AttendanceResponse> response = attendanceService.findAttendancesByAcademyId(id, academyIdLogged, isPlatformAdmin, pageable);
        return ResponseEntity.ok(response);
    }

    @CanReadAttendance
    @GetMapping("/student/{id}")
    public ResponseEntity<Page<AttendanceResponse>> findAttendancesByStudentId(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        Page<AttendanceResponse> response = attendanceService.findAttendancesByStudentId(id, academyIdLogged, isPlatformAdmin, pageable);
        return ResponseEntity.ok(response);
    }

    @CanWriteAttendance
    @PatchMapping("/{id}")
    public ResponseEntity<AttendanceResponse> updateAttendanceById(@PathVariable Long id, @Valid @RequestBody AttendanceRequest attendanceRequest, @AuthenticationPrincipal Jwt jwt) {
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        AttendanceResponse response = attendanceService.updateAttendanceById(id, attendanceRequest, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.ok(response);
    }

    @CanWriteAttendance
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        attendanceService.deleteAttendance(id, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.noContent().build();
    }
}
