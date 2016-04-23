package me.weego.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tcl
 */
public class LoggerUtil {
    private static final Logger BIZ_LOGGER = LoggerFactory.getLogger("biz");
    private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("error");
    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("debug");

    public static void logBiz(String info, Object o) {
        if (BIZ_LOGGER.isInfoEnabled()) {
            BIZ_LOGGER.info(info + " {}", o);
        }
    }

    public static void logError(String info, Throwable t) {
        if (ERROR_LOGGER.isErrorEnabled()) {
            ERROR_LOGGER.error(info, t);
        }
    }

    public static void logDebug(String info, Object o) {
        if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug(info + " {}", o);
        }
    }
}
