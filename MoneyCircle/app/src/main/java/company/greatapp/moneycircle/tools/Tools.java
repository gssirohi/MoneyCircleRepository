package company.greatapp.moneycircle.tools;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Split;

/**
 * Created by gyanendra.sirohi on 6/29/2015.
 */
public class Tools {

    public static String generateUniqueId() {
        String uid = UUID.randomUUID().toString();
        return "NEW-"+uid;
    }


    public static String getFormatedNumber(String number) {
        int length = number.length();
        if(length < 10) return null;
        String plain = "";
        int len = 0;
        for (int i = length-1; i >= 0 && len < 10;i--){
            char ch = number.charAt(i);
            if(TextUtils.isDigitsOnly("" + ch)){
                plain = ch+plain;
                len++;
            }
        }
        return plain;
    }

    public static String getSplitableString(ArrayList<String> list) {
        if(list == null) return null;
        String result = C.DIVIDER;
        for(String s : list) {
            result = result + s + C.DIVIDER;
        }
        return result;
    }

    public static void addDummyEntries(Context context, CategoryManager categoryManager) {
        String[] incomes = new String[]{"LGSI Salary","Gambling on Dewali",
                "Flat rent","Commety Interest","project freelancing","KBC Price"};
        String[] expenses= new String[]{
                "Bahubali Movie","Movie Bajrangi bhai jaan"," Food Panda","Dominos","Indigo Flight"
                ,"Relience Bill","Credit card payment","laptop emi","weekend beer"
        };
        String[] borrows= new String[]{"credit card bill","Food Coupan","New Friends Restaurent"," Central Mall shirt",
        "Weekend Trip"," Dominos Pizza","auto fare"};
        String[] lents= new String[]{"Auto fare BTM to LG","Flat rent","purchasing clothes","I dont know",
        "food","Top in Town shopping"};
        String[] splits= new String[]{"New Friends Party","Dont Tell Mama","Gilassy","KFC","McD treat","HRC treat",
        "Flat 102 party","Nandi Hill Trip"};

        ArrayList<Model> incomeCategoryList = null;
        ArrayList<Model> expenseCategoryList = null;
        ArrayList<Model> borrowCategoryList = null;
        ArrayList<Model> lentCategoryList = null;
        ArrayList<Model> splitCategoryList = null;

        if (categoryManager != null) {
            categoryManager.loadItemsFromDB();
            incomeCategoryList = categoryManager.getIncomeCategoryList();
            expenseCategoryList = categoryManager.getExpenseCategoryList();
            borrowCategoryList = categoryManager.getBorrowCategoryList();
            lentCategoryList = categoryManager.getLentCategoryList();
            splitCategoryList = categoryManager.getSplitCategoryList();
        }

        for(String s: incomes){
            Income item = new Income();
            item.setTitle(s);
            item.setDateString(getRandomDate());
            item.setCategory(incomeCategoryList.get(randInt(0, incomeCategoryList.size()-1)).getUID());
            item.setAmount(randInt(500, 10000));
            item.insertItemInDB(context);
        }
        for(String s: expenses){
            Expense item = new Expense();
            item.setTitle(s);
            item.setDateString(getRandomDate());
            item.setCategory(expenseCategoryList.get(randInt(0, expenseCategoryList.size()-1)).getUID());
            item.setAmount(randInt(500, 10000));
            item.insertItemInDB(context);
        }
        for(String s: borrows){
            Borrow item = new Borrow();
            item.setTitle(s);
            item.setDateString(getRandomDate());
            item.setCategory(borrowCategoryList.get(randInt(0, borrowCategoryList.size()-1)).getUID());
            item.setAmount(randInt(500, 10000));
            item.insertItemInDB(context);
        }
        for(String s: lents){
            Lent item = new Lent();
            item.setTitle(s);
            item.setDateString(getRandomDate());
            item.setCategory(lentCategoryList.get(randInt(0, lentCategoryList.size()-1)).getUID());
            item.setAmount(randInt(500, 10000));
            item.insertItemInDB(context);
        }
        for(String s: splits){
            Split item = new Split();
            item.setTitle(s);
            item.setDateString(getRandomDate());
            item.setCategory(randInt(0, 3));
            item.setAmount(randInt(500, 10000));
            item.insertItemInDB(context);
        }

    }


    public static String getRandomDate(){
        String date="";
        int day = randInt(1,25);//to avoid leap
        int month = randInt(1,12);
        int year = randInt(2013,2016);
        return DateUtils.getDateString(year,month,day);
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
public static String getModelName(int modelType){
    String name = "";
    if (modelType == Model.MODEL_TYPE_INCOME) {
        name = "Income";
    } else if (modelType == Model.MODEL_TYPE_EXPENSE) {
        name = "Expense";
    } else if (modelType == Model.MODEL_TYPE_BORROW) {
        name = "Borrow";
    } else if (modelType == Model.MODEL_TYPE_LENT) {
        name = "Lent";
    }
    else if (modelType == Model.MODEL_TYPE_SPLIT) {
        name = "Split";
    }
    return name;
}
}
