package com.example.loginandsplashscreen.Handlers;

public class InstructionHandler {
    public static int getInstruction(String instruction) {
        String operation = instruction;
        switch(operation) {
            case "login":
                return 1;
            case "getInfo":
                return 2;
            case "changePassword":
                return 3;
            case "transfer":
                return 4;
            case "forgotPassword":
                return 5;
            case "requestQRCode":
                return 6;
            default:
                return -1;
        }
    }
}
