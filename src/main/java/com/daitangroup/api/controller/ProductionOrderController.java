package com.daitangroup.api.controller;

import com.daitangroup.api.model.ProductionOrder;
import com.daitangroup.api.services.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/production-orders")
public class ProductionOrderController {

    @Autowired
    GarageService garageService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<ProductionOrder> findProductionOrderById(@PathVariable Long id) {

        Optional<ProductionOrder> productionOrderResult = garageService.findProductionOrderById(id);

        if(productionOrderResult.isPresent()) {
            return new ResponseEntity<>(productionOrderResult.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable> findAllProductionOrders() {

        Iterable<ProductionOrder> productionOrdersResult = garageService.findAllProductionOrders();

        if(productionOrdersResult.iterator().hasNext()) {
            return new ResponseEntity<>(productionOrdersResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/person/{id}")
    public ResponseEntity<ProductionOrder> findProducionOrderByPersonId(@PathVariable long id) {

        ProductionOrder productionOrderResult = garageService.findProductionOrderByPersonId(id);

        if (productionOrderResult != null) {
            return new ResponseEntity<>(productionOrderResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vehicle/{id}")
    public ResponseEntity<ProductionOrder> findProducionOrderSByVehicleId(@PathVariable Long id) {

        Optional<ProductionOrder> productionOrderResult = garageService.findProductionOrderByVehicleId(id);

        if(productionOrderResult.isPresent()) {
            return new ResponseEntity<>(productionOrderResult.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<ProductionOrder> createProductionOrder(@RequestParam("personid") long personid, @RequestParam("vehicleid") long vehicleid, @RequestBody ProductionOrder productionOrder) {

        ProductionOrder productionOrderSaved = garageService.createProductionOrder(personid, vehicleid, productionOrder);

        if(productionOrderSaved != null) {
            return new ResponseEntity<>(productionOrderSaved, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ResponseEntity<ProductionOrder> updateProductionOrder(@RequestParam("productionid") long productionId, @RequestParam("personid") Optional<Long> personid, @RequestParam("vehicleid") Optional<Long> vehicleid, @RequestBody ProductionOrder productionOrder) {

        productionOrder.setId(productionId);
        ProductionOrder productionOrderChanged = garageService.updateProductionOrder(personid, vehicleid, productionOrder);

        if(productionOrderChanged != null) {
            return  new ResponseEntity<>(productionOrderChanged, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<ProductionOrder> deleteProductionOrderById(@PathVariable long id) {

        ProductionOrder productionOrderToDelete = garageService.deleteProductionOrderById(id);

        if(productionOrderToDelete != null) {
            return new ResponseEntity<>(productionOrderToDelete, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete-all")
    public ResponseEntity<String> deleteAllProductionOrders() {

        garageService.deleteAllProductionOrders();

        return new ResponseEntity<String>("All production orders deleted", HttpStatus.OK);
    }
}
