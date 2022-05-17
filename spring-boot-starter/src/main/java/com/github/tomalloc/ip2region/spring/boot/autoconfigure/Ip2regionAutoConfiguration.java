package com.github.tomalloc.ip2region.spring.boot.autoconfigure;

import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@EnableConfigurationProperties(Ip2regionProperties.class)
@Configuration
public class Ip2regionAutoConfiguration {
    private Ip2regionProperties ip2regionProperties;

    public Ip2regionAutoConfiguration(Ip2regionProperties ip2regionProperties){
        this.ip2regionProperties=ip2regionProperties;
    }
    @Bean(destroyMethod = "close")
    public DbSearcher dbSearcher() throws DbMakerConfigException, FileNotFoundException {
        DbConfig config = new DbConfig(this.ip2regionProperties.getTotalHeaderSize());
        config.setIndexBlockSize(this.ip2regionProperties.getIndexBlockSize());
        Resource resource=this.ip2regionProperties.getIp2regionData();
        if(resource==null){
            resource = new ClassPathResource(Ip2regionProperties.DEFAULT_IP_DATA);
        }
        if(resource==null||!resource.exists()){
            throw new FileNotFoundException("ip2region data file not found");
        }

        try(InputStream is=resource.getInputStream()){
            Path path=Files.createTempFile("ip2region_",".db");
            try(OutputStream os=Files.newOutputStream(path)) {
                is.transferTo(os);
                return new DbSearcher(config,path.toString());
            }
        } catch (IOException e) {
            throw new DbMakerConfigException("read ip2region data["+resource+"] fail");
        }

    }
}
