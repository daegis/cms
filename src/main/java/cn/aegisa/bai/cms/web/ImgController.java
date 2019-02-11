package cn.aegisa.bai.cms.web;

import cn.aegisa.bai.cms.vo.ImageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author xianyingda@guazi.com
 * @serial
 * @since 2019-02-11 14:59
 */
@Controller
@Slf4j
@RequestMapping("/img")
public class ImgController {

    private String filePath = "/data/imgs/";

    @RequestMapping("/list")
    public String list(Model model) {
        File imgDir = new File(filePath);
        File[] imgs = imgDir.listFiles();
        List<ImageVo> imageVoList = new ArrayList<>();
        if (imgs != null) {
            for (File img : imgs) {
                ImageVo vo = new ImageVo();
                String name = img.getName();
                vo.setName(name);
                vo.setUrl("/img/p/" + name);
                imageVoList.add(vo);
            }
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
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        File dest = new File(filePath + fileName + extendName);
        try {
            file.transferTo(dest);
            result.put("fileName", fileName);
        } catch (IOException e) {
            result.put("success", false);
            result.put("msg", e.toString());
        }
        return result;
    }

    @GetMapping(value = "/p/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImg(@PathVariable String name) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath + name));
        return IOUtils.toByteArray(fis);
    }

    @DeleteMapping("/p/{name}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        File file = new File(filePath + name);
        if (file.exists()) {
            boolean delete = file.delete();
            if (!delete) {
                result.put("success", false);
                result.put("msg", "文件删除失败");
            }
        } else {
            result.put("success", false);
            result.put("msg", "文件不存在");
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
