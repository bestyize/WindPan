package xyz.thewind.windpan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:common.properties"})
public class CommonConfig {
    @Value("${file.local_file_path}")
    public String localFilePath;

}
