package com.tuyrk.controller;

import com.tuyrk.pojo.WangEditorVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/5 23:35 星期二
 * Description:
 */
@Controller
@RequestMapping("/upload")
public class WangEditorController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/editor")
    @ResponseBody
    public Object editor(@RequestParam("file") List<MultipartFile> files) {
        String fileName;
        if (files.size() == 0) {
            return new WangEditorVO("1", "上传出错");
        }
        if (files.stream().mapToLong(MultipartFile::getSize).sum() > (1048576 * 50)) {
            return new WangEditorVO("1", "总文件太大，请上传小于50MB的");
        }
        List<String> imgUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            //返回的是字节长度,1M=1024k=1048576字节 也就是if(fileSize<5*1048576)
            if (file.getSize() > (1048576 * 5)) {
                return new WangEditorVO("1", "文件太大，请上传小于5MB的");
            }
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if (StringUtils.isBlank(suffix)) {
                return new WangEditorVO("1", "上传文件没有后缀，无法识别");
            }
            try {
                fileName = System.currentTimeMillis() + suffix;
                String saveFileName = ResourceUtils.getURL("classpath:").getPath() + "static/article/" + fileName;
                System.out.println(saveFileName);
                File dest = new File(saveFileName);
                System.out.println(dest.getParentFile().getPath());
                if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                file.transferTo(dest); //保存文件
            } catch (Exception e) {
                e.printStackTrace();
                return new WangEditorVO("1", "上传失败" + e.getMessage());
            }
            imgUrls.add("article/" + fileName);
        }
        return new WangEditorVO("0", imgUrls);
    }
}
