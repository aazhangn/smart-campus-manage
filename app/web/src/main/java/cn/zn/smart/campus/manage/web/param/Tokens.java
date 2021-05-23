package cn.zn.smart.campus.manage.web.param;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/22 00:59
 */
public class Tokens {
    private String accessToken;
    private String refreshToken;

    public Tokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "Tokens{accessToken='" + this.accessToken + '\'' + ", refreshToken='" + this.refreshToken + '\'' + '}';
    }
}
