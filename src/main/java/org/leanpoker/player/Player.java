package org.leanpoker.player;

import com.google.gson.JsonElement;

import java.util.Map;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
    	System.err.println("betRequest: " +  request.toString());
        return 200;
    }

    public static void showdown(JsonElement game) {

    	System.err.println("showdown: " +  game.toString());
    }
}
