package com.sakura.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Sakura
 * @date 2023/7/31 15:22
 */
@Component
public class LogFilter implements GlobalFilter {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Gateway全局过滤器：");
        log.info(exchange.getRequest().getPath().value());
        return chain.filter(exchange);
    }
}
