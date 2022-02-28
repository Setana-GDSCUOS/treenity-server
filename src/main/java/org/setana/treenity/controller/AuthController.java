package org.setana.treenity.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.security.message.RegisterInfo;
import org.setana.treenity.security.message.ResponseInfo;
import org.setana.treenity.security.model.CustomUser;
import org.setana.treenity.security.service.CustomUserService;
import org.setana.treenity.security.util.RequestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final FirebaseAuth firebaseAuth;
    private final CustomUserService customUserService;

    @PostMapping
    public ResponseInfo register(@RequestHeader("Authorization") String authorization,
        @RequestBody RegisterInfo registerInfo) {

        FirebaseToken decodedToken;

        try {
            String token = RequestUtil.getAuthorizationToken(authorization);
            decodedToken = firebaseAuth.verifyIdToken(token);
        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }

        CustomUser registeredUser = customUserService.register(
            decodedToken.getUid(), decodedToken.getEmail(), registerInfo.getUsername());

        return new ResponseInfo(registeredUser);
    }

    @GetMapping
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    public ResponseInfo login(@ApiIgnore Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return new ResponseInfo(customUser);
    }
}
