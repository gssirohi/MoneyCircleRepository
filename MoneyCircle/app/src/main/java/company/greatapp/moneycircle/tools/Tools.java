package company.greatapp.moneycircle.tools;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.manager.BorrowManager;
import company.greatapp.moneycircle.manager.CategoryManager;
import company.greatapp.moneycircle.manager.ExpenseManager;
import company.greatapp.moneycircle.manager.IncomeManager;
import company.greatapp.moneycircle.manager.LentManager;
import company.greatapp.moneycircle.manager.SplitManager;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Expense;
import company.greatapp.moneycircle.model.Income;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Split;
import company.greatapp.moneycircle.view.TagItemView;

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

    public static String getUidFromCursor(Cursor cursor) {
        if (cursor == null) return null;
        String uid = cursor.getString(cursor.getColumnIndex(DB.UID));
        Log.d("SPLIT","UID for this cursor is : "+uid);
        return uid;
    }

    public static Model getDbInstance(Context context,Model model) {
        if(context == null || model == null) return null;
        return getDbInstance(context,model.getUID(),model.getModelType());
    }

    public static Model getDbInstance(Context context,String uid,int modelType) {
        if(context == null || TextUtils.isEmpty(uid)) return null;
        uid = uid.replaceAll("NEW","DB");
        Model model = null;
        String [] projection = null;
        String selection=DB.UID + "=" + uid;
        String [] selArgs = null;
        Uri tableUri = null;

        if (modelType == Model.MODEL_TYPE_INCOME) {
            projection = DB.INCOME_TABLE_PROJECTION;
            tableUri = DB.INCOME_TABLE_URI;
        } else if (modelType == Model.MODEL_TYPE_EXPENSE) {
            projection = DB.EXPENSE_TABLE_PROJECTION;
            tableUri = DB.EXPENSE_TABLE_URI;
        } else if (modelType == Model.MODEL_TYPE_BORROW) {
            projection = DB.BORROW_TABLE_PROJECTION;
            tableUri = DB.BORROW_TABLE_URI;
        } else if (modelType == Model.MODEL_TYPE_LENT) {
            projection = DB.LENT_TABLE_PROJECTION;
            tableUri = DB.LENT_TABLE_URI;
        }
        else if (modelType == Model.MODEL_TYPE_SPLIT) {
            projection = DB.SPLIT_TABLE_PROJECTION;
            tableUri = DB.SPLIT_TABLE_URI;
        } else if (modelType == Model.MODEL_TYPE_CATEGORY) {
            projection = DB.CATEGORY_TABLE_PROJECTION;
            tableUri = DB.CATEGORY_TABLE_URI;            
        }

        Cursor c = context.getContentResolver().query(tableUri, projection, selection, selArgs, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            model =  createLightModelFromCursor(c,modelType);
            c.close();
        }
        return model;
    }

    public static Model createLightModelFromCursor(Cursor cursor, int modelType) {
        Model model= null;
        switch (modelType) {
            case Model.MODEL_TYPE_INCOME:
                model = IncomeManager.createLightItemFromCursor(cursor);
                break;
            case Model.MODEL_TYPE_EXPENSE:
                model = ExpenseManager.createLightItemFromCursor(cursor);
                break;
            case Model.MODEL_TYPE_BORROW:
                model = BorrowManager.createLightItemFromCursor(cursor);
                break;
            case Model.MODEL_TYPE_LENT:
                model = LentManager.createLightItemFromCursor(cursor);
                break;
            case Model.MODEL_TYPE_SPLIT:
                model = SplitManager.createLightItemFromCursor(cursor);
                break;
            case Model.MODEL_TYPE_CATEGORY:
                model = CategoryManager.createLightItemFromCursor(cursor);
                break;
            default:
                Log.d("SPLIT", "modelType not found");
        }
        return model;
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
//
//        for(String s: splits){
//            Split item = new Split();
//            item.setTitle(s);
//            item.setDateString(getRandomDate());
//            item.setCategory(splitCategoryList.get(randInt(0, splitCategoryList.size()-1)).getUID());
//            item.setAmount(randInt(500, 10000));
//            item.insertItemInDB(context);
//        }

    }
    
    private void demoMethod(int modelType) {
        switch (modelType) {
            case Model.MODEL_TYPE_INCOME:

                break;
            case Model.MODEL_TYPE_EXPENSE:

                break;
            case Model.MODEL_TYPE_BORROW:

                break;
            case Model.MODEL_TYPE_LENT:

                break;
            case Model.MODEL_TYPE_SPLIT:

                break;
            case Model.MODEL_TYPE_CATEGORY:
                break;
            default:
                Log.d("SPLIT", "modelType not found");
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

    public static int getModelColor(Context context, int modelType) {
        Resources res = context.getResources();
        int color = 0;
        if (modelType == Model.MODEL_TYPE_INCOME) {
            color = res.getColor(R.color.income);
        } else if (modelType == Model.MODEL_TYPE_EXPENSE) {
            color = res.getColor(R.color.expense);
        } else if (modelType == Model.MODEL_TYPE_BORROW) {
            color = res.getColor(R.color.borrow);
        } else if (modelType == Model.MODEL_TYPE_LENT) {
            color = res.getColor(R.color.lent);
        }
        else if (modelType == Model.MODEL_TYPE_SPLIT) {
            color = res.getColor(R.color.split);
        }
        return color;
    }
}
