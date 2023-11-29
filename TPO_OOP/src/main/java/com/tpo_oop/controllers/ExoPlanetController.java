package com.tpo_oop.controllers;

import com.tpo_oop.entities.ExoPlanet;
import com.tpo_oop.services.ExoPlanetService;
import com.tpo_oop.services.SpacialObserverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/topsecret")
@CrossOrigin(origins = "*")
@RequestMapping(path = "/topsecret")
public class ExoPlanetController {
    @Autowired
    private ExoPlanetService exoPlanetService;
    @Autowired
    private SpacialObserverService spacialObserverService;

    @GetMapping("/encryptedMessage")
    public ResponseEntity<?> getEncryptedMessage(List<String> message1, List<String> message2, List<String> message3) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.spacialObserverService.getEncryptedMessage(message1, message2, message3));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente\"}");
        }
    }

    @PostMapping("/editMessage")
    public ResponseEntity<?> editSecretMessage(String configFile) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.spacialObserverService.editSecretMessage(configFile));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente\"}");
        }
    }

    @GetMapping("/planetCoordinates")
    public ResponseEntity<?> getPlanetCoordinates(double x1, double x2, double x3,
                                                  double y1, double y2, double y3,
                                                  String d1, String d2, String d3) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.spacialObserverService.getPlanetCoordinates(x1,x2,x3,y1,y2,y3,d1,d2,d3));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente\"}");
        }
    }

    @PostMapping("/request")
    public ResponseEntity<?> getRequest(@RequestBody String json) {
        try {
            ExoPlanet exoPlanet = this.spacialObserverService.getRequest(json);
            return ResponseEntity.status(HttpStatus.OK).body(exoPlanet);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente\"}");
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.exoPlanetService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente nuevamente\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.exoPlanetService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente nuevamente\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ExoPlanet entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.exoPlanetService.save(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ExoPlanet entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.exoPlanetService.update(id, entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.exoPlanetService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente\"}");
        }
    }
}
