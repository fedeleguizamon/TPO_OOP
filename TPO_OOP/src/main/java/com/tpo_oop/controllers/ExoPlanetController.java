package com.tpo_oop.controllers;

import com.tpo_oop.entities.Error;
import com.tpo_oop.entities.ExoPlanet;
import com.tpo_oop.entities.Messages;
import com.tpo_oop.entities.ObserversRequest;
import com.tpo_oop.services.SpacialObserverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController("/topsecret")
@CrossOrigin(origins = "*")
@RequestMapping(path = "/topsecret")
public class ExoPlanetController {
    @Autowired
    private SpacialObserverService spacialObserverService;

    /**
     * Devuelve el mensaje secreto
     */
    @PostMapping("/encryptedMessage")
    public ResponseEntity<?> getEncryptedMessage(@RequestBody Messages messages) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.spacialObserverService.getEncryptedMessage(messages));
        } catch (Exception e) {
            Error error = new Error();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Edita el mensaje secreto
     */
    @PostMapping("/editMessage")
    public ResponseEntity<?> editSecretMessage(String configFile) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.spacialObserverService.editSecretMessage(configFile));
        } catch (Exception e) {
            Error error = new Error();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Calcula las coordenadas del exoplaneta
     */
    @GetMapping("/planetCoordinates")
    public ResponseEntity<?> getPlanetCoordinates(@RequestParam double x1,
                                                  @RequestParam double x2,
                                                  @RequestParam double x3,
                                                  @RequestParam double y1,
                                                  @RequestParam double y2,
                                                  @RequestParam double y3,
                                                  @RequestParam String d1,
                                                  @RequestParam String d2,
                                                  @RequestParam String d3) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.spacialObserverService.getPlanetCoordinates(x1,x2,x3,y1,y2,y3,d1,d2,d3));
        } catch (Exception e) {
            Error error = new Error();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Maneja la request y devuelve el exoplaneta
     */
    @PostMapping("/request")
    public ResponseEntity<?> getRequest(@RequestBody ObserversRequest json) {
        try {
            ExoPlanet exoPlanet = this.spacialObserverService.getRequest(json);
            return ResponseEntity.status(HttpStatus.OK).body(exoPlanet);
        } catch (Exception e) {
            Error error = new Error();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
