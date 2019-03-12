package cn.aegisa.bai.cms.vo;

import lombok.Data;

/**
 * @author daegis@yeah.net
 * @serial
 * @since 2019-02-11 16:21
 */
@Data
public class ImageVo {
    private Integer id;
    private String name;
    private String url;
    private String uploadTime;
}
