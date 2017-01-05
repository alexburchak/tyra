package org.alexburchak.tyra.controller;

import lombok.extern.slf4j.Slf4j;
import org.alexburchak.tyra.config.TyraConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author alexburchak
 */
@Controller
@Slf4j
public class IndexController {
    public static final String PATH_INDEX = "/";

    private static final String MODEL_BASE_URL = "baseUrl";
    private static final String MODEL_API_KEY = "apiKey";

    private static final String RESULT_SUCCESS = "index";

    @Autowired
    private TyraConfiguration tyraConfiguration;

    @RequestMapping(PATH_INDEX)
    public String index(HttpServletRequest request, Model model) throws MalformedURLException {
        log.debug("Received {} request", PATH_INDEX);

        URL url = new URL(request.getRequestURL().toString());

        model.addAttribute(MODEL_BASE_URL, new URL(url.getProtocol(), url.getHost(), url.getPort(), HookController.PATH_HOOK).toString());
        model.addAttribute(MODEL_API_KEY, tyraConfiguration.getApiKey());

        return RESULT_SUCCESS;
    }
}
