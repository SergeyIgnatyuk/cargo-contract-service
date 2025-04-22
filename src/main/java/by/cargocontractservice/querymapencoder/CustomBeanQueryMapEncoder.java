package by.cargocontractservice.querymapencoder;

import feign.Param;
import feign.QueryMapEncoder;
import feign.codec.EncodeException;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CustomBeanQueryMapEncoder implements QueryMapEncoder {

  private final Map<Class<?>, ObjectParamMetadata> classToMetadata = new HashMap<>();

  @Override
  public Map<String, Object> encode(Object object) throws EncodeException {
    if (object == null) {
      return Collections.emptyMap();
    } else {
      try {
        ObjectParamMetadata metadata = this.getMetadata(object.getClass());
        Map<String, Object> propertyNameToValue = new LinkedHashMap<>();
        Iterator<PropertyDescriptor> objectPropertiesIterator = metadata.objectProperties.iterator();

        while (objectPropertiesIterator.hasNext()) {
          PropertyDescriptor pd = objectPropertiesIterator.next();
          Method method = pd.getReadMethod();
          Object value = method.invoke(object);
          if (value != null && value != object) {
            Param alias = method.getAnnotation(Param.class);
            String name = alias != null ? alias.value() : pd.getName();
            propertyNameToValue.put(name, value);
          }
        }

        return propertyNameToValue;
      } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
        throw new EncodeException("Failure encoding object into query map", e);
      }
    }
  }

  private ObjectParamMetadata getMetadata(Class<?> objectType) throws IntrospectionException {
    ObjectParamMetadata metadata = this.classToMetadata.get(
        objectType);
    if (metadata == null) {
      metadata = ObjectParamMetadata.parseObjectType(objectType);
      this.classToMetadata.put(objectType, metadata);
    }

    return metadata;
  }

  private static class ObjectParamMetadata {

    private final List<PropertyDescriptor> objectProperties;

    private ObjectParamMetadata(List<PropertyDescriptor> objectProperties) {
      this.objectProperties = Collections.unmodifiableList(objectProperties);
    }

    private static ObjectParamMetadata parseObjectType(Class<?> type)
        throws IntrospectionException {
      List<PropertyDescriptor> properties = new ArrayList<>();
      PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(type).getPropertyDescriptors();
      int propertyDescriptorsLength = propertyDescriptors.length;

      for (int i = 0; i < propertyDescriptorsLength; ++i) {
        PropertyDescriptor pd = propertyDescriptors[i];
        boolean isGetterMethod = pd.getReadMethod() != null && !"class".equals(pd.getName());
        if (isGetterMethod) {
          properties.add(pd);
        }
      }

      return new ObjectParamMetadata(properties);
    }
  }
}
