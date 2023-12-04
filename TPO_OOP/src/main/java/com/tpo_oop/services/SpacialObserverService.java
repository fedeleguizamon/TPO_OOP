package com.tpo_oop.services;

import com.tpo_oop.entities.*;
import com.tpo_oop.exceptions.NoSpacialObserversException;
import com.tpo_oop.exceptions.RepeatedCoordinatesException;
import com.tpo_oop.exceptions.TooManySpacialObserversException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class SpacialObserverService {

    /**
     * Recibe las coordenadas de los satelites y sus distancias (pueden ser en km, m o cm)
     */
    @Transactional
    public Point2D getPlanetCoordinates(double x1, double x2, double x3,
                                double y1, double y2, double y3,
                                String d1, String d2, String d3) throws Exception {

        if ((x1 == x2 && y1 == y2) || (x1 == x3 && y1 == y3) || (x2 == x3 && y2 == y3)) {
            throw new RepeatedCoordinatesException("Coordenadas repetidas");
        }
        if (x1 == Double.NaN || x2 == Double.NaN || x3 == Double.NaN ||
                y1 == Double.NaN || y2 == Double.NaN || y3 == Double.NaN ||
                d1 == null || d2 == null || d3 == null) {
            throw new IllegalArgumentException("Parametros nulos");
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

    /**
     * Devuelve el mensaje secreto lo mas parecido a la realidad
     */
    @Transactional
    public String getEncryptedMessage(Messages messages) {
        // Utiliza contadores para averiguar cual es el mensaje con mayor cantidad de
        // elementos vacios o "desfasajes"
        int cont1 = countEmptyElements(messages.getMessage1());
        int cont2 = countEmptyElements(messages.getMessage2());
        int cont3 = countEmptyElements(messages.getMessage3());

        // Convierte los mensajes a arreglos primitivos para facilitar su manejo mas adelante
        String[] mes1 = messages.getMessage1().toArray(new String[0]);
        String[] mes2 = messages.getMessage2().toArray(new String[0]);
        String[] mes3 = messages.getMessage3().toArray(new String[0]);

        // Rellena el mensaje con mayor cantidad de elementos vacios y lo returnea

        if (cont1 >= cont2 && cont1 >= 3) {
            fillEmptyElements(mes1, mes2, mes3);
            return Arrays.toString(mes1);
        } else if (cont2 >= cont1 && cont2 >= cont3) {
            fillEmptyElements(mes2, mes1, mes3);
            return Arrays.toString(mes2);
        } else if (cont3 >= cont1 && cont3 >= cont1) {
            fillEmptyElements(mes3, mes1, mes2);
            return Arrays.toString(mes3);
        }
        return "Error";
    }

    /**
     * Metodo que maneja el archivo de edicion del mensaje secreto
     */
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

    /**
     * Maneja la request recibida y devuelve un exoplaneta
     */
    @Transactional
    public ExoPlanet getRequest(ObserversRequest json) throws Exception {
        Map<String, Object> map = coordinates(json);

        double d1 = (double) map.get("d1");
        double d2 = (double) map.get("d2");
        double d3 = (double) map.get("d3");
        List<String> message1 = (List<String>) map.get("message1");
        List<String> message2 = (List<String>) map.get("message2");
        List<String> message3 = (List<String>) map.get("message3");
        Messages messages = new Messages(message1, message2, message3);
        String name1 = (String) map.get("name1");
        String name2 = (String) map.get("name2");
        String name3 = (String) map.get("name3");
        double flag = (double) map.get("flag");
        String message = getEncryptedMessage(messages);

        double x1;
        double y1;
        double x2;
        double y2;
        double x3;
        double y3;
        if (flag == 0) {
            SpacialObserver so1 = new SpacialObserver(Type.TELESCOPE, name1,d1 ,message1, 5, 25);
            SpacialObserver so2 = new SpacialObserver(Type.TELESCOPE, name2,d2 ,message2, -40, 30);
            SpacialObserver so3 = new SpacialObserver(Type.TELESCOPE, name3,d3 ,message3, -20, -15);
            x1 = so1.getCoordinateX();
            y1 = so1.getCoordinateY();
            x2 = so2.getCoordinateX();
            y2 = so2.getCoordinateY();
            x3 = so3.getCoordinateX();
            y3 = so3.getCoordinateY();
        } else if (flag == 1) {
            SpacialObserver so1 = new SpacialObserver(Type.TELESCOPE, name1,d1 ,message1, 5, 25);
            SpacialObserver so2 = new SpacialObserver(Type.TELESCOPE, name2,d2 ,message2, -40, 30);
            SpacialObserver so3 = new SpacialObserver(Type.SATELLITE, name3,d3 ,message3, -20, -15);
            x1 = so1.getCoordinateX();
            y1 = so1.getCoordinateY();
            x2 = so2.getCoordinateX();
            y2 = so2.getCoordinateY();
            x3 = so3.getCoordinateX();
            y3 = so3.getCoordinateY();
        } else if (flag == 2) {
            SpacialObserver so1 = new SpacialObserver(Type.TELESCOPE, name1,d1 ,message1, 5, 25);
            SpacialObserver so2 = new SpacialObserver(Type.SATELLITE, name2,d2 ,message2, -40, 30);
            SpacialObserver so3 = new SpacialObserver(Type.SATELLITE, name3,d3 ,message3, -20, -15);
            x1 = so1.getCoordinateX();
            y1 = so1.getCoordinateY();
            x2 = so2.getCoordinateX();
            y2 = so2.getCoordinateY();
            x3 = so3.getCoordinateX();
            y3 = so3.getCoordinateY();
        } else {
            SpacialObserver so1 = new SpacialObserver(Type.SATELLITE, name1,d1 ,message1, 40, 10);
            SpacialObserver so2 = new SpacialObserver(Type.SATELLITE, name2,d2 ,message2, 5, -5);
            SpacialObserver so3 = new SpacialObserver(Type.SATELLITE, name3,d3 ,message3, -10, -35);
            x1 = so1.getCoordinateX();
            y1 = so1.getCoordinateY();
            x2 = so2.getCoordinateX();
            y2 = so2.getCoordinateY();
            x3 = so3.getCoordinateX();
            y3 = so3.getCoordinateY();
        }

        Point2D coords = getPlanetCoordinates(x1,x2,x3,y1,y2,y3,d1+"m",d2+"m",d3+"m");

        return new ExoPlanet(coords.getX(), coords.getY(), message);
    }

    /**
     * Recupera las distancias y mensajes enviados por la request
     */
    private Map<String, Object> coordinates(ObserversRequest json) throws Exception {
        if (json.getSatellites().size() + json.getTelescopes().size() < 3) {
            throw new NoSpacialObserversException("Elementos insuficientes");
        }
        if (json.getSatellites().size() + json.getTelescopes().size() > 3) {
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
        int satellitesSize = json.getSatellites().size();

        try {
            if (satellitesSize == 0) {
                d1 = json.getTelescopes().get(0).getDistance();
                message1 = json.getTelescopes().get(0).getMessage();
                d2 = json.getTelescopes().get(1).getDistance();
                message2 = json.getTelescopes().get(1).getMessage();
                d3 = json.getTelescopes().get(2).getDistance();
                message3 = json.getTelescopes().get(2).getMessage();
            } else if (satellitesSize == 1) {
                d1 = json.getSatellites().get(0).getDistance();
                message1 = json.getSatellites().get(0).getMessage();
                d2 = json.getTelescopes().get(0).getDistance();
                message2 = json.getTelescopes().get(0).getMessage();
                d3 = json.getTelescopes().get(1).getDistance();
                message3 = json.getTelescopes().get(1).getMessage();
                flag = 1;
            } else if (satellitesSize == 2) {
                d1 = json.getSatellites().get(0).getDistance();
                message1 = json.getSatellites().get(0).getMessage();
                d2 = json.getSatellites().get(1).getDistance();
                message2 = json.getSatellites().get(1).getMessage();
                d3 = json.getTelescopes().get(0).getDistance();
                message3 = json.getTelescopes().get(0).getMessage();
                flag = 2;
            } else {
                d1 = json.getSatellites().get(0).getDistance();
                message1 = json.getSatellites().get(0).getMessage();
                d2 = json.getSatellites().get(1).getDistance();
                message2 = json.getSatellites().get(1).getMessage();
                d3 = json.getSatellites().get(2).getDistance();
                message3 = json.getSatellites().get(2).getMessage();
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

    /**
     * Convierte todas las unidades a metros
     */
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

    /**
     * Cuenta los elementos vacios de los mensajes
     */
    private int countEmptyElements(List<String> message) {
        int count = 0;
        for (String word : message) {
            if (word.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Rellena los espacios vacios del primer mensaje
     */
    private void fillEmptyElements(String[] target, String[] source1, String[] source2) {
        // Primero se fija si el ultimo elemento esta vacio y lo rellena
        if (target[target.length - 1].isEmpty()) {
            if (!source1[source1.length - 1].isEmpty()) {
                target[target.length - 1] = source1[source1.length - 1];
            } else {
                target[target.length - 1] = source2[source2.length - 1];
            }
        }
        // Rellena los elementos vacios de target
        for (int i = 0; i < target.length; i++) {
            if (target[i].isEmpty()) {
                if (!source1[i].isEmpty()) {
                    target[i] = source1[i];
                } else {
                    target[i] = source2[i];
                }
            }
        }
    }
}
