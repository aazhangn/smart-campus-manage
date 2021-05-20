package cn.zn.smart.campus.manage.web.util;

import org.apache.commons.httpclient.HttpStatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 20:43
 */
public class HttpUtil {
    public static String doGet(String urlPath, HashMap<String, Object> params)
            throws Exception {
        StringBuilder sb = new StringBuilder(urlPath);
        // 说明有参数
        if (params != null && !params.isEmpty()) {
            sb.append("?");

            Set<Entry<String, Object>> set = params.entrySet();
            // 遍历map里面的参数
            for (Entry<String, Object> entry : set) {
                String key = entry.getKey();
                String value = "";
                if (null != entry.getValue()) {
                    value = entry.getValue().toString();
                    // 转码
                    value = URLEncoder.encode(value, "UTF-8");
                }
                sb.append(key).append("=").append(value).append("&");
            }
            // 删除最后一个&
            sb.deleteCharAt(sb.length() - 1);
        }
        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 5s超时
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");

        // HttpStatus.SC_OK == 200
        if (conn.getResponseCode() == HttpStatus.SC_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuilder sbs = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sbs.append(line);
            }
            return sbs.toString();
        }
        return null;
    }
}
