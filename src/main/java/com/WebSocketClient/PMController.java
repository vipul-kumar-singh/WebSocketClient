package com.WebSocketClient;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping(value = "/send")
    public ResponseEntity send(@RequestBody Message Message) throws JsonProcessingException {

        if (!(new MyStompSessionHandler().sendmsg(Message)))
            return new ResponseEntity<>(new ResponseEntityBody("    Not Connected"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ResponseEntityBody("Success"), HttpStatus.OK);
    }

    @GetMapping(value = "/connect")
    public ResponseEntity connectToSocket() throws InterruptedException, BrokenBarrierException {

        webSocketService.webSocketConnection();
        return new ResponseEntity<>(new ResponseEntityBody("Connection Successful"), HttpStatus.OK);
    }

    @PostMapping(value = "/disconnect")
    public ResponseEntity disconnectToSocket() {
        try {
            new MyStompSessionHandler().disconnectWebSocket();

            return new ResponseEntity<>(new ResponseEntityBody("Disconnected Successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseEntityBody("Connection Already Closed"), HttpStatus.BAD_REQUEST);
        }
    }

}
