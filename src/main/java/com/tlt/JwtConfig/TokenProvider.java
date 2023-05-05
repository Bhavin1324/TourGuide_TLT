package com.tlt.JwtConfig;

import static com.tlt.constants.JwtConstants.REMEMBER_VALIDITY_SECONDS;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static java.util.stream.Collectors.joining;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import io.jsonwebtoken.*;
import java.util.Arrays;
import java.util.stream.Collectors;


@Named
public class TokenProvider implements Serializable {

    private static final String AUTHORITIES_KEY = "auth";
    private static String secretKey;
    private long tokenValidity;
    private long tokenValidityForRememberMe;

    @PostConstruct
    public void init() {
        this.secretKey = "NcRfUjXn2r5u8x/A?D(G-KaPdSgVkYp3s6v9y$B&E)H@MbQeThWmZq4t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u8x/A?D(G+KbPeShVmYq3t6w9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@McQfTjWnZr4";
        this.tokenValidity = TimeUnit.HOURS.toMillis(20);
        this.tokenValidityForRememberMe = TimeUnit.SECONDS.toMillis(REMEMBER_VALIDITY_SECONDS);
    }

    public String createToken(String username, Set<String> authorities, boolean remember) {
        long now = (new Date()).getTime();
        long validity = remember ? tokenValidityForRememberMe : tokenValidity;
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("localhost")
                .claim(AUTHORITIES_KEY, authorities.stream().collect(joining(",")))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(new Date(now + validity))
                .compact();

    }

    public JWTCredential getCredential(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Set<String> authorities = Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream().collect(Collectors.toSet());
        return new JWTCredential(claims.getSubject(), authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException se) {
            se.printStackTrace();
            return false;
        }
    }
}
