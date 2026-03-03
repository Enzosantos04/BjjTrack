package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.*;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.entity.User;
import enzosdev.bjjtrack.exceptions.AcademyNotFoundException;
import enzosdev.bjjtrack.exceptions.EmptyFieldException;
import enzosdev.bjjtrack.exceptions.UserEmailAlreadyExistsException;
import enzosdev.bjjtrack.exceptions.UserNotFoundException;
import enzosdev.bjjtrack.mapper.UserMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import enzosdev.bjjtrack.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

     if (userRepository.existsByAcademyIdAndEmailIgnoreCase(userRequest.getAcademyId(), userRequest.getEmail())){
         throw new UserEmailAlreadyExistsException("email already exists");
     }
        Academy academy = academyRepository.findById(userRequest.getAcademyId())
                .orElseThrow(() -> new AcademyNotFoundException("Academy not found"));
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        User user = userMapper.toUserEntity(userRequest, academy, hashedPassword);
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public UserResponse UpdateUserById(Long id, UserUpdateRequest userUpdateRequest){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        if(userUpdateRequest.getName() != null){
            if (userUpdateRequest.getName().isBlank()){
                throw new EmptyFieldException("User name empty is not allowed");
            }
            user.setName(userUpdateRequest.getName());
        }

        if(userUpdateRequest.getEmail() != null){
            if(userUpdateRequest.getEmail().isBlank()){
                throw new EmptyFieldException("User email empty is not allowed");
            }
            Long academyId = user.getAcademy().getId();

            if(userRepository.existsByAcademyIdAndEmailIgnoreCaseAndIdNot(academyId, userUpdateRequest.getEmail(), user.getId())){
                throw new UserEmailAlreadyExistsException("This email is already in use.");
            }
            user.setEmail(userUpdateRequest.getEmail());
        }
        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);

    }

    public Page<UserResponse> listUsersByAcademyId(Long id, Pageable pageable){
        if(!academyRepository.existsById(id)){
            throw  new AcademyNotFoundException("Academy not found.");
        }


        return userRepository.findByAcademyId(id, pageable)
                .map(userMapper::toResponse);

    }

    public void deleteUserById(Long id){
        userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        userRepository.deleteById(id);
    }


    public Page<UserResponse> findAllUser(Pageable pageable){
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    public UserResponse findUserByEmail(String email, Long academyId){
        Optional<User> user = userRepository.findByEmailIgnoreCaseAndAcademyId(email, academyId);
        return user.map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User not Found"));
    }

    public UserResponse findUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User not Found"));
    }

    public UserResponse deactivateUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        user.setActive(false);
        User deactivatedUser = userRepository.save(user);
        return userMapper.toResponse(deactivatedUser);
    }


}
