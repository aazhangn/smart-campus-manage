package cn.zn.smart.campus.manage.web.util;

import cn.zn.smart.campus.manage.web.param.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/22 00:30
 */
@Component
public class DoubleJWT {
    /**
     * access过期时间：毫秒
     */
    private final static long DEFAULT_ACCESS_EXPIRE = 24*60*60*1000;
    /**
     * refresh过期时间：秒
     */
    private final static long DEFAULT_REFRESH_EXPIRE = 3*24*60*60;
    private long accessExpire = DEFAULT_ACCESS_EXPIRE;
    private long refreshExpire = DEFAULT_REFRESH_EXPIRE;
    private Algorithm algorithm;
    private JWTVerifier accessVerifier;
    private JWTVerifier refreshVerifier;
    private JWTCreator.Builder builder;

    public DoubleJWT() {
        this.algorithm = Algorithm.HMAC256("smart_campus_manage");
        this.initBuilderAndVerifier();
    }

    public DoubleJWT(Algorithm algorithm, long accessExpire, long refreshExpire) {
        this.algorithm = algorithm;
        this.accessExpire = accessExpire;
        this.refreshExpire = refreshExpire;
        this.initBuilderAndVerifier();
    }

    public DoubleJWT(String secret, long accessExpire, long refreshExpire) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessExpire = accessExpire;
        this.refreshExpire = refreshExpire;
        this.initBuilderAndVerifier();
    }

    public String generateToken(String tokenType, long identity, String scope, long expire) {
        Date expireDate = DateUtil.getDurationDate(expire);
        return this.builder.withClaim("type", tokenType).withClaim("identity", identity).withClaim("scope", scope).withExpiresAt(expireDate).sign(this.algorithm);
    }

    public String generateToken(String tokenType, String identity, String scope, long expire) {
        Date expireDate = DateUtil.getDurationDate(expire);
        return this.builder.withClaim("type", tokenType).withClaim("identity", identity).withClaim("scope", scope).withExpiresAt(expireDate).sign(this.algorithm);
    }

    public Map<String, Claim> decodeAccessToken(String token) {
        DecodedJWT jwt = this.accessVerifier.verify(token);
        this.checkTokenExpired(jwt.getExpiresAt());
        this.checkTokenType(jwt.getClaim("type").asString(), "access");
        this.checkTokenScope(jwt.getClaim("scope").asString());
        return jwt.getClaims();
    }

    public Map<String, Claim> decodeRefreshToken(String token) {
        DecodedJWT jwt = this.refreshVerifier.verify(token);
        this.checkTokenExpired(jwt.getExpiresAt());
        this.checkTokenType(jwt.getClaim("type").asString(), "refresh");
        this.checkTokenScope(jwt.getClaim("scope").asString());
        return jwt.getClaims();
    }

    private void checkTokenScope(String scope) {
        if (scope == null || !scope.equals("lin")) {
            throw new InvalidClaimException("token scope is invalid");
        }
    }

    private void checkTokenType(String type, String accessType) {
        if (type == null || !type.equals(accessType)) {
            throw new InvalidClaimException("token type is invalid");
        }
    }

    private void checkTokenExpired(Date expiresAt) {
        long now = System.currentTimeMillis();
        if (expiresAt.getTime() < now) {
            throw new TokenExpiredException("token is expired");
        }
    }

    public String generateAccessToken(long identity) {
        return this.generateToken("access", identity, "lin", this.accessExpire);
    }

    public String generateAccessToken(String identity) {
        return this.generateToken("access", identity, "lin", this.accessExpire);
    }

    public String generateRefreshToken(long identity) {
        return this.generateToken("refresh", identity, "lin", this.refreshExpire);
    }

    public String generateRefreshToken(String identity) {
        return this.generateToken("refresh", identity, "lin", this.refreshExpire);
    }

    public Tokens generateTokens(long identity) {
        String access = this.generateToken("access", identity, "lin", this.accessExpire);
        String refresh = this.generateToken("refresh", identity, "lin", this.refreshExpire);
        return new Tokens(access, refresh);
    }

    public Tokens generateTokens(String identity) {
        String access = this.generateToken("access", identity, "lin", this.accessExpire);
        String refresh = this.generateToken("refresh", identity, "lin", this.refreshExpire);
        return new Tokens(access, refresh);
    }

    public JWTVerifier getAccessVerifier() {
        return this.accessVerifier;
    }

    public JWTVerifier getRefreshVerifier() {
        return this.refreshVerifier;
    }

    public JWTCreator.Builder getBuilder() {
        return this.builder;
    }

    public Algorithm getAlgorithm() {
        return this.algorithm;
    }

    public Long getAccessExpire() {
        return this.accessExpire;
    }

    public Long getRefreshExpire() {
        return this.refreshExpire;
    }

    private void initBuilderAndVerifier() {
        this.accessVerifier = JWT.require(this.algorithm).acceptExpiresAt(this.accessExpire).build();
        this.refreshVerifier = JWT.require(this.algorithm).acceptExpiresAt(this.refreshExpire).build();
        this.builder = JWT.create();
    }
}