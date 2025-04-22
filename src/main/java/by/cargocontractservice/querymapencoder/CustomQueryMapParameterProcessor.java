package by.cargocontractservice.querymapencoder;

import feign.MethodMetadata;
import feign.QueryMapEncoder;
import org.springframework.cloud.openfeign.annotation.QueryMapParameterProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CustomQueryMapParameterProcessor extends QueryMapParameterProcessor {

  private final QueryMapEncoder queryMapEncoder;

  public CustomQueryMapParameterProcessor(QueryMapEncoder queryMapEncoder) {
    this.queryMapEncoder = queryMapEncoder;
  }

  @Override
  public boolean processArgument(AnnotatedParameterContext context, Annotation annotation, Method method) {
    int paramIndex = context.getParameterIndex();
    MethodMetadata metadata = context.getMethodMetadata();
    metadata.queryMapIndex(paramIndex);
    metadata.queryMapEncoder(queryMapEncoder);
    return true;
  }
}
