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

        Optional<ProductionOrder> productionOrderResult = productionOrderService.findProductionOrderById(id);

        if(productionOrderResult.isPresent()) {
            return new ResponseEntity<>(productionOrderResult.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable> findAllProductionOrders() {

        Iterable<ProductionOrder> productionOrdersResult = productionOrderService.findAllProductionOrders();

        if(productionOrdersResult.iterator().hasNext()) {
            return new ResponseEntity<>(productionOrdersResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/person/{id}")
    public ResponseEntity<ProductionOrder> findProducionOrderByPersonId(@PathVariable long id) {

        ProductionOrder productionOrderResult = productionOrderService.findProductionOrderByPersonId(id);

        if (productionOrderResult != null) {
            return new ResponseEntity<>(productionOrderResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vehicle/{id}")
    public ResponseEntity<ProductionOrder> findProducionOrderSByVehicleId(@PathVariable Long id) {

        Optional<ProductionOrder> productionOrderResult = productionOrderService.findProductionOrderByVehicleId(id);

        if(productionOrderResult.isPresent()) {
            return new ResponseEntity<>(productionOrderResult.get(), HttpStatus.OK);
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

        productionOrder.setId(productionId);
        ProductionOrder productionOrderChanged = productionOrderService.updateProductionOrder(personid, vehicleid, productionOrder);

        if(productionOrderChanged != null) {
            return  new ResponseEntity<>(productionOrderChanged, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<ProductionOrder> deleteProductionOrderById(@PathVariable long id) {

        ProductionOrder productionOrderToDelete = productionOrderService.deleteProductionOrderById(id);

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
