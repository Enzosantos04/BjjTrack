package enzosdev.bjjtrack.controller;

import enzosdev.bjjtrack.dto.request.AttendanceRequest;
import enzosdev.bjjtrack.dto.response.AttendanceResponse;
import enzosdev.bjjtrack.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<AttendanceResponse> createAttendance(@Valid @RequestBody AttendanceRequest attendanceRequest) {
        AttendanceResponse response = attendanceService.createAttendance(attendanceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/academy/{id}")
    public ResponseEntity<Page<AttendanceResponse>> findAttendancesByAcademyId(@PathVariable Long id, Pageable pageable) {
        Page<AttendanceResponse> response = attendanceService.findAttendancesByAcademyId(id, pageable);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AttendanceResponse> updateAttendanceById(@PathVariable Long id, @Valid @RequestBody AttendanceRequest attendanceRequest) {
        AttendanceResponse response = attendanceService.updateAttendanceById(id, attendanceRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
