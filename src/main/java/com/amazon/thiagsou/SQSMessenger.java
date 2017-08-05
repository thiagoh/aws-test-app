package com.amazon.thiagsou;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSMessenger {

	public static GetQueueUrlResult getQueue(String queueName) {

		final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

		return sqs.getQueueUrl(queueName);
	}

	public static void sendMessage(String queueName, String message) {

		GetQueueUrlResult myQueueUrl = getQueue(queueName);

		final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

		// Send a message
		sqs.sendMessage(new SendMessageRequest(myQueueUrl.getQueueUrl(), message));
	}

	public static void main(String[] args) {

		final String USAGE = "\n" + "SQSMessenger - send a messsage to a SQS queue\n\n"
				+ "Usage: SQSMessenger <sqsname> <message>\n\n" + "Where:\n"
				+ "  sqsname - the name of the queue to receive the message.\n"
				+ "  message - the message to be sent.\n\n"
				+ "The queue name must be unique, or an error will result.\n";

		/**
		 * Script to send multiple messages in background
		 * var1=0; while [ $var1 -lt 10 ]; do ; ./run-app com.amazon.thiagsou.SQSMessenger com-amazon-thiagsou-queue-1 "this is my fu**** message $var1" 5>&1 > /dev/null & ; (( var1++ )); done
		 */
		
		if (args.length < 1) {
			System.out.println(USAGE);
			System.exit(1);
		}

		String queueName = args[0];
		String message = args[1];

		System.out.println("Sending a message to MyQueue.\n");
		sendMessage(queueName, message);

		System.out.println("Done!\n");
	}
}
