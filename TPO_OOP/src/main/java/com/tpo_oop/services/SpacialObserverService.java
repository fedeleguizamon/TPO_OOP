package com.tpo_oop.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpo_oop.entities.*;
import com.tpo_oop.exceptions.NoSpacialObserversException;
import com.tpo_oop.repositories.SatelliteRepository;
import com.tpo_oop.repositories.SpacialObserverRepository;
import com.tpo_oop.repositories.TelescopeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SpacialObserverService implements BaseService<SpacialObserver> {
    @Autowired
    private SpacialObserverRepository spacialObserverRepository;
    @Autowired
    private TelescopeRepository telescopeRepository;
    @Autowired
    private SatelliteRepository satelliteRepository;

    @Transactional
    public Point2D getPlanetCoordinates(double x1, double x2, double x3,
                                double y1, double y2, double y3,
                                String d1, String d2, String d3) {
        double dis1 = convertUnit(d1);
        double dis2 = convertUnit(d2);
        double dis3 = convertUnit(d3);
        double A = 2 * (x2 - x1);
        double B = 2 * (y2 - y1);
        double C = dis1 * dis1 - dis2 * dis2 - x1 * x1 + x2 * x2 - y1 * y1 + y2 * y2;
        double D = 2 * (x3 - x2);
        double E = 2 * (y3 - y2);
        double F = dis2 * dis2 - dis3 * dis3 - x2 * x2 + x3 * x3 - y2 * y2 + y3 * y3;

        double x = (C * E - F * B) / (E * A - B * D);
        double y = (C * D - A * F) / (B * D - A * E);

        return new Point2D.Double(x, y);
    }

    @Transactional
    public String getEncryptedMessage(List<String> message1, List<String> message2, List<String> message3) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i<message1.size(); i++) {
            if (!message1.get(i).isEmpty()) {
                encryptedMessage.append(message1.get(i));
            } else if (!message2.get(i).isEmpty()) {
                encryptedMessage.append(message2.get(i));
            } else {
                encryptedMessage.append(message3.get(i));
            }
        }
        return encryptedMessage.toString();
    }

    @Transactional
    public ExoPlanet getRequest(String json) throws Exception {
        clearAll();
        ObjectMapper objectMapper = new ObjectMapper();
        RequestHandler handler = objectMapper.readValue(json, RequestHandler.class);
        Map<String, Object> map = coordinates(handler);
        double x1 = (double) map.get("x1");
        double x2 = (double) map.get("x2");
        double x3 = (double) map.get("x3");
        double y1 = (double) map.get("y1");
        double y2 = (double) map.get("y2");
        double y3 = (double) map.get("y3");
        double d1 = (double) map.get("d1");
        double d2 = (double) map.get("d2");
        double d3 = (double) map.get("d3");
        String message = (String) map.get("message");

        double A = 2 * (x2 - x1);
        double B = 2 * (y2 - y1);
        double C = d1 * d1 - d2 * d2 - x1 * x1 + x2 * x2 - y1 * y1 + y2 * y2;
        double D = 2 * (x3 - x2);
        double E = 2 * (y3 - y2);
        double F = d2 * d2 - d3 * d3 - x2 * x2 + x3 * x3 - y2 * y2 + y3 * y3;

        double x = (C * E - F * B) / (E * A - B * D);
        double y = (C * D - A * F) / (B * D - A * E);

        return new ExoPlanet(x, y, message);
    }

    private Map<String, Object> coordinates(RequestHandler handler) throws Exception {
        if (handler.getSatellites().size() + handler.getTelescopes().size() != 3) {
            throw new NoSpacialObserversException("Elementos insuficientes");
        }
        double x1;
        double x2;
        double x3;
        double y1;
        double y2;
        double y3;
        double d1;
        double d2;
        double d3;
        String message;
        List<Satellite> satelliteList = handler.getSatellites();
        List<Telescope> telescopeList = handler.getTelescopes();
        try {
            if (satelliteList.size() == 1) {
                this.save(satelliteList.get(0));
                this.save(telescopeList.get(0));
                this.save(telescopeList.get(1));
                x1 = satelliteList.get(0).getCoordinateX();
                y1 = satelliteList.get(0).getCoordinateY();
                d1 = satelliteList.get(0).getDistance();
                List<String> message1 = satelliteList.get(0).getMessage();
                x2 = telescopeList.get(0).getCoordinateX();
                y2 = telescopeList.get(0).getCoordinateY();
                d2 = telescopeList.get(0).getDistance();
                List<String> message2 = telescopeList.get(0).getMessage();
                x3 = telescopeList.get(1).getCoordinateX();
                y3 = telescopeList.get(1).getCoordinateY();
                d3 = telescopeList.get(1).getDistance();
                List<String> message3 = telescopeList.get(1).getMessage();
                message = this.getEncryptedMessage(message1, message2, message3);
            } else if (satelliteList.size() == 2) {
                this.save(satelliteList.get(0));
                this.save(satelliteList.get(1));
                this.save(telescopeList.get(0));
                x1 = satelliteList.get(0).getCoordinateX();
                y1 = satelliteList.get(0).getCoordinateY();
                d1 = satelliteList.get(0).getDistance();
                List<String> message1 = satelliteList.get(0).getMessage();
                x2 = satelliteList.get(1).getCoordinateX();
                y2 = satelliteList.get(1).getCoordinateY();
                d2 = satelliteList.get(1).getDistance();
                List<String> message2 = satelliteList.get(1).getMessage();
                x3 = telescopeList.get(0).getCoordinateX();
                y3 = telescopeList.get(0).getCoordinateY();
                d3 = telescopeList.get(0).getDistance();
                List<String> message3 = telescopeList.get(0).getMessage();
                message = this.getEncryptedMessage(message1, message2, message3);
            } else {
                this.save(satelliteList.get(0));
                this.save(satelliteList.get(1));
                this.save(satelliteList.get(2));
                x1 = satelliteList.get(0).getCoordinateX();
                y1 = satelliteList.get(0).getCoordinateY();
                d1 = satelliteList.get(0).getDistance();
                List<String> message1 = satelliteList.get(0).getMessage();
                x2 = satelliteList.get(1).getCoordinateX();
                y2 = satelliteList.get(1).getCoordinateY();
                d2 = satelliteList.get(1).getDistance();
                List<String> message2 = satelliteList.get(1).getMessage();
                x3 = satelliteList.get(2).getCoordinateX();
                y3 = satelliteList.get(2).getCoordinateY();
                d3 = satelliteList.get(2).getDistance();
                List<String> message3 = satelliteList.get(2).getMessage();
                message = this.getEncryptedMessage(message1, message2, message3);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("x1", x1);
        map.put("x2", x2);
        map.put("x3", x3);
        map.put("y1", y1);
        map.put("y2", y2);
        map.put("y3", y3);
        map.put("d1", d1);
        map.put("d2", d2);
        map.put("d3", d3);
        map.put("message", message);
        return map;
    }

    private double convertUnit(String distance) {
        String value = distance.substring(0, distance.length()-2);
        String unit = distance.substring(distance.length()-2).toLowerCase();

        return switch (unit) {
            case "km" -> Double.parseDouble(value) * 1000;
            case "cm" -> Double.parseDouble(value) / 100;
            case "m" -> Double.parseDouble(value);
            default -> Double.parseDouble(value);
        };
    }

    private void clearAll() throws Exception {
        List<SpacialObserver> entities = this.spacialObserverRepository.findAll();
        for (SpacialObserver entity : entities) {
            if (entity instanceof Telescope)
                this.telescopeRepository.deleteById(((Telescope) entity).getId());
            if (entity instanceof Satellite) {
                this.satelliteRepository.deleteById(((Satellite) entity).getId());
            }
        }
    }

    @Override
    @Transactional
    public List<SpacialObserver> findAll() throws Exception {
        try {
            List<SpacialObserver> entities = this.spacialObserverRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public SpacialObserver findById(Integer id) throws Exception {
        try {
            Optional<SpacialObserver> entityOptinal = this.spacialObserverRepository.findById(id);
            return entityOptinal.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public SpacialObserver save(SpacialObserver entity) throws Exception {
        try {
            entity = this.spacialObserverRepository.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public SpacialObserver update(Integer id, SpacialObserver entity) throws Exception {
        try {
            Optional<SpacialObserver> entityOptional = this.spacialObserverRepository.findById(id);
            SpacialObserver spacialObserver = entityOptional.get();
            spacialObserver = this.spacialObserverRepository.save(entity);
            return spacialObserver;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Integer id) throws Exception {
        try {
            if (this.spacialObserverRepository.existsById(id)) {
                this.spacialObserverRepository.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
