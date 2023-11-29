package com.tpo_oop.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpo_oop.entities.*;
import com.tpo_oop.exceptions.NoSpacialObserversException;
import com.tpo_oop.exceptions.RepeatedCoordinatesException;
import com.tpo_oop.exceptions.TooManySpacialObserversException;
import com.tpo_oop.repositories.SpacialObserverRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class SpacialObserverService implements BaseService<SpacialObserver> {
    @Autowired
    private SpacialObserverRepository spacialObserverRepository;

    // Recibe las coordenadas de los satelites y sus distancias (pueden ser en km, m o cm)
    @Transactional
    public Point2D getPlanetCoordinates(double x1, double x2, double x3,
                                double y1, double y2, double y3,
                                String d1, String d2, String d3) throws Exception {

        if ((x1 == x2 && y1 == y2) || (x1 == x3 && y1 == y3) || (x2 == x3 && y2 == y3)) {
            throw new RepeatedCoordinatesException("Coordenadas repetidas");
        }

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

    // Devuelve el mensaje secreto lo mas parecido a la realidad
    @Transactional
    public String getEncryptedMessage(List<String> message1, List<String> message2, List<String> messagee3) {
        List<String> realMessage = new ArrayList<>();

        for (int i = 0; i < message1.size(); i++) {
            List<String> words = new ArrayList<>();
            words.add(message1.get(i));
            words.add(message2.get(i));
            words.add(messagee3.get(i));

            List<String> filtrateWords = new ArrayList<>();
            for (String word : words) {
                if (!word.isEmpty()) {
                    filtrateWords.add(word);
                }
            }

            if (!filtrateWords.isEmpty()) {
                realMessage.add(filtrateWords.get(0));
            }
        }

        return realMessage.toString();
    }

    // Metodo que maneja el archivo de edicion del mensaje secreto
    @Transactional
    public List<String[]> editSecretMessage(String configFile) {
        List<String[]> satelliteMessages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                satelliteMessages.add(words);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return satelliteMessages;
    }

    // Maneja la request recibida y devuelve un exoplaneta
    @Transactional
    public ExoPlanet getRequest(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestHandler handler = objectMapper.readValue(json, RequestHandler.class);

        Map<String, Object> map = coordinates(handler);
        double d1 = (double) map.get("d1");
        double d2 = (double) map.get("d2");
        double d3 = (double) map.get("d3");
        List<String> message1 = (List<String>) map.get("message1");
        List<String> message2 = (List<String>) map.get("message2");
        List<String> message3 = (List<String>) map.get("message3");
        double flag = (double) map.get("flag");
        String message = getEncryptedMessage(message1, message2, message3);
        SpacialObserver so1 = new SpacialObserver(1, Type.TELESCOPE, "James Webb",d1 ,message1, 5, 25);
        SpacialObserver so2 = new SpacialObserver(2, Type.TELESCOPE, "Hubble",d2 ,message2, -40, 30);
        SpacialObserver so3 = new SpacialObserver(3, Type.TELESCOPE, "Chandra",d3 ,message3, -20, -15);
        SpacialObserver so4 = new SpacialObserver(4, Type.SATELLITE, "Explorer 1",d1 ,message1, 40, 10);
        SpacialObserver so5 = new SpacialObserver(5, Type.SATELLITE, "Curiosity",d2 ,message2, 5, -5);
        SpacialObserver so6 = new SpacialObserver(6, Type.SATELLITE, "Landsat",d3 ,message3, -10, -35);

        double x1;
        double y1;
        double x2;
        double y2;
        double x3;
        double y3;
        if (flag == 0) {
            x1 = so1.getCoordinateX();
            y1 = so1.getCoordinateY();
            x2 = so2.getCoordinateX();
            y2 = so2.getCoordinateY();
            x3 = so3.getCoordinateX();
            y3 = so3.getCoordinateY();
        } else if (flag == 1) {
            x1 = so1.getCoordinateX();
            y1 = so1.getCoordinateY();
            x2 = so2.getCoordinateX();
            y2 = so2.getCoordinateY();
            x3 = so4.getCoordinateX();
            y3 = so4.getCoordinateY();
        } else if (flag == 2) {
            x1 = so1.getCoordinateX();
            y1 = so1.getCoordinateY();
            x2 = so4.getCoordinateX();
            y2 = so4.getCoordinateY();
            x3 = so5.getCoordinateX();
            y3 = so5.getCoordinateY();
        } else {
            x1 = so4.getCoordinateX();
            y1 = so4.getCoordinateY();
            x2 = so5.getCoordinateX();
            y2 = so5.getCoordinateY();
            x3 = so6.getCoordinateX();
            y3 = so6.getCoordinateY();
        }

        double A = 2 * (x2 - x1);
        double B = 2 * (y2 - y1);
        double C = d1 * d1 - d2 * d2 - x1 * x1 + x2 * x2 - y1 * y1 + y2 * y2;
        double D = 2 * (x3 - x2);
        double E = 2 * (y3 - y2);
        double F = d2 * d2 - d3 * d3 - x2 * x2 + x3 * x3 - y2 * y2 + y3 * y3;

        double x = (C * E - F * B) / (E * A - B * D);
        double y = (C * D - A * F) / (B * D - A * E);

        return new ExoPlanet(1, x, y, message);
    }

    // Recupera las distancias y mensajes enviados por la request
    private Map<String, Object> coordinates(RequestHandler handler) throws Exception {
        if (handler.getSatellites().size() + handler.getTelescopes().size() < 3) {
            throw new NoSpacialObserversException("Elementos insuficientes");
        }
        if (handler.getSatellites().size() + handler.getTelescopes().size() > 3) {
            throw new TooManySpacialObserversException("Demasiados elementos");
        }

        double d1;
        double d2;
        double d3;
        List<String> message1;
        List<String> message2;
        List<String> message3;
        double flag = 0; // Utiliza una bandera para posteriormente saber que satelites
                        // o telescopios se utilizaron
        List<SpacialObserver> satelliteList = handler.getSatellites();
        List<SpacialObserver> telescopeList = handler.getTelescopes();
        try {
            if (satelliteList.size() == 0) {
                d1 = telescopeList.get(0).getDistance();
                message1 = telescopeList.get(0).getMessage();
                d2 = telescopeList.get(1).getDistance();
                message2 = telescopeList.get(1).getMessage();
                d3 = telescopeList.get(2).getDistance();
                message3 = telescopeList.get(2).getMessage();
            } else if (satelliteList.size() == 1) {
                d1 = satelliteList.get(0).getDistance();
                message1 = satelliteList.get(0).getMessage();
                d2 = telescopeList.get(0).getDistance();
                message2 = telescopeList.get(0).getMessage();
                d3 = telescopeList.get(1).getDistance();
                message3 = telescopeList.get(1).getMessage();
                flag = 1;
            } else if (satelliteList.size() == 2) {
                d1 = satelliteList.get(0).getDistance();
                message1 = satelliteList.get(0).getMessage();
                d2 = satelliteList.get(1).getDistance();
                message2 = satelliteList.get(1).getMessage();
                d3 = telescopeList.get(0).getDistance();
                message3 = telescopeList.get(0).getMessage();
                flag = 2;
            } else {
                d1 = satelliteList.get(0).getDistance();
                message1 = satelliteList.get(0).getMessage();
                d2 = satelliteList.get(1).getDistance();
                message2 = satelliteList.get(1).getMessage();
                d3 = satelliteList.get(2).getDistance();
                message3 = satelliteList.get(2).getMessage();
                flag = 3;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("d1", d1);
        map.put("d2", d2);
        map.put("d3", d3);
        map.put("message1", message1);
        map.put("message2", message2);
        map.put("message3", message3);
        map.put("flag", flag);
        return map;
    }

    // Convierte todas las unidades a metros
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

    // A partir de aqui solo hay operaciones CRUD
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
            Optional<SpacialObserver> entityOptional = this.spacialObserverRepository.findById(id);
            return entityOptional.get();
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
