package common.dto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginToken {

    private Long userCode;
    private String secret;
    private String issuer;
    private LocalDateTime expiredAt;

    public static LoginToken fromBearToken(String jwt){
        jwt = jwt.replace("Bearer ", "").trim();
        LoginToken token = new LoginToken();
        token.setUserCode(JWT.decode(jwt).getClaim("userCode").asLong());
        return token;
    }

    public LoginToken(Long userCode, String secret, String issuer, LocalDateTime expiredAt) {
        setUserCode(userCode);
        this.secret = secret;
        this.issuer = issuer;
        this.expiredAt = expiredAt;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        if(userCode == null) throw new IllegalArgumentException("user code must be not null");
        this.userCode = userCode;
    }

    public String toBearerToken() {
        ZoneOffset zoneOffset = ZoneOffset.systemDefault().getRules().getOffset(expiredAt);
        return "Bearer " + JWT.create()
                .withClaim("userCode", userCode)
                .withExpiresAt(expiredAt.toInstant(zoneOffset))
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(secret));
    }
}
