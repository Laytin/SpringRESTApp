package com.laytin.SpringRESTApp.controllers;

import com.laytin.SpringRESTApp.dto.AuthDTO;
import com.laytin.SpringRESTApp.dto.CustomerDTO;
import com.laytin.SpringRESTApp.dto.CustomerRequest;
import com.laytin.SpringRESTApp.models.Customer;
import com.laytin.SpringRESTApp.security.JWTCore;
import com.laytin.SpringRESTApp.services.CustomerService;
import com.laytin.SpringRESTApp.utils.CustomerValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerValidator customerValidator;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTCore jwtCore;
    @Autowired
    public CustomerController(CustomerService customerService, CustomerValidator customerValidator, ModelMapper modelMapper, AuthenticationManager authenticationManager, JWTCore jwtCore) {
        this.customerService = customerService;
        this.customerValidator = customerValidator;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtCore = jwtCore;
    }
    @GetMapping("/{id}")
    public CustomerRequest getUser(@PathVariable("id") int id){
        return modelMapper.map(customerService.get(id), CustomerRequest.class);
    }
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody @Valid CustomerDTO customerDTO, BindingResult result){
        Customer c = modelMapper.map(customerDTO, Customer.class);
        customerValidator.validate(c,result);
        if(result.hasErrors())
            throw new RuntimeException();
        customerService.register(c);
        String token = jwtCore.generateToken(c.getUsername());
        return Map.of("jwt-token", token);
    }
    @PatchMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> edit(@PathVariable("id") int id,@RequestBody @Valid CustomerDTO customerDTO, BindingResult result){
        Customer c = modelMapper.map(customerDTO, Customer.class);
        customerValidator.validate(c,result);
        if(result.hasErrors())
            throw new RuntimeException();

        customerService.edit(id,c);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthDTO customerDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(customerDTO.getUsername(),
                        customerDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtCore.generateToken(customerDTO.getUsername());
        return Map.of("jwt-token", token);
    }
}

