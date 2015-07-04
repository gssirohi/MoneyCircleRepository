package company.greatapp.moneycircle.model;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Circle {
    String name;
    String memberCount;
    ArrayList<Contact> memberList;
    JSONObject jsonMembers;
}
