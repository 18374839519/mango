package com.lous.mango.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义拦截器
 */
@Component
public class MyFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public String filterType() {
        return "pre";  // 定义filter类型，共有四种：pre, route, post, error
    }

    @Override
    public int filterOrder() {
        return 0;  // 定义filter的顺序，数字越小，表示顺序越高，越先执行
    }

    @Override
    public boolean shouldFilter() {
        return true;  // 是否需要执行该filter，false不执行，true执行
    }

    @Override
    public Object run() throws ZuulException {
        // filter需要执行的具体操作(检查token)
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getParameter("token");
        logger.info(token);
        if (token == null) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            try {
                requestContext.getResponse().getWriter().write("there is no request token.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return "Success";
    }
}
