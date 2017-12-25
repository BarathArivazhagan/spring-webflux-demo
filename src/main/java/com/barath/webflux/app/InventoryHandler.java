package com.barath.webflux.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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

        Mono<Inventory> inventoryMono=request.bodyToMono(Inventory.class);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(inventoryMono.doOnNext(inventoryRepository::save).doOnError( throwable -> {
            logger.error("error in creating inventory {}",throwable.getMessage());
        }).log(),Inventory.class);

    }

    public Mono<ServerResponse> addInventories(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(request.bodyToFlux(Inventory.class).doOnNext(inventoryRepository::save).log(),Inventory.class);
    }

    public Mono<ServerResponse> getInventory(ServerRequest request){
        Long inventoryId=Long.parseLong(request.pathVariable("id"));
        return ServerResponse.ok().body(Mono.justOrEmpty(inventoryRepository.findById(inventoryId)).log(),Inventory.class);
    }

    public Mono<ServerResponse> getInventories(ServerRequest request){

        Flux<Inventory> inventoryFlux=Flux.fromIterable(inventoryRepository.findAll()).log();
        return ServerResponse.ok().body(inventoryFlux,Inventory.class).onErrorReturn(ServerResponse.noContent().build().block());

    }

    public Mono<ServerResponse> updateInventory(ServerRequest request){


        Mono<Inventory> inventoryMono=request.bodyToMono(Inventory.class);
        Mono<Inventory> updatedInventoryMono=inventoryMono.doOnNext( inventory -> {

             inventoryRepository.findById(inventory.getInventoryId());
        }).doOnNext(inventoryRepository::save).log()
                .doOnError( throwable -> {
                    logger.error( "Error in updating the inventory {} ",throwable.getMessage());
                });

        return ServerResponse.ok().body(updatedInventoryMono,Inventory.class).onErrorReturn(ServerResponse.noContent().build().block());
    }
}
