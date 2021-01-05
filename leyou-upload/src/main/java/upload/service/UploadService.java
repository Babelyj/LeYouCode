package upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author : yj
 * @date : 14:25 2020/11/26
 */

@Service
public class UploadService {
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif","image/jpeg","image/png");

    private static final Logger logger= LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private FastFileStorageClient storageClient;

    public String uploadImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        //校验文件类型
        String contentType = file.getContentType();
        if(!CONTENT_TYPES.contains(contentType)){
            logger.info("文件类型不合法:{}", originalFilename);
            return null;
        }

        try {
            //校验文件内容
            BufferedImage read = ImageIO.read(file.getInputStream());
            //read.getHeight()图像的高
            if(null == read || read.getHeight() < 0){
                logger.info("文件内容不合法:{}", originalFilename);
                return null;
            }

            // 要上传的文件
            // 上传并保存图片，参数：1-上传的文件流 2-文件的大小 3-文件的后缀 4-可以不管他
            String ext = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = this.storageClient.uploadFile(
                    file.getInputStream(), file.getSize(), ext, null);

            logger.info("文件上传成功，服务器地址为:{}",storePath.getFullPath());
            return "http://image.leyou.com/" + storePath.getFullPath();
            //保存到服务器
            //file.transferTo(new File("D:\\uploadImage\\" + originalFilename));
            //返回url，进行回显
            //return "http://image.leyou.com/" + originalFilename;
        }catch (Exception e){
            logger.info("服务器内部错误:{}",originalFilename);
            e.printStackTrace();
        }

        return null;

    }
}
