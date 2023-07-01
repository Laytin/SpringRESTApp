package com.laytin.SpringRESTApp.controllers;

import com.laytin.SpringRESTApp.dto.TripOrderDTO;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.models.TripOrder;
import com.laytin.SpringRESTApp.models.TripOrderStatus;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import com.laytin.SpringRESTApp.services.TripOrderService;
import com.laytin.SpringRESTApp.utils.errors.DefaulErrorResponce;
import com.laytin.SpringRESTApp.utils.errors.DefaultErrorException;
import com.laytin.SpringRESTApp.utils.rabbitmq.RabbitMQSender;
import com.laytin.SpringRESTApp.utils.validators.TripOrderValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.laytin.SpringRESTApp.utils.errors.ErrorBuilder.buildErrorMessageForClient;

@RestController
@RequestMapping("/order")
public class TripOrderController {
    private final TripOrderService tripOrderService;
    private final ModelMapper modelMapper;
    private final TripOrderValidator validator;
    private final RabbitMQSender rqm;
    @Autowired
    public TripOrderController(TripOrderService tripOrderService, ModelMapper modelMapper, TripOrderValidator validator, RabbitMQSender rqm) {
        this.tripOrderService = tripOrderService;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.rqm = rqm;
    }
    @PostMapping()
    public ResponseEntity<HttpStatus> join(@RequestBody @Valid TripOrderDTO tripOrderDTO, BindingResult result,Authentication auth){
        TripOrder o = modelMapper.map(tripOrderDTO,TripOrder.class);
        validator.validate(o,result);
        if(result.hasErrors())
            buildErrorMessageForClient(result);
        TripOrder res = tripOrderService.join(o,(CustomerDetails)auth.getPrincipal());

        rqm.sendObject(modelMapper.map(res,TripOrderDTO.class), "order-create");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> acceptOrDecline(@PathVariable("id") int id,
                                                      @RequestParam(value = "status",  required = true) TripOrderStatus status,
                                                      BindingResult result, Authentication auth){
        TripOrder res = tripOrderService.acceptOrDecline(id,status,(CustomerDetails) auth.getPrincipal(),result);
        if(result.hasErrors() || res == null)
            buildErrorMessageForClient(result);

        rqm.sendObject(modelMapper.map(res,TripOrderDTO.class), "order-edit");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") int id, Authentication auth){
        if(!tripOrderService.delete(id,(CustomerDetails) auth.getPrincipal()))
            buildErrorMessageForClient("Error due deleting trip order.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<DefaulErrorResponce> handleException(DefaultErrorException e) {
        DefaulErrorResponce response = new DefaulErrorResponce(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
