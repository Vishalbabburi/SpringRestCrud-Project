package com.luv2code.springdemo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.service.CustomerService;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.exceptions.CustomerNotFoundException;

@RestController
@RequestMapping("/api")
public class CustomerController {
	// Inject our CustomerService coz it is a dependency to delegate method calls to appropriate DAO methods
	@Autowired
	private CustomerService thecustomerService;
	// get request mapping for '/api/students'
	@GetMapping("/customers")
	public List<Customer> getcustomers(){
		
		return thecustomerService.getCustomers();
	}
	
	
	//get mapping for getting a specific customer based on his id
	@GetMapping("/customers/{customer_id}")
	public Customer getcustomer(@PathVariable("customer_id") int customerid) {
		Customer thecustomer=thecustomerService.getCustomer(customerid);
		if(thecustomer==null) {
			throw new CustomerNotFoundException("Customer with id : "+customerid+" not found");
		}
		
		return thecustomer;
	}
	
	//post mapping to receive customer json from frontend and giving id to customer and adding it to db table 
	//responding with Json that contains id of the Customer also
	@PostMapping("/customers")
	public Customer savecustomer(@RequestBody Customer thecustomer) {
		
		thecustomer.setId(0); //here 0 is not id, we are telling saveorupdate() DAO method to insert the customer object coz it inserts if id is empty(so give 0 or null)
		thecustomerService.saveCustomer(thecustomer);
		return thecustomer;
	}
	
	//put mapping to update details of a existing customer using id from Json object received from frontend
	@PutMapping("/customers")
	public Customer updatecustomer(@RequestBody Customer thecustomer) {
		
		thecustomerService.saveCustomer(thecustomer); //as received JSON object already has id,the saveorupdate() DAO method will update details of existing customer who has this id
		return thecustomer;
	}
	
	//Delete a customer of specific id and return a text
	
	@DeleteMapping("/customers/{customer_id}")
	public String deletecustomer(@PathVariable("customer_id") int customerid ) {
		
		//first check if there is a customer with that id, if not there then throw our exception to be handled by @ControllerAdvice
		Customer thecustomer=thecustomerService.getCustomer(customerid);
		if(thecustomer==null) {
			throw new CustomerNotFoundException("Customer with id : "+customerid+" not found");
		}
		//if it is happy path(customer found) then go ahead and delete the customer
		thecustomerService.deleteCustomer(customerid);
		return "Deleted Customer with id: "+customerid;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
