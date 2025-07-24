package de.hsrm.mi.web.projekt.auth;

import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthTokenService authTokenService;

    public record LoginRecord(String username, String losung) {
    }

    @PostMapping("/api/token")
    public String postAuth(@RequestBody LoginRecord entity) throws AccessDeniedException {
        String jwToken, au;

        try {
            var tok = new UsernamePasswordAuthenticationToken(entity.username, entity.losung);
            var authentication = authenticationManager.authenticate(tok);

            if (!authentication.isAuthenticated()) {
                throw new AccessDeniedException("Logindaten falsch");
            }

            var authorities = authentication.getAuthorities().toArray();
            au = authorities[0].toString();

            jwToken = authTokenService.generateToken(entity.username(), au);
        } catch (JOSEException | AuthenticationException exc) {
            throw new AccessDeniedException(exc.getMessage());
        }
        return jwToken;
    }

}
