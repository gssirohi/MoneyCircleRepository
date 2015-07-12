package company.greatapp.moneycircle.model;

import android.content.ContentValues;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Circle extends Model  {
    String name;

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Contact> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<Contact> memberList) {
        this.memberList = memberList;
    }

    public String getJsonMembers() {
        return jsonMembers;
    }

    public void setJsonMembers(String jsonMembers) {
        this.jsonMembers = jsonMembers;
    }

    String memberCount;
    ArrayList<Contact> memberList;
    String jsonMembers;

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
    public ContentValues getContentValues() {
        return null;
    }

    @Override
    public void printModelData() {

    }
}
