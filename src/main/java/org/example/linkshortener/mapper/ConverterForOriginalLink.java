package org.example.linkshortener.mapper;

import org.example.linkshortener.dto.LinkDto;
import org.example.linkshortener.entity.OriginalLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterForOriginalLink {
    private final MapperConfig mapperConfig;

    @Autowired
    public ConverterForOriginalLink(MapperConfig mapperConfig) {
        this.mapperConfig = mapperConfig;
    }

    public OriginalLink convertToOriginalLink(LinkDto linkDto) {
        return this.mapperConfig
                .modelMapper()
                .map(linkDto, OriginalLink.class);
    }
}
