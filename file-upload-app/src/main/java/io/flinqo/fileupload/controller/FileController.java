package io.flinqo.fileupload.controller;

import com.alibaba.fastjson.JSON;
import io.flinqo.fileupload.dao.AttachDao;
import io.flinqo.fileupload.po.AttachPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zhulongkun20@163.com
 * @since 2021/6/18 11:00 PM
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 不能用绝对路径，Chrome浏览器不准用绝对路径访问本地文件
     * 要用前端项目的相对路径
     */
    private static final String FILE_DIRECTORY = "/Users/chenxii/Documents/Java/file-upload/file-upload-web/static/files";

    @Autowired
    private AttachDao attachDao;

    @RequestMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            return "文件不能为空";
        }
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String[] split = fileName.split("\\.");
            String fileType = split[split.length - 1];
            // 获取文件存放地址
            String filePath = FILE_DIRECTORY + File.separator + fileName;
            File f = new File(filePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            file.transferTo(f);

            AttachPo attachPo = new AttachPo();
            attachPo.setAttachId(UUID.randomUUID().toString());
            attachPo.setAttachName(fileName);
            attachPo.setAttachType(fileType);
            attachPo.setSavePath(filePath);
            attachPo.setSize((int) file.getSize());
            attachPo.setUploadBy("unknown");
            attachPo.setUploadTime(new Date());
            attachDao.save(attachPo);
            return attachPo;
        } catch (Exception e) {
            System.out.println("保存文件异常" + e);
            return "文件上传失败";
        }
    }

    @RequestMapping("/findAll")
    public String findAll() {
        List<AttachPo> all = attachDao.findAll();
        if (all == null || all.size() == 0) {
            return JSON.toJSONString(Collections.emptyList());
        }
        // 去除绝对路径，否则Chrome拒绝绝对路径访问本地文件
        all.forEach(e -> e.setSavePath(e.getSavePath().replace(
                "/Users/chenxii/Documents/Java/file-upload/file-upload-web/", "")));
        return JSON.toJSONString(all);
    }

    @RequestMapping("/deleteAttach")
    public String deleteAttach(@RequestParam("attachId") String attachId) {
        int result = attachDao.deleteByAttachId(attachId);
        return result > 0 ? "删除成功" : "不存在该文件";
    }
}
