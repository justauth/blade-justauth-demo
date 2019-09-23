package me.zhyd.justauth.blade;

import com.blade.Blade;
import com.blade.kit.JsonKit;
import com.blade.kit.json.FastJsonSupport;
import com.blade.mvc.RouteContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;

@Path
public class BladeApplication {

    @GetRoute("/")
    public String index(RouteContext context) {
        return "index.html";
    }

    public static void main(String[] args) {
        // 修改内置的json库
        JsonKit.jsonSupprt(new FastJsonSupport());
        Blade.of().listen(8443).start(BladeApplication.class, args);
    }

}
