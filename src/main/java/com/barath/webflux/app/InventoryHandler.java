package com.barath.webflux.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;

/**
 * Created by barath on 22/12/17.
 */
@Service
public class InventoryHandler {

    private static final Logger logger= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private InventoryRepository inventoryRepository;

    public InventoryHandler(InventoryRepository inventoryRepository){
        this.inventoryRepository=inventoryRepository;
    }

    public Mono<ServerResponse> addInventory(ServerRequest request){
           return ServerResponse.ok().body(BodyInserters.fromObject(request.bodyToMono(Inventory.class).doOnNext(inventoryRepository::save).log()));
    }

    public Mono<ServerResponse> addInventories(ServerRequest request){
        return ServerResponse.ok().body(BodyInserters.fromObject(request.bodyToFlux(Inventory.class).doOnNext(inventoryRepository::save).log()));
    }

    public Mono<ServerResponse> getInventory(ServerRequest request){
        Long inventoryId=Long.parseLong(request.pathVariable("id"));
        return ServerResponse.ok().body(BodyInserters.fromObject(Mono.justOrEmpty(inventoryRepository.findById(inventoryId)).log()));
    }

    public Mono<ServerResponse> getInventories(ServerRequest request){

        Flux<Inventory> inventoryFlux=Flux.fromIterable(inventoryRepository.findAll()).log();
        return ServerResponse.ok().body(BodyInserters.fromObject(inventoryFlux));

    }
}
