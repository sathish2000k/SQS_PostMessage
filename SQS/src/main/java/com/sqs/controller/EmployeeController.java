package com.sqs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.sqs.modal.Employee;
import com.sqs.service.PostMessage;

@RestController
public class EmployeeController {
	
	@Autowired
	private PostMessage postMessage;
	
	@PostMapping("/add")
	public String add(@RequestBody Employee employee) {
		
		System.out.println("Posting message to queue");
		
		postMessage.sendMessageToQueue(employee);		    
		
		System.out.println("Message posted to queue");
		
		return "Added successfully";
	}
}
