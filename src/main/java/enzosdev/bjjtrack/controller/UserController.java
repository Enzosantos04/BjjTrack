package enzosdev.bjjtrack.controller;

import enzosdev.bjjtrack.dto.request.UserRequest;
import enzosdev.bjjtrack.dto.request.UserUpdateRequest;
import enzosdev.bjjtrack.dto.response.UserResponse;
import enzosdev.bjjtrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest){
        UserResponse user = userService.UpdateUserById(id, userUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAllUser(Pageable pageable){
        Page<UserResponse> users = userService.findAllUser(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping(params = {"email", "academyId"})
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam String email, @RequestParam Long academyId){
        UserResponse userResponse = userService.findUserByEmail(email, academyId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id){
        UserResponse userResponse = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUserById(@PathVariable Long id){
       userService.deactivateUserById(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateUserById(@PathVariable Long id){
        userService.activateUserById(id);
        return ResponseEntity.noContent().build();
    }

}
