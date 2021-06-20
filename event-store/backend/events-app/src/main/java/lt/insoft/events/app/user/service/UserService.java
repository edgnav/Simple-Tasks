package lt.insoft.events.app.user.service;

import lombok.RequiredArgsConstructor;
import lt.insoft.events.app.user.entity.UserEntity;
import lt.insoft.events.app.user.repository.UserRepo;
import lt.insoft.events.model.dto.entity.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signUp(UserDto userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new Exception("User with this email already exists!");
        }
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setHash(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return user.getEmail() + " successfully registered!";
    }

    public UserEntity loadUserByUsername(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
}