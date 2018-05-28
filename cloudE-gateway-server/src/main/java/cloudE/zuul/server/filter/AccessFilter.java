package cloudE.zuul.server.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * filter 前置过滤器
 */
public class AccessFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    /**
     * 具体的过滤逻辑
     *
     * @return
     */
    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            String requestURL = request.getRequestURL().toString();
            logger.info("{} AccessFilter request to {}", request.getMethod(), requestURL);
            if (true) {
                ctx.setRequest(request);
                // 对该请求进行路由
                ctx.setSendZuulResponse(true);
                ctx.setResponseStatusCode(200);
                // 设值，让下一个Filter看到上一个Filter的状态
                ctx.set("isSuccess", true);
            } else {
                // 过滤该请求，不对其进行路由
                ctx.setSendZuulResponse(false);
                // 返回错误码
                ctx.setResponseStatusCode(401);
                // 返回错误内容
                ctx.setResponseBody("{\"result\":\"request fail!\"}");
                ctx.set("isSuccess", false);
            }
        } catch (Throwable throwable) {
            logger.error("AccessFilter fail", throwable);
        }
        return null;
    }

    /**
     * 是否执行该过滤器，此处为true，说明需要过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 优先级为0，数字越大，优先级越低
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 前置过滤器
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }
}
