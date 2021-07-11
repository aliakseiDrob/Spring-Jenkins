package com.epam.esm.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * This class consists of static utility methods for operating on objects
 */
public class ServiceUtils {

    private ServiceUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * checks if param empty or equals null
     *
     * @param param param to check, may be null or empty
     * @return boolean value if param empty or null, returns false
     */
    public static boolean isParameterPassed(String param) {
        return StringUtils.isNoneBlank(param);
    }

    /**
     * checks if set of names empty or equals null
     *
     * @param names set to check, may be null or empty
     * @return boolean value if names empty or null, returns false
     */
    public static boolean isTagNamesPassed(Set<String> names) {
        return names != null && names.size() > 0;
    }
}
