package com.green.car.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    //파일 업로드 하기  경로,원본파일이름, byte[] file
    public String uploadFile(String uploadPath, String originalFile, byte[] fileData) throws IOException {
        //dog1.jpg,dog2.png,dot3.gif ---> uuid 중복되지않도록 새로운 파일이름생성
        //1231fdfdf123d1fd2f1dfd
        UUID uuid = UUID.randomUUID();
        //확장자
        String extension = originalFile.substring(originalFile.lastIndexOf((".")));
        //새로운 uuid파일명 + 확장자  1231fdfdf123d1fd2f1dfd.jpg
        String saveFilename = uuid.toString()+extension;
        //경로와 파일명 더해줌  C://car/image/1231fdfdf123d1fd2f1dfd.jpg
        String fileUploadUrl = uploadPath+"/"+saveFilename;
        //FileOutputStream 바이트단위의 출력을 내보내는 클래스
        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        //파일데이터를 파일아웃풋스트림에 쓰기
        fos.write(fileData);
        fos.close();
        //업로드된파일이름
        return saveFilename;
    }
    //삭제하기
    public void deleteFile(String filePath){
        //저장된 경로를 이용하여 파일객체를 생성
        File deleteFile = new File(filePath);
        //해당파일이 존재하면 삭제
        if(deleteFile.exists()){
            deleteFile.delete();
            System.out.println("파일을 삭제했습니다.");
        }else {
            System.out.println("파일이 존재하지않습니다.");
        }
    }
}
