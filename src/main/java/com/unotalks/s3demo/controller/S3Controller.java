package com.unotalks.s3demo.controller;

import com.unotalks.s3demo.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "localstack/")
@AllArgsConstructor
public class S3Controller {

    S3Service s3Service;

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<String> createBucket(@RequestParam String bucketName){

        String result = s3Service.createBucket(bucketName);

        return ResponseEntity.ok(result);
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<String>> getBuckets(){

        List<String> buckets = s3Service.listBuckets();

        return ResponseEntity.ok(buckets);
    }

    @PutMapping(produces = {"application/json"})
    public ResponseEntity<String> putObject(@RequestParam String bucketName){

        String result = s3Service.putObject(bucketName);

        return ResponseEntity.ok(result);
    }

}
