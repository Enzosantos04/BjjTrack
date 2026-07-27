package enzosdev.bjjtrack.controller;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @CanWriteAttendance
    @PostMapping
    public ResponseEntity<AttendanceResponse> createAttendance(@Valid @RequestBody AttendanceRequest attendanceRequest) {
        AttendanceResponse response = attendanceService.createAttendance(attendanceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @CanReadAttendance
    @GetMapping
    public ResponseEntity<Page<AttendanceResponse>> findAllAttendances(Pageable pageable) {
        Page<AttendanceResponse> response = attendanceService.findAllAttendances(pageable);
        return ResponseEntity.ok(response);
    }

    @CanReadAttendance
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> findAttendanceById(@PathVariable Long id) {
        if (id == null) {}
        AttendanceResponse response = attendanceService.findAttendanceById(id);
        return ResponseEntity.ok(response);
    }

    @CanReadAttendance
    @GetMapping("/academy/{id}")
    public ResponseEntity<Page<AttendanceResponse>> findAttendancesByAcademyId(@PathVariable Long id, Pageable pageable) {
        Page<AttendanceResponse> response = attendanceService.findAttendancesByAcademyId(id, pageable);
        return ResponseEntity.ok(response);
    }

    @CanReadAttendance
    @GetMapping("/student/{id}")
    public ResponseEntity<Page<AttendanceResponse>> findAttendancesByStudentId(@PathVariable Long id, Pageable pageable) {
        Page<AttendanceResponse> response = attendanceService.findAttendancesByStudentId(id, pageable);
        return ResponseEntity.ok(response);
    }

    @CanWriteAttendance
    @PatchMapping("/{id}")
    public ResponseEntity<AttendanceResponse> updateAttendanceById(@PathVariable Long id, @Valid @RequestBody AttendanceRequest attendanceRequest) {
        AttendanceResponse response = attendanceService.updateAttendanceById(id, attendanceRequest);
        return ResponseEntity.ok(response);
    }

    @CanWriteAttendance
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
