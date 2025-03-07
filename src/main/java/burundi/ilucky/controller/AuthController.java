package burundi.ilucky.controller;

import burundi.ilucky.jwt.JwtTokenProvider;
import burundi.ilucky.model.User;
import burundi.ilucky.model.dto.TokenDTO;
import burundi.ilucky.payload.Request.AuthRequest;
import burundi.ilucky.payload.Request.RegisterRequest;
import burundi.ilucky.payload.Response.APIResponse;
import burundi.ilucky.payload.Response.AuthResponse;
import burundi.ilucky.payload.Response.RegisterResponse;
import burundi.ilucky.service.AuthService.AuthService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<APIResponse<TokenDTO>> auth(@Valid @RequestBody AuthRequest request) {

		TokenDTO tokenReponse = authService.login(request);
		return ResponseEntity.ok()
				.body(new APIResponse<>(HttpStatus.OK.value(), "login-success", tokenReponse));
	}
	@PostMapping(value = "/register")
	public ResponseEntity<APIResponse<RegisterResponse>> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		RegisterResponse RegisterResponse = this.authService.register(registerRequest);
		return ResponseEntity.ok(
				new APIResponse<>(HttpStatus.CREATED.value(), "register-success", RegisterResponse));
	}
	@PostMapping(value = "/logout")
	public ResponseEntity<APIResponse<Void>> logout(@RequestParam("refreshToken") String refreshToken) {
		authService.logout(refreshToken);
		return ResponseEntity.ok()
				.body(new APIResponse<>(HttpStatus.OK.value(), "logout-success", null));
	}
}
