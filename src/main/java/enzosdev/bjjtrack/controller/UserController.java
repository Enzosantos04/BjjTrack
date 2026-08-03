package enzosdev.bjjtrack.controller;

import enzosdev.bjjtrack.config.JwtUtils;
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
import enzosdev.bjjtrack.config.annotations.CanManageUser;
import enzosdev.bjjtrack.config.annotations.CanWriteProfile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public UserController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }


    @CanManageUser
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest, @AuthenticationPrincipal Jwt jwt){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        UserResponse userResponse = userService.createUser(userRequest, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @CanManageUser
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> updateUserById(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest, @AuthenticationPrincipal Jwt jwt){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        UserUpdateResponse user = userService.UpdateUserById(id, academyIdLogged, isPlatformAdmin, userUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    @CanManageUser
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        userService.deleteUserById(id, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.noContent().build();
    }

    @CanManageUser
    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAllUser(@AuthenticationPrincipal Jwt jwt, Pageable pageable){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        Page<UserResponse> users = userService.findAllUser(academyIdLogged, isPlatformAdmin, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @CanManageUser
    @GetMapping(params = {"email", "academyId"})
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam String email, @RequestParam Long academyId, @AuthenticationPrincipal Jwt jwt){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        UserResponse userResponse = userService.findUserByEmail(email, academyId, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @CanManageUser
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        UserResponse userResponse = userService.findUserById(id, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @CanManageUser
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUserById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
       userService.deactivateUserById(id, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.noContent().build();

    }

    @CanManageUser
    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateUserById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt){
        Long academyIdLogged = jwtUtils.getAcademyIdToken(jwt);
        boolean isPlatformAdmin = jwtUtils.isPlatformAdmin(jwt);
        userService.activateUserById(id, academyIdLogged, isPlatformAdmin);
        return ResponseEntity.noContent().build();
    }


    @CanWriteProfile
    @PatchMapping("/{id}/update-email")
    public ResponseEntity<UserUpdateEmailResponse> updateEmail(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt, @Valid  @RequestBody UserUpdateEmailRequest request){
        Long userIdLogged = jwtUtils.getUserIdToken(jwt);
        UserUpdateEmailResponse response = userService.updateEmailById(id, userIdLogged, request);
        return ResponseEntity.ok(response);
    }

}
