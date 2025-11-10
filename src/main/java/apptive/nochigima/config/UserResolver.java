package apptive.nochigima.config;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

import apptive.nochigima.repository.UserRepository;
import apptive.nochigima.util.JwtUtil;

@Component
@RequiredArgsConstructor
public class UserResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        String authHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(AUTHORIZATION_PREFIX)) {
            throw new IllegalArgumentException("JWT 인증 헤더가 필요합니다.");
        }

        String token = authHeader.substring(AUTHORIZATION_PREFIX.length());
        Long userId = jwtUtil.getUserId(token);

        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("회원 정보가 존재하지 않습니다."));
    }
}
