package company.greatapp.moneycircle.tools;

import java.util.UUID;

/**
 * Created by gyanendra.sirohi on 6/29/2015.
 */
public class Tools {

    public static String generateUniqueId() {
        String uid = UUID.randomUUID().toString();
        return uid;
    }
}
