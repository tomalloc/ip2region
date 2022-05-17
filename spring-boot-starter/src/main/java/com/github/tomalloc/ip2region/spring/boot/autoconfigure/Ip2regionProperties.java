package com.github.tomalloc.ip2region.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(Ip2regionProperties.PREFIX)
public class Ip2regionProperties {
    static final String PREFIX = "ip2region";

    static final String DEFAULT_IP_DATA = "ip2region.db";

    /**
     * total header data block size
     */
    private int totalHeaderSize=8 * 2048;

    /**
     * max index data block size
     * u should always choice the fastest read block size
     */
    private int indexBlockSize=4 * 2048;


    private Resource ip2regionData;

    public int getTotalHeaderSize() {
        return totalHeaderSize;
    }

    public void setTotalHeaderSize(int totalHeaderSize) {
        this.totalHeaderSize = totalHeaderSize;
    }

    public int getIndexBlockSize() {
        return indexBlockSize;
    }

    public void setIndexBlockSize(int indexBlockSize) {
        this.indexBlockSize = indexBlockSize;
    }

    public Resource getIp2regionData() {
        return ip2regionData;
    }

    public void setIp2regionData(Resource ip2regionData) {
        this.ip2regionData = ip2regionData;
    }
}
