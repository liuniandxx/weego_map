package me.weego.service;

/**
 * @author tcl
 */
public interface SpiderService {
    String getGoogleTraffic(String mode, String origin, String dest);
    String getGoogleTraffic(String origin,String dest);
    String translate(String text);
}
