package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.AcademyRequest;
import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.dto.UserRequest;
import enzosdev.bjjtrack.dto.UserResponse;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.entity.User;
import enzosdev.bjjtrack.exceptions.AcademyNotFoundException;
import enzosdev.bjjtrack.mapper.UserMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import enzosdev.bjjtrack.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AcademyRepository academyRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AcademyRepository academyRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.academyRepository = academyRepository;
    }

    public UserResponse createUser(UserRequest userRequest){
        Academy academy = academyRepository.findById(userRequest.getAcademyId())
                .orElseThrow(() -> new AcademyNotFoundException("Academy not found"));
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        User user = userMapper.toUserEntity(userRequest, academy, hashedPassword);
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
