package com.barath.webflux.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;


/**
 * Created by barath on 22/12/17.
 */

@Configuration
public class RouterConfiguration {


    private InventoryHandler inventoryHandler;

    public RouterConfiguration(InventoryHandler inventoryHandler) {
        this.inventoryHandler = inventoryHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routes(){

        return RouterFunctions.route(RequestPredicates.POST("/inventory/add")
                    .and(accept(MediaType.APPLICATION_JSON_UTF8)),inventoryHandler::addInventory)
                .andRoute(RequestPredicates.POST("/inventory/all")
                        .and(accept(MediaType.APPLICATION_JSON_UTF8)),inventoryHandler::addInventories)
                .andRoute(RequestPredicates.GET("/inventory/get/{id}")
                        .and(accept(MediaType.APPLICATION_JSON_UTF8)),inventoryHandler::getInventory)
                .andRoute(RequestPredicates.GET("/inventories")
                        .and(accept(MediaType.APPLICATION_JSON_UTF8)),inventoryHandler::getInventories)
                .andRoute(RequestPredicates.PUT("/inventory/update")
                        .and(accept(MediaType.APPLICATION_JSON_UTF8)),inventoryHandler::updateInventory);

    }
}
