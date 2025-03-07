package burundi.ilucky.service.AuthService.Imp;

import burundi.ilucky.Exception.BaseException;
import burundi.ilucky.Patern.ErrorCode;
import burundi.ilucky.jwt.JwtTokenProvider;
import burundi.ilucky.model.Token;
import burundi.ilucky.model.User;
import burundi.ilucky.model.dto.TokenDTO;
import burundi.ilucky.payload.Request.AuthRequest;
import burundi.ilucky.payload.Request.RegisterRequest;
import burundi.ilucky.payload.Response.RegisterResponse;
import burundi.ilucky.repository.TokenRepository;
import burundi.ilucky.repository.UserRepository;
import burundi.ilucky.service.AuthService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Override
    public TokenDTO login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassWord()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);
        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();
        tokenRepository.save(token);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if ((existByUsername(request.getUserName()))) {
            throw new BaseException(ErrorCode.USER_EXIST);
        }
        User user = new User();
        user.setUsername(request.getUserName());
        user.setPassword(request.getPassWord());
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setTotalPlay(1);
        user.setTotalVnd(10000);
        User savedUser = this.userRepository.save(user);  //save User
        return new RegisterResponse(  //Response Register
                savedUser.getUsername());
    }
    @Override
    public Void logout(String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_REFRESH_TOKEN));
        token.setRevoked(true);
        this.tokenRepository.save(token);
        return null;
    }
    public boolean existByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

}
