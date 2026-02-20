package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.AcademyRequest;
import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.dto.UserRequest;
import enzosdev.bjjtrack.dto.UserResponse;
import enzosdev.bjjtrack.entity.User;
import enzosdev.bjjtrack.mapper.UserMapper;
import enzosdev.bjjtrack.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserRequest userRequest){
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        User user = userMapper.toUserEntity(userRequest, hashedPassword);
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
