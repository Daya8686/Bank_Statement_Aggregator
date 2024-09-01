//package com.BankStatementAggregator.services;
//
//import java.io.File;
//import java.nio.file.Paths;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//@Service
//public class AWSService {
//	
//	  private final S3Client s3Client;
//	    private final String bucketName;
//
//	    public AWSService(@Value("${aws.accessKeyId}") String accessKeyId,
//	                      @Value("${aws.secretAccessKey}") String secretAccessKey,
//	                      @Value("${aws.s3.bucket}") String bucketName) {
//	        this.bucketName = bucketName;
//	        this.s3Client = S3Client.builder()
//	                .region(Region.US_EAST_1)
//	                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
//	                .build();
//	    }
//
//	    public String uploadFile(String filePath, String fileName) {
//	        File file = new File(filePath);
//	        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//	                .bucket(bucketName)
//	                .key(fileName)
//	                .build();
//
//	        s3Client.putObject(putObjectRequest, Paths.get(filePath));
//	        return s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(fileName)).toExternalForm();
//	    }
//
//}
