package cn.aegisa.bai.cms.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author daegis@yeah.net
 * @serial
 * @since 2019-02-14 16:05
 */
@Data
@ConfigurationProperties(prefix = "app.bai")
public class CommonProperties {
    private String imgUrl;
}