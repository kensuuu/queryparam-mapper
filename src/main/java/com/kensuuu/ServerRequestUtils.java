package com.kensuuu;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class ServerRequestUtils {

    public static <T> Mono<T> queryParamToMono(ServerRequest serverRequest, Class<T> clazz) {
        return Mono.defer(() -> {
            try {
                var instance = clazz.getDeclaredConstructor().newInstance();
                var queryParams = serverRequest.queryParams();

                for (PropertyDescriptor propertyDescriptor : BeanUtils.getPropertyDescriptors(clazz)) {
                    var name = propertyDescriptor.getName();
                    var type = propertyDescriptor.getPropertyType();
                    if (queryParams.containsKey(name) && !CollectionUtils.isEmpty(queryParams.get(name))) {
                        if (List.class.isAssignableFrom(type)) {
                            var values = new ArrayList<>(queryParams.get(name));
                            propertyDescriptor.getWriteMethod().invoke(instance, values);
                        } else {
                            propertyDescriptor.getWriteMethod().invoke(instance, queryParams.getFirst(name));
                        }
                    }
                }
                return Mono.just(instance);
            } catch (Exception e) {
                return Mono.error(e);
            }
        });
    }
}
