package co.api.user;

import co.api.config.ApiPath;
import co.api.user.mapper.UserRequestMapper;
import co.api.user.mapper.UserResponseMapper;
import co.api.user.request.RegisterUserRequest;
import co.api.user.response.UserResponse;
import co.usecase.user.RegisterAgentUseCase;
import co.usecase.user.RegisterCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPath.ROUTE_USER, version = ApiPath.V1)
public class UserApiRest {

    private final RegisterAgentUseCase registerAgentUseCase;
    private final RegisterCustomerUseCase registerCustomerUseCase;

    @PostMapping("/register-customer")
    public ResponseEntity<UserResponse> createCustomer(@RequestBody RegisterUserRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseMapper
                        .toResponse(registerCustomerUseCase
                                .execute(UserRequestMapper.toCreateUserCommand(request))));
    }

    @PostMapping("/register-agent")
    public ResponseEntity<UserResponse> createAgent(@RequestBody RegisterUserRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseMapper
                        .toResponse(registerAgentUseCase
                                .execute(UserRequestMapper.toCreateUserCommand(request))));
    }
}
