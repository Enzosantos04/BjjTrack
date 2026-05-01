package enzosdev.bjjtrack.controller;


import enzosdev.bjjtrack.dto.request.LoginRequest;
import enzosdev.bjjtrack.dto.response.LoginResponse;
import enzosdev.bjjtrack.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
