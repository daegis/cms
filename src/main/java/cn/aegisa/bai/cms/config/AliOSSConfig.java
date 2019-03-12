package cn.aegisa.bai.cms.config;

import cn.aegisa.bai.cms.config.properties.AliOSSProperties;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author daegis@yeah.net
 * @serial
 * @since 2019-02-14 15:41
 */
@Configuration
public class AliOSSConfig {

    @Autowired
    private AliOSSProperties ossProperties;

    @Bean
    public OSSClient oSSClient() {
        String endpoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

}
