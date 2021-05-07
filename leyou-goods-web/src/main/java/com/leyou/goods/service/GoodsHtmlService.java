package com.leyou.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/4/28
 * Time: 18:38
 * Description: No Description
 */
@Service
public class GoodsHtmlService {

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private GoodsService goodsService;

    public void createHtml(Long spuId)  {


        //初始化运行上下文
        Context context = new Context();
        //设置数据模型
        context.setVariables(this.goodsService.loadData(spuId));

        //静态文件生成到服务器本地
        File file = new File("D:\\developerenvironment\\nginx-1.14.0\\html\\item\\" + spuId + ".html");

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            this.engine.process("item", context, printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(printWriter != null)
                printWriter.close();
        }



    }

    public void deleteHtml(Long id) {
        File file = new File("D:\\developerenvironment\\nginx-1.14.0\\html\\item\\" + id + ".html");
        file.deleteOnExit();
    }
}