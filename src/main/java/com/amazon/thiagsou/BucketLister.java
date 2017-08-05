package com.amazon.thiagsou;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

public class BucketLister {

	public static void listBucket(OutputStream out) {
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

		List<Bucket> buckets = s3.listBuckets();

		for (Bucket bucket : buckets) {
			try {
				IOUtils.write(bucket.getOwner().getDisplayName() + " owns " + bucket.getName() + "\n", out,
						Charset.defaultCharset());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {

		listBucket(System.out);
	}
}
