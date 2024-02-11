package com.kensuuu;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static com.kensuuu.ServerRequestUtils.queryParamToMono;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServerRequestUtilsTest {

    @Test
    public void shouldReturnMonoOfInstance_whenQueryParamsMatchClassProperties() {
        // given
        var serverRequest = mock(ServerRequest.class);
        var map = new LinkedMultiValueMap<String, String>();
        map.put("name", List.of("test"));
        when(serverRequest.queryParams()).thenReturn(map);

        // when
        var result = queryParamToMono(serverRequest, TestClass.class);

        // then
        StepVerifier.create(result)
                .expectNextMatches(instance -> "test".equals(instance.getName()))
                .verifyComplete();
    }

    @Test
    public void shouldReturnEmptyMono_whenQueryParamsDoNotMatchClassProperties() {
        // given
        var serverRequest = mock(ServerRequest.class);
        var map = new LinkedMultiValueMap<String, String>();
        map.put("none", List.of("test"));
        when(serverRequest.queryParams()).thenReturn(map);

        // when
        var result = queryParamToMono(serverRequest, TestClass.class);

        // then
        StepVerifier.create(result)
                .expectNextMatches(instance -> Objects.isNull(instance.getName()))
                .verifyComplete();
    }

    public static class TestClass {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}