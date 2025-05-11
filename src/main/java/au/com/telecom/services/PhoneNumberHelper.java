package au.com.telecom.services;

import au.com.telecom.entities.PhoneNumberEntity;
import java.util.List;

/**
 * This helper class will load the first defualt page size phonesNumbers
 * to the static list - entities.
 * @author kuladeep.
 */
public class PhoneNumberHelper {
    private static List<PhoneNumberEntity> entities;

    public static List<PhoneNumberEntity> getEntities() {
        return entities;
    }

    public static void setEntities(List<PhoneNumberEntity> entities) {
        PhoneNumberHelper.entities = entities;
    }
}
