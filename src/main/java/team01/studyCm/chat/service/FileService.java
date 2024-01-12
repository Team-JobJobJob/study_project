package team01.studyCm.chat.service;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team01.studyCm.chat.dto.FileUploadDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

//    public File convertMultipartFileToFile(MultipartFile mfile, String tmpPath) throws IOException {
//        File file = new File(tmpPath);
//
//        if (file.createNewFile()) {
//            try (FileOutputStream fos = new FileOutputStream(file)) {
//                fos.write(mfile.getBytes());
//            }
//            return file;
//        }
//        throw new IOException();
//    }
//
//    public FileUploadDto uploadFile(MultipartFile file, String transaction, String roomId) {
//        try{
//
//            String filename = file.getOriginalFilename(); // 파일원본 이름
//            String key = roomId+"/"+transaction+"/"+filename; // S3 파일 경로
//
//            // 매개변수로 넘어온 multipartFile 을 File 객체로 변환 시켜서 저장하기 위한 메서드
//            File convertedFile = convertMultipartFileToFile(file, transaction + filename);
//
//            // 아마존 S3 에 파일 업로드를 위해 사용하는 TransferManagerBuilder
////            TransferManager transferManager = TransferManagerBuilder
////                    .standard()
////                    .withS3Client(amazonS3)
////                    .build();
////
////             bucket 에 key 와 converedFile 을 이용해서 파일 업로드
////            Upload upload = transferManager.upload(bucket, key, convertedFile);
////            upload.waitForUploadResult();
////
////             변환된 File 객체 삭제
////            removeFile(convertedFile);
//
//            // uploadDTO 객체 빌드
//            FileUploadDto uploadReq = FileUploadDto.builder()
//                    .transaction(transaction)
//                    .chatRoom(roomId)
//                    .originFileName(filename)
//                    .fileDir(key)
//                    .s3DataUrl(baseUrl+"/"+key)
//                    .build();
//
//            // uploadDTO 객체 리턴
//            return uploadReq;
//
//        } catch (Exception e) {
//            log.error("fileUploadException {}", e.getMessage());
//            return null;
//        }
//    }
//
//    // path 아래있는 모든 파일을 삭제한다.
//    // 이때 path 는 roomId 가 된다 => S3 에 roomId/변경된 파일명(uuid)/원본 파일명 으로 되어있기 때문에
//    // roomId 를 적어주면 기준이 되는 chatId 아래의 모든 파일이 삭제된다.
//    public void deleteFileDir(String path) {
//        for (S3ObjectSummary summary : amazonS3.listObjects(bucket, path).getObjectSummaries()) {
//            amazonS3.deleteObject(bucket, summary.getKey());
//        }
//    }
//
//    // byte 배열 타입을 return 한다.
//    public ResponseEntity<byte[]> getObject(String fileDir, String fileName) throws IOException {
//        // bucket 와 fileDir 을 사용해서 S3 에 있는 객체 - object - 를 가져온다.
//        S3Object object = amazonS3.getObject(new GetObjectRequest(bucket, fileDir));
//
//        // object 를 S3ObjectInputStream 형태로 변환한다.
//        S3ObjectInputStream objectInputStream = object.getObjectContent();
//
//        // 이후 다시 byte 배열 형태로 변환한다.
//        // 아마도 파일 전송을 위해서는 다시 byte[] 즉, binary 로 변환해서 전달해야햐기 때문
//        byte[] bytes = IOUtils.toByteArray(objectInputStream);
//
//        // 여기는 httpHeader 에 파일 다운로드 요청을 하기 위한내용
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
//        // 지정된 fileName 으로 파일이 다운로드 된다.
//        httpHeaders.setContentDispositionFormData("attachment", fileName);
//
//        log.info("HttpHeader : [{}]", httpHeaders);
//
//        // 최종적으로 ResponseEntity 객체를 리턴하는데
//        // --> ResponseEntity 란?
//        // ResponseEntity 는 사용자의 httpRequest 에 대한 응답 테이터를 포함하는 클래스이다.
//        // 단순히 body 에 데이터를 포함하는 것이 아니라, header 와 httpStatus 까지 넣어 줄 수 있다.
//        // 이를 통해서 header 에 따라서 다른 동작을 가능하게 할 수 있다 => 파일 다운로드!!
//
//        // 나는 object가 변환된 byte 데이터, httpHeader 와 HttpStatus 가 포함된다.
//        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
//    }
}
