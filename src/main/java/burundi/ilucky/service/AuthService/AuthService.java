package burundi.ilucky.service.AuthService;

import burundi.ilucky.model.dto.TokenDTO;
import burundi.ilucky.payload.Request.AuthRequest;
import burundi.ilucky.payload.Request.RegisterRequest;
import burundi.ilucky.payload.Response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    TokenDTO login(AuthRequest request);
    Void logout(String refreshToken);
}
