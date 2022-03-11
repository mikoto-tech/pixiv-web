package net.mikoto.pixiv.web.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static net.mikoto.pixiv.web.constant.Properties.MAIN_PROPERTIES;

/**
 * @author mikoto
 * @date 2022/2/26 15:06
 */
@RestController
public class UserController {
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    @RequestMapping(
            value = "/user/register",
            method = RequestMethod.GET
    )
    public String registerUser() throws IOException {
        InputStream inputStream = UserController.class.getClassLoader().getResourceAsStream("register.html");
        assert inputStream != null;
        String register = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        inputStream.close();

        register = register.replace("${pixiv-central-url}", MAIN_PROPERTIES.getProperty("PIXIV_CENTRAL_URL"));
        register = register.replace("${recaptcha-site-key}", MAIN_PROPERTIES.getProperty("RECAPTCHA_SITE_KEY"));
        String publicKeyUrl = MAIN_PROPERTIES.getProperty("PIXIV_CENTRAL_URL") + "/web/publicKey";
        Request publicKeyRequest = new Request.Builder()
                .url(publicKeyUrl)
                .get()
                .build();
        Response publicKeyResponse = OK_HTTP_CLIENT.newCall(publicKeyRequest).execute();
        register = register.replace("${public-key}", Objects.requireNonNull(publicKeyResponse.body()).string());
        return register;
    }

    @RequestMapping(
            value = "/user/login",
            method = RequestMethod.GET
    )
    public String loginUser() throws IOException {
        InputStream inputStream = UserController.class.getClassLoader().getResourceAsStream("login.html");
        assert inputStream != null;
        String loginPage = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        inputStream.close();

        String publicKeyUrl = MAIN_PROPERTIES.getProperty("PIXIV_CENTRAL_URL") + "/web/publicKey";
        Request publicKeyRequest = new Request.Builder()
                .url(publicKeyUrl)
                .get()
                .build();
        Response publicKeyResponse = OK_HTTP_CLIENT.newCall(publicKeyRequest).execute();
        loginPage = loginPage.replace("${pixiv-central-url}", MAIN_PROPERTIES.getProperty("PIXIV_CENTRAL_URL"));
        loginPage = loginPage.replace("${recaptcha-site-key}", MAIN_PROPERTIES.getProperty("RECAPTCHA_SITE_KEY"));
        loginPage = loginPage.replace("${public-key}", Objects.requireNonNull(publicKeyResponse.body()).string());
        return loginPage;
    }
}
