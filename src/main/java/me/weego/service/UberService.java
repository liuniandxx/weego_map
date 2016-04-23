package me.weego.service;

/**
 * uber api
 */
public interface UberService {
    String getEstimatePrice(String start, String end);
    String getEstimatePrice(String start, String end, int seatCount);
}
