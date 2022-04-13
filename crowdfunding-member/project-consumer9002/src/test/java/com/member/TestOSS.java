package com.member;

import com.util.CrowFundingUtil;
import com.util.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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
    @Test
    public void test(){
        String endPoint = "https://oss-cn-shenzhen.aliyuncs.com";
        String bucketName = "crowdfunding123";
        String accessKeyId = "LTAI5tRod2DSBcCa65KgKpDu";
        String accessKeySecret = "uwM860Qgz32BhZuCeq958BktwhC6vd";
        String bucketDomain = "https://crowdfunding123.oss-cn-shenzhen.aliyuncs.com";
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
