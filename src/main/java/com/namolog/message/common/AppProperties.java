package com.namolog.message.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "namo")
@Getter @Setter
public class AppProperties {

    @NotEmpty
    private Boolean enableIpProtect;

    @NotBlank
    private String niceSiteCode;

    @NotBlank
    private String niceSitePassword;

}
