package me.weego.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tcl
 */
public enum SpiderKeyEnum {
    GOOGLE_ONLINE("1", "google.key.online"),
    GOOGLE_OFFLINE("2", "google.key.offline"),
    GOOGLE_PLACE("6", "google.key.placestation"),
    BAIDU_KEY("4","baidu.key"),
    TRIP_ADVISOR("3", "tripadvisor.key"),
    UBER_SERVERTOKEN("5", "uber.token.server");


    private String type;
    private String key;

    SpiderKeyEnum(String type, String key) {
        this.type = type;
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private static Map<String, SpiderKeyEnum> envMap = new HashMap<String, SpiderKeyEnum>();

    static {
        for (SpiderKeyEnum env : SpiderKeyEnum.values()) {
            envMap.put(env.getType(), env);
        }
    }

    public static SpiderKeyEnum getSpiderKeyEnumByType(String type) {
        return envMap.get(type);
    }
}
