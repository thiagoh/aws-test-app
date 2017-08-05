package com.amazon.thiagsou;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

public class BucketObjectPutter {

	public static Bucket getBucket(String bucket_name) {
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		Bucket named_bucket = null;
		List<Bucket> buckets = s3.listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(bucket_name)) {
				named_bucket = b;
			}
		}
		return named_bucket;
	}

	private static boolean putInsideBucket(String bucketName, String filepath) {

		Bucket b = getBucket(bucketName);

		if (b == null) {
			System.err.println("Bucket not found");
			return false;
		}

		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		File file = Paths.get(filepath).toFile();

		if (file.exists() == false) {
			System.err.format("File %s was not found", file.getAbsolutePath());
			return false;
		}

		try {

			s3.putObject(bucketName, file.getName(), file);
			return true;

		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		}

		return false;
	}

	public static void main(String[] args) {

		final String USAGE = "\n" + "CreateBucket - create an S3 bucket\n\n" + "Usage: CreateBucket <bucketname>\n\n"
				+ "Where:\n" + "  bucketname - the name of the bucket to create.\n\n"
				+ "The bucket name must be unique, or an error will result.\n";

		if (args.length < 1) {
			System.out.println(USAGE);
			System.exit(1);
		}

		String bucketName = args[0];
		String filepath = args[1];

		System.out.format("\nPutting object %s inside S3 bucket: %s\n", bucketName, filepath);

		if (putInsideBucket(bucketName, filepath)) {
			System.out.println("Done!\n");
		} else {
			System.out.println("Error putting file inside the bucket!\n");
		}
	}

}
