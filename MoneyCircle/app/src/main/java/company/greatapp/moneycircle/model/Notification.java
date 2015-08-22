package company.greatapp.moneycircle.model;

import android.content.ContentValues;
import android.net.Uri;

import company.greatapp.moneycircle.constants.DB;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Notification extends Model {

    @Override
    protected Uri getTableUri() {
        return DB.NOTIFICATION_TABLE_URI;
    }
    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setModelType(int modelType) {

    }

    @Override
    public void setDbId(int dbId) {

    }

    @Override
    public void setUID(String uid) {

    }

    @Override
    public void setJsonString(String jsonString) {

    }

    @Override
    public void setAmount(float amount) {

    }

    @Override
    public void setDateString(String dateString) {

    }

    @Override
    public void setDueDateString(String dueDateString) {

    }

    @Override
    public void setLinkedContact(Contact contact) {

    }

    @Override
    public void setCategory(String categoryUid) {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getModelType() {
        return 0;
    }

    @Override
    public String getUID() {
        return null;
    }

    @Override
    public int getDbId() {
        return 0;
    }

    @Override
    public String getJsonString() {
        return null;
    }

    @Override
    public float getAmount() {
        return 0;
    }

    @Override
    public String getDateString() {
        return null;
    }

    @Override
    public String getDueDateString() {
        return null;
    }

    @Override
    public Contact getLinkedContact() {
        return null;
    }

    @Override
    public String getCategory() {
        return null;
    }

    @Override
    public ContentValues getContentValues() {
        return null;
    }
}
