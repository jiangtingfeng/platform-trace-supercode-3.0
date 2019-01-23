package com.jgw.supercodeplatform.trace.interceptor;

import com.jgw.supercodeplatform.web.WebMvcCustomizeFunUrlInterceptorPathConfigurer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 描述：
 * <p>
 * Created by corbett on 2018/12/27.
 */
@Component
public class MyCustomizeFunUrlInterceptor implements WebMvcCustomizeFunUrlInterceptorPathConfigurer {
    @Override
    public List<String> addPathPatterns() {
        String[] add = new String[]{
                "/trace/dynamic/fun/addFunData",
                "/trace/dynamic/fun/list",
                "/trace/dynamic/fun/update",
        		"/trace/dynamic/fun/delete"
                };
        return Arrays.asList(add);
    }
}
