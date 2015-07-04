package company.greatapp.moneycircle.model;

import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra.sirohi on 6/29/2015.
 */
public class Model {
    String uid;
    String title;
    public Model() {
        uid = Tools.generateUniqueId();
    }

    public String getUID() {
        return this.uid;
    }

}
