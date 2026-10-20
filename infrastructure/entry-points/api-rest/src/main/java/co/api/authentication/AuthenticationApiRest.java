package co.api.authentication;

import co.api.authentication.mapper.AuthenticationRequestMapper;
import co.api.authentication.mapper.AuthenticationResponseMapper;
import co.api.authentication.request.AuthenticationRequest;
import co.api.authentication.response.AuthenticationResponse;
import co.api.config.ApiPath;
import co.api.config.filter.AuthenticationUser;
import co.usecase.authentication.AuthenticationUseCase;
import co.usecase.authentication.RefreshAuthenticationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ApiPath.ROUTE_AUTH, version = ApiPath.V1)
@RequiredArgsConstructor
public class AuthenticationApiRest {

    private final AuthenticationUseCase authenticationUseCase;
    private final RefreshAuthenticationUseCase refreshAuthenticationUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        AuthenticationResponseMapper.toResponse(
                                authenticationUseCase.execute(
                                        AuthenticationRequestMapper.toAuthenticationCommand(authenticationRequest)
                                )
                        )
                );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@AuthenticationPrincipal AuthenticationUser authenticationUser) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        AuthenticationResponseMapper.toResponse(
                                refreshAuthenticationUseCase.execute(
                                        authenticationUser.userId()
                                )
                        )
                );
    }
}
