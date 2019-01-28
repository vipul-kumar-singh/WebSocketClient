package com.WebSocketClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.BrokenBarrierException;

@RestController
public class PMController {

    private final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping(value = "/send")
    public ResponseEntity send(@RequestBody Message message) throws JsonProcessingException {
        LOGGER.info("PMController send..");

        if (!(webSocketService.sendMessage(message)))
            return new ResponseEntity<>(new ResponseEntityBody("Not Connected"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ResponseEntityBody("Success"), HttpStatus.OK);
    }

    @GetMapping(value = "/connect")
    public ResponseEntity connectToSocket() throws InterruptedException, BrokenBarrierException {
        LOGGER.info("PMController connectToSocket..");

        webSocketService.webSocketConnection();
        return new ResponseEntity<>(new ResponseEntityBody("Connection Successful"), HttpStatus.OK);
    }

    @GetMapping(value = "/disconnect")
    public ResponseEntity disconnectFromSocket() {
        LOGGER.info("PMController disconnectFromSocket..");

        if (!webSocketService.disconnect())
            return new ResponseEntity<>(new ResponseEntityBody("Connection Already Closed"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ResponseEntityBody("Disconnected Successfully"), HttpStatus.OK);
    }

}
