package com.amazon.thiagsou;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.ListQueuesResult;

public class SQSLister {

	public static void listQueues(OutputStream out) {

		final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

		ListQueuesResult queues = sqs.listQueues();

		for (String queueUrl : queues.getQueueUrls()) {
			try {
				
				IOUtils.write(queueUrl + "\n", out, Charset.defaultCharset());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		listQueues(System.out);
	}
}
