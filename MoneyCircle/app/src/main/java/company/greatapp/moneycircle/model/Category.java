package company.greatapp.moneycircle.model;

import android.content.ContentValues;

/**
 * Created by gyanendra.sirohi on 7/1/2015.
 */
public class Category extends Model  {
    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryDesciption() {
        return categoryDesciption;
    }

    public void setCategoryDesciption(String categoryDesciption) {
        this.categoryDesciption = categoryDesciption;
    }

    public int getSuperType() {
        return superType;
    }

    public void setSuperType(int superType) {
        this.superType = superType;
    }

    String categoryType;
    String categoryDesciption;
    int superType;

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
