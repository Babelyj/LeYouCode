package upload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import upload.service.UploadService;

/**
 * @author : yj
 * @date : 14:22 2020/11/26
 */

@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){

       String url = this.uploadService.uploadImage(file);
       if(StringUtils.isEmpty(url)){
           return ResponseEntity.badRequest().build();
       }

       return ResponseEntity.status(HttpStatus.CREATED).body(url);


    }
}
