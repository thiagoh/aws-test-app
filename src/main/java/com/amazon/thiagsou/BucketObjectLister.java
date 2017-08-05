package com.amazon.thiagsou;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class BucketObjectLister {

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

	private static void listBucket(String bucketName, OutputStream out) {

		Bucket b = getBucket(bucketName);

		if (b == null) {
			System.err.println("Bucket not found");
			return;
		}

		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		ObjectListing ol = s3.listObjects(bucketName);

		ol.getObjectSummaries().parallelStream().forEach(os -> {

			String keyName = os.getKey();
			String url = new StringBuilder("http://").append(bucketName).append(".s3.amazonaws.com/").append(keyName)
					.toString();

			// S3Object s3Object = s3.getObject(bucket_name, keyName);
			// s3Object.getObjectContent()

			try {

				IOUtils.write("* " + keyName + " --- " + url + "\n", out, Charset.defaultCharset());

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void main(String[] args) {

		final String USAGE = "\n" + "CreateBucket - create an S3 bucket\n\n" + "Usage: CreateBucket <bucketname>\n\n"
				+ "Where:\n" + "  bucketname - the name of the bucket to create.\n\n"
				+ "The bucket name must be unique, or an error will result.\n";

		if (args.length < 1) {
			System.out.println(USAGE);
			System.exit(1);
		}

		String bucket_name = args[0];

		System.out.format("\nListing S3 bucket items: %s\n", bucket_name);
		listBucket(bucket_name, System.out);
	}

}
