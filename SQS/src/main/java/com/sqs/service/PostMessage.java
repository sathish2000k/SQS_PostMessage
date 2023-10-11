package com.sqs.service;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.sqs.modal.Employee;



@Component
public class PostMessage {
	
	private static final String ACCESS_KEY_ID = "your_access_key_id";
	private static final String SECRET_ACCESS_KEY = "your_secret_access_key";
	private static final String SESSION_TOKEN = "your_session_token";
	
	public void sendMessageToQueue(Employee employee) {
		JSONObject jsonObject = payloadPreprocessor(employee);
		
		BasicSessionCredentials credentials = new BasicSessionCredentials(ACCESS_KEY_ID, 
				SECRET_ACCESS_KEY, 
				SESSION_TOKEN
				);
		
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
							.withRegion("us-west-1")
							.withCredentials(new AWSStaticCredentialsProvider(credentials))
							.build();
		
		SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
				  .withQueueUrl("your queue url")
				  .withMessageBody(jsonObject.toString())
				  .withDelaySeconds(30)
				  .addMessageAttributesEntry("documentType",
							new MessageAttributeValue().withDataType("String").withStringValue("Any string value"));

		sqs.sendMessage(sendMessageStandardQueue);
		
	}

	public JSONObject payloadPreprocessor(Employee employee) {
		JSONObject obj = new JSONObject();
		obj.put("id", employee.getId());
		obj.put("name", employee.getName());
		obj.put("city", employee.getCity());
		return obj;
	}
}
