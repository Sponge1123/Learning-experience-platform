package com.buka.service;

import com.buka.util.ResponseResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseResult upload(MultipartFile img);

}
