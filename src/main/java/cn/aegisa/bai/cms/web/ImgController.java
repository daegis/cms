package cn.aegisa.bai.cms.web;

import cn.aegisa.bai.cms.config.properties.AliOSSProperties;
import cn.aegisa.bai.cms.config.properties.CommonProperties;
import cn.aegisa.bai.cms.service.ImgService;
import cn.aegisa.bai.cms.vo.ImageVo;
import cn.aegisa.bai.model.UploadImg;
import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author daegis@yeah.net
 * @serial
 * @since 2019-02-11 14:59
 */
@Controller
@Slf4j
@RequestMapping("/img")
public class ImgController {

    private String filePath = "/data/imgs/";

    @Autowired
    private OSSClient ossClient;

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private AliOSSProperties ossProperties;

    @Autowired
    private ImgService imgService;

    @RequestMapping("/list")
    public String list(Model model) {
        List<UploadImg> imgList = imgService.getAllImgs();
        List<ImageVo> imageVoList = new ArrayList<>();
        for (UploadImg img : imgList) {
            ImageVo vo = new ImageVo();
            vo.setId(img.getId());
            vo.setName(img.getImgName());
            vo.setUrl(img.getCdnUrl());
            vo.setUploadTime(img.getUploadTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            imageVoList.add(vo);
        }
        model.addAttribute("imgs", imageVoList);
        return "img/list";
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("msg", "上传文件失败，请选择文件");
        }
        String originalFilename = file.getOriginalFilename();
        String extendName = getExtendName(originalFilename);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + extendName;
        try {
            ossClient.putObject(ossProperties.getBucketName(), fileName, file.getInputStream());
            // 文件上传成功，保存图片信息
            imgService.saveUploadInfo(fileName);
            result.put("fileName", fileName);
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", e.toString());
        }
        return result;
    }


    @DeleteMapping("/p/{id}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        boolean deleteResult = imgService.deleteImg(id);
        if (!deleteResult) {
            result.put("success", false);
            result.put("msg", "文件删除失败");
        }
        return result;
    }

    private String getExtendName(String originalFilename) {
        if (StringUtils.isEmpty(originalFilename)) {
            return ".jpg";
        }
        int dotIndex = originalFilename.lastIndexOf(".");
        return originalFilename.substring(dotIndex - 1);
    }
}
