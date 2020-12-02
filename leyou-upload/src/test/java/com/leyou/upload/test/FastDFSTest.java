package com.leyou.upload.test;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import upload.LeyouUploadApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest(classes = LeyouUploadApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class FastDFSTest {

    @Test
    public void test123(){
        System.out.println(123);
    }

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Test
    public void testUpload() throws FileNotFoundException {
        // 要上传的文件
        File file = new File("D:\\uploadImage\\dis.png");
        // 上传并保存图片，参数：1-上传的文件流 2-文件的大小 3-文件的后缀 4-可以不管他
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file), file.length(), file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")+1), null);
        // 带分组的路径
        //group1/M00/00/00/wKjHgF_HtzKAdkrZAAAEz0UQViA436.png
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        //M00/00/00/wKjHgF_HtzKAdkrZAAAEz0UQViA436.png
        System.out.println(storePath.getPath());
    }

    @Test
    public void testUploadAndCreateThumb() throws FileNotFoundException {
        File file = new File("D:\\uploadImage\\dis.png");
        // 上传并且生成缩略图
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(file), file.length(), file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")+1), null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
        // 获取缩略图路径
        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println(path);
    }
}