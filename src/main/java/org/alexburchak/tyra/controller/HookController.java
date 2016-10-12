package org.alexburchak.tyra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.alexburchak.tyra.data.DistributedWebSocketSessionManager;
import org.alexburchak.tyra.data.HttpRequest;
import org.alexburchak.tyra.ws.OutcomingMessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;

/**
 * @author alexburchak
 */
@RestController
@Slf4j
public class HookController {
    public static final String PATH_HOOK = "/hook";

    public static final String PARAM_SID = "sid";

    @Autowired
    private DistributedWebSocketSessionManager distributedWebSocketSessionManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(PATH_HOOK)
    public void hook(@RequestParam(PARAM_SID) String sid, HttpServletRequest request) throws URISyntaxException, IOException {
        log.debug("Received {} request for sid {}", PATH_HOOK, sid);

        HttpRequest httpRequest = new HttpRequest(request);

        log.debug("Created HTTP request {}", httpRequest);

        StringWriter writer = new StringWriter();
        objectMapper.writeValue(writer, httpRequest);

        distributedWebSocketSessionManager.send(sid, OutcomingMessageType.REQUEST.format(writer.toString()));
    }
}
