package com.github.tomalloc.ip2region.spring.boot.autoconfigure;

import org.junit.jupiter.api.Test;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;


import static org.assertj.core.api.Assertions.assertThat;

public class Ip2regionAutoConfigurationTest {

    @Test
    void ip2regionBeanTest() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(Ip2regionAutoConfiguration.class));

        contextRunner.run((context) -> {
            DbSearcher dbSearcher = context.getBean(DbSearcher.class);
            assertThat(dbSearcher).isNotNull();
            String ip = "110.95.63.69";
            DataBlock dataBlock = dbSearcher.binarySearch(ip);
            assertThat(dataBlock).isNotNull();
            assertThat(dataBlock.getRegion()).isEqualTo("中国|0|北京|北京市|电信");
        });
    }


}
