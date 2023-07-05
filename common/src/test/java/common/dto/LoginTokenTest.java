package common.dto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginTokenTest {

    @DisplayName("jwt 토큰을 이용한 객체는 토큰의 정보를 동일하게 가지고 있다.")
    @Test
    public void fromBearToken() {
        //given
        String issuer = "issuer";
        String secret = "test secret";
        Long userCode = 123l;
        LocalDateTime expiredAt = LocalDateTime.now().plusDays(1);
        ZoneOffset zoneOffset = ZoneOffset.systemDefault().getRules().getOffset(expiredAt);

        String token = JWT.create()
                .withClaim("userCode", userCode)
                .withExpiresAt(expiredAt.toInstant(zoneOffset))
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(secret));
        //when
        LoginToken result = LoginToken.fromBearToken("Bearer " + token);
        //then
        assertThat(result.getUserCode()).isEqualTo(userCode);
    }

    @DisplayName("jwt 토큰을 생성할 수 있다.")
    @Test
    public void toBearerToken() {
        //given
        String issuer = "issuer";
        String secret = "test secret";
        Long userCode = 123l;
        LocalDateTime expiredAt = LocalDateTime.now().plusDays(1);
        ZoneOffset zoneOffset = ZoneOffset.systemDefault().getRules().getOffset(expiredAt);

        String token = JWT.create()
                .withClaim("userCode", userCode)
                .withExpiresAt(expiredAt.toInstant(zoneOffset))
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(secret));

        LoginToken tokenObject = new LoginToken(userCode, secret, issuer, expiredAt);
        //when
        String result = tokenObject.toBearerToken();
        //then
        assertThat(result).isEqualTo("Bearer " + token);
    }
}