package com.unotalks.s3demo.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class S3Service {

    AmazonS3 s3;

    public String createBucket(String bucketName) {
        String result = "";
        if (!s3.doesBucketExistV2(bucketName)) {
            try {
                s3.createBucket(bucketName);
                result = "success";
            } catch (Exception e) {
                result = e.getMessage();
            }
        }
        return result;
    }

    public List<String> listBuckets(){
        List<String> bucketList = new ArrayList<>();
            List<Bucket> buckets = s3.listBuckets();
            for (Bucket b : buckets) {
                bucketList.add(b.getName());
            }
            return bucketList;
    }

    public String putObject(String bucketName){
        String result = "";
        try {

            String stringObjKeyName = "*** String object key name ***";

            // Upload a text string as a new object.
            s3.putObject(bucketName, stringObjKeyName, "Uploaded String Object");
            result = "success";
            /*
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3.putObject(request);*/
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            //e.printStackTrace();
            result = e.getMessage();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            //e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }

}
