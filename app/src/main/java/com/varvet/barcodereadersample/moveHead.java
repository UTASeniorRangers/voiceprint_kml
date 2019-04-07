package com.varvet.barcodereadersample;

import org.octoprint.api.PrinterCommand;
import org.octoprint.api.model.Axis;

public class moveHead {
    Recognizer voice = new Recognizer();

    final static PrinterCommand printer = new PrinterCommand(MainActivity.octoPrint);

    public static void moveLeft() {
        printer.moveOnAxis(Axis.getAxis("x"), -5);
    }
    public static void moveLeft(int distance) {
        printer.moveOnAxis(Axis.getAxis("x"), -distance);
    }

    public static void moveRight() {
        printer.moveOnAxis(Axis.getAxis("x"),5);
    }
    public static void moveRight(int distance) {
        printer.moveOnAxis(Axis.getAxis("x"),distance);
    }

    public static void moveFront() {
        printer.moveOnAxis(Axis.getAxis("y"),-5);
    }
    public static void moveFront(int distance) {
        printer.moveOnAxis(Axis.getAxis("y"),-distance);
    }

    public static void moveBack() {
        printer.moveOnAxis(Axis.getAxis("y"),5);
    }
    public static void moveBack(int distance) {
        printer.moveOnAxis(Axis.getAxis("y"),distance);
    }

    public static void moveUp() {
        printer.moveOnAxis(Axis.getAxis("z"),-5);
    }
    public static void moveUp(int distance) {
        printer.moveOnAxis(Axis.getAxis("z"),-distance);
    }

    public static void moveDown() {
        printer.moveOnAxis(Axis.getAxis("z"),5);
    }
    public static void moveDown(int distance) {
        printer.moveOnAxis(Axis.getAxis("z"),distance);
    }

    public static void moveHome() {
        printer.moveHome();
    }

}
