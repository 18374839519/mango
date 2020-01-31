package com.lous.mango.zuul.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 实现熔断器功能
 */
@Component
public class MyFallbackProvider implements FallbackProvider {

    /**
     * 指定熔断器应用于哪些路由的服务
     * @return
     */
    @Override
    public String getRoute() {
        return "mango-consumer";  // 只针对mango-consumer服务，如果需要所有的服务都加熔断功能，则 return "*";
    }

    /**
     * 进入熔断器功能是执行的逻辑
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        System.out.println("route:" + route);
        System.out.println("exception:" + cause.getMessage());
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "Success";
            }

            @Override
            public void close() {

            }

            /**
             * 返回熔断时的反馈信息
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("Sorry, the service is unavailable now.".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }
        };
    }
}
