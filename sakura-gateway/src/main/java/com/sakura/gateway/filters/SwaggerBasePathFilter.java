package com.sakura.gateway.filters;

import com.sakura.gateway.swagger.SwaggerResourcesImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author Sakura
 * @date 2023/8/8 10:15
 */
@Slf4j
@Component
public class SwaggerBasePathFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //判断是否需要过滤
        String path = exchange.getRequest().getURI().getPath();
        if(StringUtils.endsWithIgnoreCase(path, SwaggerResourcesImpl.API_URI)) { //为swagger2视图请求接口的地址
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                        return super.writeWith(fluxBody.map(dataBuffer -> {
                            // probably should reuse buffers
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            //释放掉内存
                            DataBufferUtils.release(dataBuffer);
                            //s就是response的值，想修改、查看就随意而为了
                            String serverName = Arrays.stream(path.split("/")).filter(StringUtils::isNotBlank).findFirst().get();
                            content = new String(content, Charset.forName("UTF-8")).replaceFirst("\"basePath\" ?: ?\"[^\"]*\"",
                                    "\"basePath\":\"/" + serverName + "\"").getBytes();
                            byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
                            return bufferFactory.wrap(uppedContent);
                        }));
                    }
                    // if body is not a flux. never got there.
                    return super.writeWith(body);
                }
            };
            // replace response with decorator
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }
        return chain.filter(exchange);
    }
}
