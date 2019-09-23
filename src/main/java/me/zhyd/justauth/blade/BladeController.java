package me.zhyd.justauth.blade;

import com.alibaba.fastjson.JSONObject;
import com.blade.mvc.RouteContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PathParam;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

import java.io.IOException;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/9/22 2:04
 * @since 1.8
 */
@Path(value = "/oauth", restful = true)
public class BladeController {

    @GetRoute("/render/:source")
    public void render(@PathParam String source, RouteContext context) {
        System.out.println("进入render：" + source);
        AuthRequest request = JustAuthUtil.getAuthRequest(source);
        String authorizeUrl = request.authorize(AuthStateUtils.createState());
        System.out.println("授权地址：" + authorizeUrl);
        context.redirect(authorizeUrl);
    }

    /**
     * oauth平台中配置的授权回调地址，以本项目为例，在创建github授权应用时的回调地址应为：http://127.0.0.1:8443/oauth/callback/github
     */
    @GetRoute("/callback/:source")
    public Object login(@PathParam String source, AuthCallback callback) {
        System.out.println("进入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = JustAuthUtil.getAuthRequest(source);
        AuthResponse response = authRequest.login(callback);
        System.out.println(JSONObject.toJSONString(response));
        return response;
    }

    @GetRoute("/revoke/:source/:token")
    public Object revokeAuth(@PathParam String source, @PathParam String token) throws IOException {
        AuthRequest authRequest = JustAuthUtil.getAuthRequest(source);
        return authRequest.revoke(AuthToken.builder().accessToken(token).build());
    }

    @GetRoute("/refresh/:source")
    public Object refreshAuth(@PathParam String source, String token) {
        AuthRequest authRequest = JustAuthUtil.getAuthRequest(source);
        return authRequest.refresh(AuthToken.builder().refreshToken(token).build());
    }

}
