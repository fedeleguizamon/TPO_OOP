package com.tpo_oop;

import java.awt.geom.Point2D;

public class Test {
    public static void main(String[] args) {
        // Coordenadas de los satélites
        Point2D.Double satellite1 = new Point2D.Double(1, 1);
        Point2D.Double satellite2 = new Point2D.Double(5, 1);
        Point2D.Double satellite3 = new Point2D.Double(3, 4);

        // Distancias desde los satélites al exoplaneta
        double distance1 = 2.5;
        double distance2 = 3.6;
        double distance3 = 4.9;

        // Calcular las coordenadas del exoplaneta
        Point2D.Double exoplanetCoordinates = trilaterate(satellite1, satellite2, satellite3, distance1, distance2, distance3);

        // Imprimir las coordenadas del exoplaneta
        System.out.println("Coordenadas del exoplaneta: (" + exoplanetCoordinates.x + ", " + exoplanetCoordinates.y + ")");
    }

    public static Point2D.Double trilaterate(Point2D.Double satellite1, Point2D.Double satellite2, Point2D.Double satellite3, double distance1, double distance2, double distance3) {
        // Calcular las diferencias de coordenadas entre los satélites
        double x1 = satellite1.x;
        double y1 = satellite1.y;
        double x2 = satellite2.x;
        double y2 = satellite2.y;
        double x3 = satellite3.x;
        double y3 = satellite3.y;

        // Calcular las diferencias de distancias entre los satélites y el exoplaneta
        double d1 = distance1;
        double d2 = distance2;
        double d3 = distance3;

        // Calcular las coordenadas del exoplaneta
        double A = 2 * (x2 - x1);
        double B = 2 * (y2 - y1);
        double C = d1 * d1 - d2 * d2 - x1 * x1 + x2 * x2 - y1 * y1 + y2 * y2;
        double D = 2 * (x3 - x2);
        double E = 2 * (y3 - y2);
        double F = d2 * d2 - d3 * d3 - x2 * x2 + x3 * x3 - y2 * y2 + y3 * y3;

        double x = (C * E - F * B) / (E * A - B * D);
        double y = (C * D - A * F) / (B * D - A * E);

        return new Point2D.Double(x, y);
    }
}
