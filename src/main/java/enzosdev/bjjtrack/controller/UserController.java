package enzosdev.bjjtrack.controller;

import enzosdev.bjjtrack.dto.request.UserRequest;
import enzosdev.bjjtrack.dto.request.UserUpdateEmailRequest;
import enzosdev.bjjtrack.dto.request.UserUpdateRequest;
import enzosdev.bjjtrack.dto.response.UserResponse;
import enzosdev.bjjtrack.dto.response.UserUpdateEmailResponse;
import enzosdev.bjjtrack.dto.response.UserUpdateResponse;
import enzosdev.bjjtrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_platform:admin', 'SCOPE_admin:all')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_platform:admin', 'SCOPE_admin:all')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> updateUserById(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest){
        UserUpdateResponse user = userService.UpdateUserById(id, userUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_platform:admin', 'SCOPE_admin:all')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_platform:admin', 'SCOPE_admin:all')")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAllUser(Pageable pageable){
        Page<UserResponse> users = userService.findAllUser(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_platform:admin', 'SCOPE_admin:all')")
    @GetMapping(params = {"email", "academyId"})
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam String email, @RequestParam Long academyId){
        UserResponse userResponse = userService.findUserByEmail(email, academyId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_platform:admin', 'SCOPE_admin:all')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id){
        UserResponse userResponse = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_platform:admin', 'SCOPE_admin:all')")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUserById(@PathVariable Long id){
       userService.deactivateUserById(id);
        return ResponseEntity.noContent().build();

    }

    @PreAuthorize("hasAnyAuthority('SCOPE_platform:admin', 'SCOPE_admin:all')")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateUserById(@PathVariable Long id){
        userService.activateUserById(id);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_platform:admin', 'SCOPE_admin:all', 'SCOPE_profile:write')")
    @PatchMapping("/{id}/update-email")
    public ResponseEntity<UserUpdateEmailResponse> updateEmail(@PathVariable Long id,@Valid  @RequestBody UserUpdateEmailRequest request){
        UserUpdateEmailResponse response = userService.updateEmailById(id, request);
        return ResponseEntity.ok(response);
    }

}
