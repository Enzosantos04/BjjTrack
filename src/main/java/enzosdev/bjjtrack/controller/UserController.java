package enzosdev.bjjtrack.controller;

import enzosdev.bjjtrack.dto.*;
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

    @GetMapping("academy/{id}/users")
    public ResponseEntity<Page<UserResponse>> findAllUsersByAcademyId(@PathVariable Long id, Pageable pageable){
        Page<UserResponse> users = userService.listUsersByAcademyId(id,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
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

    @GetMapping("/users")
    public ResponseEntity<UserResponse> findUserByEmail(@RequestParam String email){
        UserResponse userResponse = userService.findUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id){
        UserResponse userResponse = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

}
