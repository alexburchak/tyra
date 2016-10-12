package org.alexburchak.tyra.controller;

import lombok.extern.slf4j.Slf4j;
import org.alexburchak.tyra.config.TyraConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author alexburchak
 */
@Controller
@Slf4j
public class TyraController {
    public static final String PATH_TYRA = "/tyra";

    public static final String PARAM_SID = "sid";

    private static final String MODEL_ENDPOINT = "endpoint";
    private static final String MODEL_SID = "sid";

    private static final String RESULT_SUCCESS = "tyra";

    @Autowired
    private TyraConfiguration tyraConfiguration;

    @RequestMapping(PATH_TYRA)
    public String tyra(@RequestParam(PARAM_SID) String sid, Model model) {
        log.debug("Received {} request for sid {}", PATH_TYRA, sid);

        model.addAttribute(MODEL_ENDPOINT, tyraConfiguration.getEndpoint());
        model.addAttribute(MODEL_SID, sid);

        return RESULT_SUCCESS;
    }
}
