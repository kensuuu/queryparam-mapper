# queryparam-mapper
## Description
This Java library provides a convenient way to convert query parameters received in a ServerRequest object of Spring WebFlux into Java model objects. 
It simplifies handling query parameters by automatically mapping them to model fields, supporting single values, lists, and arrays. 
This is particularly useful for web applications using Spring WebFlux where query parameters need to be deserialized into objects for further processing.

## Features
Easy conversion of query parameters to Java objects.
Supports single value and list in model classes.
Utilizes Spring WebFlux's ServerRequest for seamless integration with reactive web applications.
Customizable to handle complex scenarios and validations.

## Requirements
Java 17 or higher
Spring WebFlux

## How to Use

```java
public Mono<ServerResponse> handleRequest(ServerRequest serverRequest) {
    return ServerRequestUtils.queryParamToMono(serverRequest, YourModelClass.class)
        .flatMap(response -> ServerResponse.ok().bodyValue(response));
}
```
