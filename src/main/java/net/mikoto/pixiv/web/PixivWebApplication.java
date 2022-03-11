package net.mikoto.pixiv.web;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static net.mikoto.pixiv.web.constant.Properties.MAIN_PROPERTIES;
import static net.mikoto.pixiv.web.util.FileUtil.createDir;
import static net.mikoto.pixiv.web.util.FileUtil.createFile;

/**
 * @author mikoto2464
 */

@SpringBootApplication
public class PixivWebApplication {

    public static void main(String[] args) throws IOException {
        createDir("config");
        createFile(new File("config/config.properties"), IOUtils.toString(Objects.requireNonNull(PixivWebApplication.class.getClassLoader().getResourceAsStream("config.properties")), StandardCharsets.UTF_8));
        MAIN_PROPERTIES.load(new FileReader("config/config.properties"));

        SpringApplication.run(PixivWebApplication.class, args);
    }

}
