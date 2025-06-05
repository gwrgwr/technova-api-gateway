package com.technova.apigateway.middleware;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final CurrentUserIdResolver userResolver;
    private final CurrentVendorResolver vendorResolver;

    public WebConfig(CurrentUserIdResolver userResolver, CurrentVendorResolver vendorResolver) {
        this.userResolver = userResolver;
        this.vendorResolver = vendorResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userResolver);
        resolvers.add(vendorResolver);
    }
}
