package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.dto.AdminResponse;
import enzosdev.bjjtrack.dto.SignupRequest;
import enzosdev.bjjtrack.dto.SignupResponse;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.entity.User;
import enzosdev.bjjtrack.exceptions.AcademyNameAlreadyExistsException;
import enzosdev.bjjtrack.exceptions.AcademySlugAlreadyExistsException;
import enzosdev.bjjtrack.exceptions.UserEmailAlreadyExistsException;
import enzosdev.bjjtrack.mapper.AcademyMapper;
import enzosdev.bjjtrack.mapper.SignupMapper;
import enzosdev.bjjtrack.mapper.AdminMapper;
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
    private final AdminMapper adminMapper;
    private final AcademyMapper academyMapper;
    private final PasswordEncoder passwordEncoder;

    public SignupService(AcademyRepository academyRepository, UserRepository userRepository, SignupMapper signupMapper, AdminMapper adminMapper, AcademyMapper academyMapper, PasswordEncoder passwordEncoder) {
        this.academyRepository = academyRepository;
        this.userRepository = userRepository;
        this.signupMapper = signupMapper;
        this.adminMapper = adminMapper;
        this.academyMapper = academyMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public SignupResponse signup(SignupRequest request){

        if(academyRepository.existsByName(request.getAcademy().getName())){
            throw new AcademyNameAlreadyExistsException("Academy name already exists");
        }

        if(academyRepository.existsBySlug(request.getAcademy().getSlug())){
            throw new AcademySlugAlreadyExistsException("Academy slug already exists");
        }

        if(userRepository.existsByEmail(request.getAdmin().getEmail())){
            throw new UserEmailAlreadyExistsException("Admin email already exists");
        }

        Academy academy = academyMapper.toEntity(request.getAcademy());
        academy = academyRepository.save(academy);
        String hashedPassword = passwordEncoder.encode(request.getAdmin().getPassword());
        User admin = adminMapper.toAdminEntity(request.getAdmin(), academy, hashedPassword);
        admin = userRepository.save(admin);
        AcademyResponse academyResponse = academyMapper.toResponse(academy);
        AdminResponse adminResponse = adminMapper.toResponse(admin);
        return signupMapper.toResponse(academyResponse, adminResponse);
    }

}
