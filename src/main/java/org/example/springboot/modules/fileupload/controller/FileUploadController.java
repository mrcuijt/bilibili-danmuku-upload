package org.example.springboot.modules.fileupload.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.example.springboot.modules.fileupload.config.FileUploadConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping
public class FileUploadController {

    @RequestMapping(value = "upload")
    @ResponseBody
    public Map upload(MultipartFile file) {
        File path = new File(FileUploadConfiguration.UPLOAD_BASE_PATH, FileUploadConfiguration.UPLOAD_DIRECTORY);
        if (!path.exists())
            path.mkdirs();

        String uploadPath = path.getAbsolutePath();

        String fileName = file.getOriginalFilename();
        fileName += "-" + IdWorker.getIdStr() + ".json";
        File output = new File(uploadPath, fileName);
        try {
            file.transferTo(output);
            log.info("Save file to : {}", output.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success();
    }

    public Map<String, Object> success() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("code", 200);
        dataMap.put("msg", "success");
        return dataMap;
    }
}
