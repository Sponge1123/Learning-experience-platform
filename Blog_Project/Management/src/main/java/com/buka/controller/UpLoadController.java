package com.buka.controller;

import com.buka.service.UploadService;
import com.buka.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UpLoadController {
    @Autowired
    private UploadService uploadService;
    /**
     * 上传图片
     * @param
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult upLoadImg(@RequestParam("img")MultipartFile img){
        try {
            return uploadService.upload(img);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传图片失败");
        }
    }
}
