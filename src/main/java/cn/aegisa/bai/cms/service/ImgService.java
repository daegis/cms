package cn.aegisa.bai.cms.service;

import cn.aegisa.bai.model.UploadImg;

import java.util.List;

/**
 * @author xianyingda@guazi.com
 * @serial
 * @since 2019-02-16 13:01
 */
public interface ImgService {

    void saveUploadInfo(String fileName);

    List<UploadImg> getAllImgs();

    boolean deleteImg(Integer id);
}
