package com.toilamdev.stepbystep.service;

import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {
    String handleUploadFile(MultipartFile file, String mediaType, Integer id);
}
