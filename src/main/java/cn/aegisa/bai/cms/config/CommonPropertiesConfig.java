package cn.aegisa.bai.cms.config;

import cn.aegisa.bai.cms.config.properties.AliOSSProperties;
import cn.aegisa.bai.cms.config.properties.CommonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author daegis@yeah.net
 * @serial
 * @since 2019-02-14 15:53
 */
@Configuration
@EnableConfigurationProperties({
        CommonProperties.class,
        AliOSSProperties.class})
public class CommonPropertiesConfig {

}
