package com.theater.kobrin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("storage")
@Getter
@Setter
public class FileUploadProperties {
    private String locationExhibit = "C:\\Users\\dns\\Desktop\\программирование\\семестр №6\\веб\\kobrinKR\\src\\main\\resources\\static\\images";
    private String locationProfile= "C:\\Users\\dns\\Desktop\\программирование\\семестр №6\\веб\\kobrinKR\\src\\main\\resources\\static\\images";

}
