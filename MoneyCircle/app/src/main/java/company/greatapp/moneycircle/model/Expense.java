package company.greatapp.moneycircle.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Expense {
    int amount;
    Date date;
    Category category;
    String description;
    Uri bilUri;
    ArrayList<Circle> taggedCircles;
    ArrayList<Contact> taggedContacts;
}
