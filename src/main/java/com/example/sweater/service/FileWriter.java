package com.example.sweater.service;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileWriter {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.access.key}")
    private String secretAccessKey;

    @Value("${bucket.name}")
    private String bucketName;

    @Value("${default.photo.of.user}")
    private String defaultPhoto;

    /* methods of saving into AWS*/
    public String writeToAmazonS3(MultipartFile file, String photo) throws S3ServiceException {
        AWSCredentials credentials = new AWSCredentials(accessKey,secretAccessKey);
        S3Service s3 = new RestS3Service(credentials);
        S3Bucket imageBucket = s3.getBucket(bucketName);
        File newName = renameFile(file);
        S3Object imageObject = new S3Object(newName.getName());
        try {
            imageObject.setDataInputStream(new ByteArrayInputStream(file.getBytes()));
            imageObject.setContentLength(file.getBytes().length);
            AccessControlList list = new AccessControlList();
            list.setOwner(imageBucket.getOwner());
            list.grantPermission(GroupGrantee.ALL_USERS,Permission.PERMISSION_READ);
            imageObject.setAcl(list);
            if (photo!=null){
                if(!(photo.equals(defaultPhoto)))
                    s3.deleteObject(imageBucket.getName(), photo);
            }
            s3.putObject(imageBucket, imageObject);

        } catch (IOException | ServiceException e) {
            e.printStackTrace();
        }finally {
            return newName.getName();
        }

    }

    private  File renameFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String[] split = filename.split("\\.");
        String expansion = split[split.length - 1];
        String newName = UUID.randomUUID().toString().concat(".").concat(expansion);
        File oldFile = new File( file.getOriginalFilename());
        File newFile = new File( newName);
        oldFile.renameTo(newFile);
        return newFile;
    }
    /*end AWS methods*/

    /*Save into file system*/
    public void saveFileOfMessage(Message message, MultipartFile file, String uploadPath) throws IOException {
        String resultFilename = saveFile(file, uploadPath);
        message.setFilename(resultFilename);
    }

    public void saveFileOfUser(User user, MultipartFile file, String uploadPath) throws IOException {
        String resultFilename = saveFile(file, uploadPath);
        user.setPhoto(resultFilename);
    }

    private  String saveFile(MultipartFile file, String uploadPath) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            return resultFilename;
        }else return null;
    }
    /*end methods of saving into file system*/
}
