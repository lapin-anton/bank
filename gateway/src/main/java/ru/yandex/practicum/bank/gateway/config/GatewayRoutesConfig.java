package ru.yandex.practicum.bank.gateway.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GatewayRoutesConfig {

    private final DiscoveryClient discoveryClient;

    public GatewayRoutesConfig(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();

        List<String> services = discoveryClient.getServices();

        for (String serviceName : services) {
            if (serviceName.equalsIgnoreCase("gateway"))
                continue;
            if (serviceName.equalsIgnoreCase("config"))
                continue;
            if (serviceName.equalsIgnoreCase("exchange-generator"))
                continue;

            List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
            if (instances.isEmpty())
                continue;

            ServiceInstance instance = instances.getFirst();
            String targetUri = instance.getUri().toString();

            if (serviceName.equals("frontend-ui")) {
                List<String> uiPaths = List.of("/", "/user/**", "/login/oauth2/code/**", "/oauth2/**", "/account",
                        "/account/**", "/cash/**", "/transfer");
                for (String path : uiPaths) {
                    routes.route("route-" + serviceName + "-" + path.replaceAll("[^a-zA-Z0-9]", "-"), r -> r
                            .path(path)
                            .filters(GatewayFilterSpec::preserveHostHeader)
                            .uri(targetUri));
                }

                continue;
            }

            String prefix = "/api/" + serviceName + "/(?<segment>.*)";
            routes.route("route-" + serviceName, r -> r
                    .path("/api/" + serviceName + "/**")
                    .filters(f -> f.rewritePath(prefix, "/${segment}"))
                    .uri(targetUri));
        }

        return routes.build();
    }
}
