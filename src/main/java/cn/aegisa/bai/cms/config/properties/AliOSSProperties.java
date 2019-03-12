package cn.aegisa.bai.cms.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xianyingda@hnair.com
 * @serial
 * @since 2019-02-14 16:05
 */
@Data
@ConfigurationProperties(prefix = "ali.oss")
public class AliOSSProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
