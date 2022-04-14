package com.member;

import com.member.config.OSSProperties;
import com.util.CrowFundingUtil;
import com.util.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author qiu
 * @create 2022-04-13 17:08
 */
@Slf4j
@SpringBootTest
public class TestOSS {

    @Autowired
    private OSSProperties ossProperties;

    @Test
    public void test(){
        String endPoint = ossProperties.getEndPoint();
        String bucketName = ossProperties.getBucketName();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();
        String bucketDomain = ossProperties.getBucketDomain();
        String originalFileName = "upload.png";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("C:\\Users\\BRUCE\\Pictures\\upload.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ResultEntity<String> resultEntity = CrowFundingUtil.uploadFileToOSS(endPoint, bucketName, accessKeyId, accessKeySecret, bucketDomain, inputStream, originalFileName);

        log.info(resultEntity.getOperationMessage());
    }
}
