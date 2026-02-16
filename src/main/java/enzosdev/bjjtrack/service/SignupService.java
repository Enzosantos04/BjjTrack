package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.config.PasswordEncoderConfig;
import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.dto.AdminResponse;
import enzosdev.bjjtrack.dto.SignupRequest;
import enzosdev.bjjtrack.dto.SignupResponse;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.entity.User;
import enzosdev.bjjtrack.mapper.AcademyMapper;
import enzosdev.bjjtrack.mapper.SignupMapper;
import enzosdev.bjjtrack.mapper.UserMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import enzosdev.bjjtrack.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {

    private final AcademyRepository  academyRepository;
    private final UserRepository userRepository;
    private final SignupMapper signupMapper;
    private final UserMapper userMapper;
    private final AcademyMapper academyMapper;
    private final PasswordEncoder passwordEncoder;

    public SignupService(AcademyRepository academyRepository, UserRepository userRepository, SignupMapper signupMapper, UserMapper userMapper, AcademyMapper academyMapper, PasswordEncoder passwordEncoder) {
        this.academyRepository = academyRepository;
        this.userRepository = userRepository;
        this.signupMapper = signupMapper;
        this.userMapper = userMapper;
        this.academyMapper = academyMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public SignupResponse signup(SignupRequest request){
        Academy academy = academyMapper.toEntity(request.getAcademy());
        academy = academyRepository.save(academy);
        String hashedPassword = passwordEncoder.encode(request.getAdmin().getPassword());
        User admin = userMapper.toAdminEntity(request.getAdmin(), academy, hashedPassword);
        admin = userRepository.save(admin);
        AcademyResponse academyResponse = academyMapper.toResponse(academy);
        AdminResponse adminResponse = userMapper.toResponse(admin);
        return signupMapper.toResponse(academyResponse, adminResponse);
    }

}
