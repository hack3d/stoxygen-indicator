package de.stoxygen.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class StoxygenIndicatorConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(StoxygenIndicatorConfiguration.class);

    @Value("${downloader.url:}")
    private String downloader_url;


    public String getDownloader_url() {
        return downloader_url;
    }

}
