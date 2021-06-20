package lt.insoft.events.app.user;

import lombok.RequiredArgsConstructor;
import lt.insoft.events.app.user.entity.UserEntity;
import lt.insoft.events.app.user.service.UserService;
import lt.insoft.events.app.utils.JwtUtil;
import lt.insoft.events.model.dto.Request.AuthenticationRequest;
import lt.insoft.events.model.dto.Response.AuthenticationResponse;
import lt.insoft.events.model.dto.entity.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;


    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password!", e);
        }

        final UserEntity user = userService.loadUserByUsername(authRequest.getEmail());
        if(user!=null){
            final String jwt = jwtTokenUtil.generateToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } else {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PostMapping("/signup")
    public String signUp(@RequestBody UserDto userDto) throws Exception {
        return userService.signUp(userDto);
    }
}
