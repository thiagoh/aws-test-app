package com.amazon.thiagsou;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueResult;

public class SQSCreator {

	public static CreateQueueResult createQueue(String queueName) {

		final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

		return sqs.createQueue(queueName);
	}

	public static void main(String[] args) {

		final String USAGE = "\n" + "SQSCreator - create an SQS queue\n\n" + "Usage: SQSCreator <sqsname>\n\n"
				+ "Where:\n" + "  sqsname - the name of the queue to create.\n\n"
				+ "The queue name must be unique, or an error will result.\n";

		if (args.length < 1) {
			System.out.println(USAGE);
			System.exit(1);
		}

		String queueName = args[0];

		System.out.format("\nCreating SQS queue: %s\n", queueName);
		CreateQueueResult createQueueResult = createQueue(queueName);

		System.out.format("Queue created successfuly %s\n", createQueueResult.getQueueUrl());
		System.out.println("Done!\n");
	}
}
