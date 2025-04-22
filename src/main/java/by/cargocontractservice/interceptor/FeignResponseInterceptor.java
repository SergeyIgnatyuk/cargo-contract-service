package by.cargocontractservice.interceptor;

import by.cargocontractservice.dto.BaseResponse;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import feign.InvocationContext;
import feign.Response;
import feign.ResponseInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FeignResponseInterceptor implements ResponseInterceptor {

  private final ObjectMapper mapper;

  @Override
  public Object intercept(InvocationContext invocationContext, Chain chain) throws Exception {
    try (Response response = invocationContext.response()) {
      if (response.status() < 200 || response.status() >= 300) {
        return invocationContext.proceed();
      }
      String body = StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);
      JavaType type = getType(invocationContext.returnType());
      try {
        BaseResponse<?> baseResponse = mapper.readValue(body, BaseResponse.class);
        if (baseResponse.getPayload() == null) {
          return List.class.isAssignableFrom(TypeFactory.rawClass(type)) ? Collections.emptyList() : null;
        }
        return mapper.convertValue(baseResponse.getPayload(), type);
      } catch (JsonMappingException e) {
        log.warn("No payload found, using body instead: {}", body, e);
        return mapper.readValue(body, type);
      }
    }
  }

  private JavaType getType(Type type) {
    Class<?> rawType = TypeFactory.rawClass(type);
    if (type instanceof ParameterizedType) {
      Class<?> genericType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
      return mapper.getTypeFactory().constructParametricType(rawType, genericType);
    }
    return mapper.getTypeFactory().constructType(rawType);
  }
}
