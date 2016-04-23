package me.weego.service;

/**
 * uber api
 */
public interface UberService {
    String getEstimatePrice(String startLat, String startLon,
                            String endLat, String endLon, int seatCount);
}
