package com.unotalks.s3demo.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
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

    public String putSimpleObject(String bucketName){
        String result = "";
        try {

            String stringObjKeyName = "*** String object key name ***";

            // Upload a text string as a new object.
            s3.putObject(bucketName, stringObjKeyName, "Uploaded String Object");
            result = "success";
        } catch (SdkClientException e) {
            result = e.getMessage();
        }
        return result;
    }

    public String putFileObject(String bucketName){
        String result = "";
        try {

            String fileName = "*** String object key name ***";
            String fileObjKeyName = "*** File object key name ***";

            //File fileToUpload = new File("user.json");
            File fileToUpload = ResourceUtils.getFile("classpath:user.json");


            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileToUpload.getName(), fileToUpload);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3.putObject(request);
            result = "success";
        } catch (SdkClientException | FileNotFoundException e) {
            result = e.getMessage();
        }
        return result;
    }

}
