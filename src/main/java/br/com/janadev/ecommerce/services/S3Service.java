package br.com.janadev.ecommerce.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucketName;

    public void uploadFile(String localFilePath){
        try {
            File file = new File(localFilePath);
            LOG.info("Iniciado upload");
            amazonS3.putObject(new PutObjectRequest(bucketName, "teste", file));
            LOG.info("Upload concluído");
        } catch (AmazonServiceException ex){
            LOG.info("AmazonServiceException: " + ex.getErrorMessage());
            LOG.info("Status code: " + ex.getErrorCode());
        } catch (AmazonClientException ex){
            LOG.info("AmazonClientException: " + ex.getMessage());
        }
    }
}
