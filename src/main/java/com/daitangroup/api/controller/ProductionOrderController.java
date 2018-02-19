package com.daitangroup.api.controller;

import com.daitangroup.api.model.ProductionOrder;
import com.daitangroup.api.services.ProductionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/productionorders")
public class ProductionOrderController {

    @Autowired
    ProductionOrderService productionOrderService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<ProductionOrder> findProductionOrderById(@PathVariable Long id) {

        Optional<ProductionOrder> productionOrder = productionOrderService.findById(id);

        if(productionOrder.isPresent()) {
            return new ResponseEntity<>(productionOrder.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Iterable> findAllProductionOrders() {

        Iterable<ProductionOrder> productionOrders = productionOrderService.findAll();

        if(productionOrders.iterator().hasNext()) {
            return new ResponseEntity<>(productionOrders, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<ProductionOrder> createProductionOrder(@RequestParam("personid") long personid, @RequestParam("vehicleid") long vehicleid, @RequestBody ProductionOrder productionOrder) {

        ProductionOrder productionOrderSaved = productionOrderService.createProductionOrder(personid, vehicleid, productionOrder);

        if(productionOrderSaved != null) {
            return new ResponseEntity<>(productionOrderSaved, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change")
    public ResponseEntity<ProductionOrder> changeProductionOrder(@RequestParam("productionid") long productionId, @RequestParam("personid") Optional<Long> personid, @RequestParam("vehicleid") Optional<Long> vehicleid, @RequestBody ProductionOrder productionOrder) {

        ProductionOrder productionOrderChanged = productionOrderService.changeProductionOrder(productionId, personid, vehicleid, productionOrder);

        if(productionOrderChanged != null) {
            return  new ResponseEntity<>(productionOrderChanged, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<ProductionOrder> deleteProductionOrder(@PathVariable long id) {

        ProductionOrder productionOrderToDelete = productionOrderService.deleteProductionOrder(id);

        if(productionOrderToDelete != null) {
            return new ResponseEntity<>(productionOrderToDelete, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity<String> deleteAllProductionOrders() {

        productionOrderService.deleteAllProductionOrders();

        return new ResponseEntity<String>("All production orders deleted",HttpStatus.OK);
    }
}
