package by.cargocontractservice.configuration;

import by.cargocontractservice.client.LogisticBpClient;
import by.cargocontractservice.interceptor.FeignResponseInterceptor;
import by.cargocontractservice.querymapencoder.CustomBeanQueryMapEncoder;
import by.cargocontractservice.querymapencoder.CustomQueryMapParameterProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.annotation.QueryMapParameterProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients("by.cargocontractservice.client")
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean
    public ResponseInterceptor feignResponseInterceptor() {
        final var objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return new FeignResponseInterceptor(objectMapper);
    }

    // TODO: set feign.okhttp.enabled=true (see FeignAutoConfiguration)
    @Bean
    public Client feignHttpClient() {
        return new OkHttpClient();
    }

    /**
     * Разбивает объект (DTO) на мапу состоящую из имен и значений свойств. Обязательно должны быть геттеры свойств, для
     * кастомизации названия параметров можно использовать аннотацию из библиотеки Feign @Param("")
     */
    @Bean
    public QueryMapEncoder queryMapEncoder() {
        return new CustomBeanQueryMapEncoder();
    }

    /**
     * Обрабатывает DTO в запросе которые были переданы в качестве параметров и отмечены аннотацией @SpringQueryMap.
     * Использует бин QueryMapEncoder для инициализации соответствующего поля в классе MethodMetadata из библиотеки
     * Feign.
     *
     * @param queryMapEncoder бин для получения параметров и их значений из DTO.
     */
    @Bean
    public QueryMapParameterProcessor queryMapParameterProcessor(QueryMapEncoder queryMapEncoder) {
        return new CustomQueryMapParameterProcessor(queryMapEncoder);
    }

    @Bean
    public LogisticBpClient icdbAccessChannelsClient(Feign.Builder feignBuilder,
                                                     Contract contract,
                                                     Encoder encoder,
                                                     Decoder decoder,
                                                     Client client,
                                                     QueryMapEncoder queryMapEncoder) {
        return feignBuilder
                .client(client)
                .contract(contract)
                .encoder(encoder)
                .decoder(decoder)
                .queryMapEncoder(queryMapEncoder)
                .logger(new Slf4jLogger(LogisticBpClient.class))
                .target(LogisticBpClient.class, "http://localhost:8081/logistic/contract");
    }

}
