package de.hsrm.mi.web.projekt.auth;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class AuthTokenService {
    @Value("${SECRETKEY}")
    private String signierschluessel;

    public String generateToken(String username, String authority)
            throws JOSEException {
        var jetzt = LocalDateTime.now();
        var ablauf = jetzt.plusHours(1);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(Date.from(jetzt.toInstant(ZoneOffset.UTC)))
                .expirationTime(Date.from(ablauf.toInstant(ZoneOffset.UTC)))
                .claim("authorities", new String[] { authority })
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        JWSSigner signer = new MACSigner(signierschluessel);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
}