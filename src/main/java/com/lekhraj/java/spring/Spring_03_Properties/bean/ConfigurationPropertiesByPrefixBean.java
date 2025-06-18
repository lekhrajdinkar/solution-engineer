package com.lekhraj.java.spring.Spring_03_Properties.bean;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "mail")
@ConfigurationPropertiesScan // register bean
@Component // not needed
public class ConfigurationPropertiesByPrefixBean
{
    @NotBlank private String hostname;
    private int port;
    private String from;
    private List<String> defaultRecipients;
    private Map<String, String> additionalHeaders;
    private Credentials credentials1; // inbuilt binder
    private Credentials credentials2; // my binder/converter

    // --- inbuilt - Conversion for Duration,DataSize,etc ---

    // @DurationUnit(ChronoUnit.DAYS)
    // private Duration timeInDays;

    // @DataSizeUnit(DataUnit.TERABYTES)
    // private DataSize sizeInTB;
}

/*

// -- validation (JSR-380 format) while binding---

@NotBlank
@length(min,max)
@Max(1025)
@Min(1025)
@Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")

 */

