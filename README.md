# The cache works differently if the value of local-response-cache.enabled is default false or explicitly false

When the value of the `spring.cloud.gateway.filter.local-response-cache.enabled` property is `false`, the application
doesn't start as the documentation mentions. But if this property is missing, the application starts. The documentation
says, the `false` is the default value.

https://docs.spring.io/spring-cloud-gateway/reference/4.1-SNAPSHOT/appendix.html#page-title

The current documentation gets 404 - Not found status now.

https://docs.spring.io/spring-cloud-gateway/reference/current/index.html

I have two routes:

```yaml
      routes:
        - id: hello
          uri: http://localhost:8080
          predicates:
            - Path=/api/hello
          filters:
            - LocalResponseCache=30m,500MB
        - id: hello-name
          uri: http://localhost:8080
          predicates:
            - Path=/api/hello/{name}
```

When the property is missing:

* `hello` is cached
* `hello-name` is not cached

When the property is `false`:

* `IllegalArgumentException` - `Unable to find GatewayFilterFactory with name LocalResponseCache`

When the property is `true`:

* `hello` is cached
* `hello-name` is cached (default cache)

Yet another question: what is the difference between `spring.cloud.gateway.filter.local-response-cache.enabled` and
`spring.cloud.gateway.global-filter.local-response-cache.enabled` property?
