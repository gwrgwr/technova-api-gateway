package com.technova.apigateway.middleware;

import com.technova.apigateway.middleware.annotation.CurrentUserId;
import com.technova.apigateway.utils.JwtUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserIdResolver implements HandlerMethodArgumentResolver {

    private final JwtUtils jwtUtils;

    public CurrentUserIdResolver(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUserId.class) && // Verifica se o parâmetro tem a anotação @CurrentUserId
                parameter.getParameterType().equals(String.class); // Verifica se o parâmetro tem a anotação @CurrentUserId e é do tipo String
    }

    // Métod que será chamado quando algum endpoint que utiliza a anotação @CurrentUserId for acessado
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return jwtUtils.getCurrentUser().getId();
    }
}
