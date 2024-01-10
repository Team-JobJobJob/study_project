package team01.studyCm.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDto {
    private MultipartFile file; // MultipartFile

    private String originFileName; // 파일 원본 이름

    private String transaction; // UUID 를 활용한 랜덤한 파일 위치

    private String chatRoom; // 파일이 올라간 채팅방 ID

    private String s3DataUrl; // 파일 링크

    private String fileDir; // S3 파일 경로

}
