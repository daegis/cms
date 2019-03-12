package cn.aegisa.bai.cms.service.impl;

import cn.aegisa.bai.cms.config.properties.AliOSSProperties;
import cn.aegisa.bai.cms.config.properties.CommonProperties;
import cn.aegisa.bai.cms.service.ImgService;
import cn.aegisa.bai.model.UploadImg;
import cn.aegisa.spring.boot.mybatis.component.service.ICommonService;
import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xianyingda@hnair.com
 * @serial
 * @since 2019-02-16 13:01
 */
@Service
@Slf4j
public class ImgServiceImpl implements ImgService {

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private OSSClient ossClient;

    @Autowired
    private AliOSSProperties ossProperties;

    @Override
    public void saveUploadInfo(String fileName) {
        String fullUrl = commonProperties.getImgUrl() + fileName;
        UploadImg uploadImg = new UploadImg();
        uploadImg.setCdnUrl(fullUrl);
        uploadImg.setUploadTime(LocalDateTime.now());
        uploadImg.setImgName(fileName);
        uploadImg.setDescription("这个人很懒，什么都没写");
        commonService.save(uploadImg);
    }

    @Override
    public List<UploadImg> getAllImgs() {
        return commonService.getList(UploadImg.class);
    }

    @Override
    public boolean deleteImg(Integer id) {
        UploadImg uploadImg = commonService.get(id, UploadImg.class);
        if (uploadImg == null) {
            return false;
        }
        String imgName = uploadImg.getImgName();
        ossClient.deleteObject(ossProperties.getBucketName(), imgName);
        commonService.delete(uploadImg.getId(), UploadImg.class);
        return true;
    }
}
