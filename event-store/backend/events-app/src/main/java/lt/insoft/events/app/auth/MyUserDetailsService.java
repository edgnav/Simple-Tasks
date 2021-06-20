package lt.insoft.events.app.auth;

import lt.insoft.events.app.user.entity.UserEntity;
import lt.insoft.events.app.user.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo userRepository;

    public MyUserDetailsService(UserRepo userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("not found");
        }
        ArrayList<Object> list = new ArrayList<>();
        list.add(user.get().getId());
        return new User(user.get().getEmail(), user.get().getHash(),new ArrayList<>());
    }
}
