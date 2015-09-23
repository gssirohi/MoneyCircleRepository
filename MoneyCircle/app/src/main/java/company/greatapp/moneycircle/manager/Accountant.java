package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import company.greatapp.moneycircle.asynctask.UpdateAccountRegistersTask;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.constants.States;
import company.greatapp.moneycircle.model.AccountRegister;
import company.greatapp.moneycircle.model.Borrow;
import company.greatapp.moneycircle.model.Category;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Lent;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.model.Split;
import company.greatapp.moneycircle.tools.DateUtils;
import company.greatapp.moneycircle.tools.GreatJSON;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by Gyanendrasingh on 8/21/2015.
 */
public class Accountant {

    private Context mContext;
    AccountRegister mIncomeRegister = new AccountRegister(Model.MODEL_TYPE_INCOME);
    AccountRegister mExpenseRegister = new AccountRegister(Model.MODEL_TYPE_EXPENSE);
    AccountRegister mBorrowRegister = new AccountRegister(Model.MODEL_TYPE_BORROW);
    AccountRegister mLentRegister = new AccountRegister(Model.MODEL_TYPE_LENT);
    AccountRegister mSplitRegister = new AccountRegister(Model.MODEL_TYPE_SPLIT);

    float budget = 18000;


    private Accountant() {//constructor blocked
    }


    public Accountant(Context context, boolean withLoadedRegisters) {
        mContext = context;
        if (withLoadedRegisters) {
            loadAllRegistersFromDB();
        }
    }

    public Accountant(Context context, Cursor cursor) {
        mContext = context;
        loadAllRegistersFromCursor(cursor);
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    private void loadAllRegistersFromDB() {
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(DB.ACCOUNT_TABLE_URI,
                    DB.ACCOUNT_TABLE_PROJECTION, null, null, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    AccountRegister register = loadRegisterFromCursor(c);
                    setRegister(register);
                    c.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    private void loadAllRegistersFromCursor(Cursor c) {
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    AccountRegister register = loadRegisterFromCursor(c);
                    setRegister(register);
                    c.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public AccountRegister getRegister(int registerType) {
        AccountRegister register = null;
        switch (registerType) {
            case Model.MODEL_TYPE_INCOME:
                register = mIncomeRegister;
                break;
            case Model.MODEL_TYPE_EXPENSE:
                register =  mExpenseRegister;
                break;
            case Model.MODEL_TYPE_BORROW:
                register =  mBorrowRegister;
                break;
            case Model.MODEL_TYPE_LENT:
                register =  mLentRegister;
                break;
            case Model.MODEL_TYPE_SPLIT:
                register =  mSplitRegister;
                break;
        }
        if(register != null) {
            register.printRegister();
        } else {
            Log.e("SPLIT","Register is NULL");
        }
        return register;
    }

    private void setRegister(AccountRegister register) {
        switch (register.getRegisterType()) {
            case Model.MODEL_TYPE_INCOME:
                mIncomeRegister = register;
                break;
            case Model.MODEL_TYPE_EXPENSE:
                mExpenseRegister = register;
                break;
            case Model.MODEL_TYPE_BORROW:
                mBorrowRegister = register;
                break;
            case Model.MODEL_TYPE_LENT:
                mLentRegister = register;
                break;
            case Model.MODEL_TYPE_SPLIT:
                mSplitRegister = register;
                break;
        }
    }

    public AccountRegister loadRegisterFromDB(int registerType) {
        Cursor c = null;
        AccountRegister register = null;
        String selection = DB.ACCOUNT_REGISTER_TYPE + "=" + registerType;
        try {
            c = mContext.getContentResolver().query(DB.ACCOUNT_TABLE_URI,
                    DB.ACCOUNT_TABLE_PROJECTION, selection, null, null);
            if (c != null && c.getCount() > 0) {
                register = loadRegisterFromCursor(c);
                setRegister(register);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return register;
    }

    public AccountRegister loadRegisterFromCursor(Cursor cursor) {
        if (cursor == null) return null;

        int registerType = cursor.getInt(cursor.getColumnIndex(DB.ACCOUNT_REGISTER_TYPE));
        Log.i("SPLIT","Loading register of type "+registerType);

        float curr_day = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOTAL_CURRENT_DAY)));
        float curr_week = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOTAL_CURRENT_WEEK)));
        float curr_month = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOTAL_CURRENT_MONTH)));
        float curr_year = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOTAL_CURRENT_YEAR)));

        float last_day = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOTAL_LAST_DAY)));
        float last_week = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOTAL_LAST_WEEK)));
        float last_month = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOTAL_LAST_MONTH)));
        float last_year = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOTAL_LAST_YEAR)));

        float total = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOTAL)));

        String jsonUpcomingsDay = cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_UPCOMINGS_DAY));
        String jsonUpcomingsWeek = cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_UPCOMINGS_WEEK));
        String jsonUpcomingsMonth = cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_UPCOMINGS_MONTH));

        String jsonTopItems = cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_TOPITEMS));

        ArrayList<Model> upcomingDays = new ArrayList<Model>();
        ArrayList<Model> upcomingWeek = new ArrayList<Model>();
        ArrayList<Model> upcomingMonth = new ArrayList<Model>();

        if(!TextUtils.isEmpty(jsonUpcomingsDay)) {
            upcomingDays = GreatJSON.getModelListFromJsonString(jsonUpcomingsDay, mContext, registerType);
        }

        if(!TextUtils.isEmpty(jsonUpcomingsWeek)) {
            upcomingWeek = GreatJSON.getModelListFromJsonString(jsonUpcomingsWeek, mContext, registerType);
        }

        if(!TextUtils.isEmpty(jsonUpcomingsMonth)) {
            upcomingMonth = GreatJSON.getModelListFromJsonString(jsonUpcomingsMonth, mContext, registerType);
        }


        int modelType = Model.MODEL_TYPE_CATEGORY;
        switch (registerType) {
            case Model.MODEL_TYPE_INCOME:

                break;
            case Model.MODEL_TYPE_EXPENSE:
                modelType = Model.MODEL_TYPE_CATEGORY;
                break;
            case Model.MODEL_TYPE_BORROW:
                modelType = Model.MODEL_TYPE_CONTACT;
                break;
            case Model.MODEL_TYPE_LENT:
                modelType = Model.MODEL_TYPE_CONTACT;
                break;
            case Model.MODEL_TYPE_SPLIT:

                break;
            default:
                Log.d("SPLIT", "registerType not found while loding from cursor");
        }
        ArrayList<Model> topItems = GreatJSON.getModelListFromJsonString(jsonTopItems, mContext, modelType);

        String jsonLastTransaction = cursor.getString(cursor.getColumnIndex(DB.ACCOUNT_LAST_TRANSACTION));

        AccountRegister register = new AccountRegister(registerType);

        register.setTotalOfCurrentDay(curr_day);
        register.setTotalOfCurrentWeek(curr_week);
        register.setTotalOfCurrentMonth(curr_month);
        register.setTotalOfCurrentYear(curr_year);

        register.setTotalOfLastDay(last_day);
        register.setTotalOfLastWeek(last_week);
        register.setTotalOflastMonth(last_month);
        register.setTotalOfLastYear(last_year);

        register.setTotalTillNow(total);

        register.setUpComingEventsOfToday(upcomingDays);
        register.setUpComingEventsOfWeek(upcomingWeek);
        register.setUpComingEventsOfMonth(upcomingMonth);

        register.setTopItems(topItems);

        register.setLastTransaction(GreatJSON.getModelFromJsonString(jsonLastTransaction,mContext,registerType));
        setRegister(register);

        return register;
    }

    public void updateAllRegistersInDb(Intent updateIntent) {
        String last_json = updateIntent.getStringExtra(UpdateAccountRegistersTask.LAST_TRANSACTION_JSON);
        int transaction_model = updateIntent.getIntExtra(UpdateAccountRegistersTask.TRANSACTION_TYPE,0);
        setLastTransaction(last_json,transaction_model);
        updateRegisterInDb(Model.MODEL_TYPE_INCOME);
        updateRegisterInDb(Model.MODEL_TYPE_EXPENSE);
        updateRegisterInDb(Model.MODEL_TYPE_BORROW);
        updateRegisterInDb(Model.MODEL_TYPE_LENT);
        updateRegisterInDb(Model.MODEL_TYPE_SPLIT);
    }

    private void setLastTransaction(String last_json, int transaction_model) {
        switch (transaction_model) {
            case Model.MODEL_TYPE_INCOME:
                mIncomeRegister.setLastTransaction(GreatJSON.getModelFromJsonString(last_json,mContext,Model.MODEL_TYPE_INCOME));
                break;
            case Model.MODEL_TYPE_EXPENSE:
                mExpenseRegister.setLastTransaction(GreatJSON.getModelFromJsonString(last_json,mContext,Model.MODEL_TYPE_EXPENSE));
                break;
            case Model.MODEL_TYPE_BORROW:
                mBorrowRegister.setLastTransaction(GreatJSON.getModelFromJsonString(last_json,mContext,Model.MODEL_TYPE_BORROW));
                break;
            case Model.MODEL_TYPE_LENT:
                mLentRegister.setLastTransaction(GreatJSON.getModelFromJsonString(last_json,mContext,Model.MODEL_TYPE_LENT));
                break;
            case Model.MODEL_TYPE_SPLIT:
                Split split = (Split)GreatJSON.getModelFromJsonString(last_json, mContext, Model.MODEL_TYPE_SPLIT);
                if(split != null) {
                    mSplitRegister.setLastTransaction(split);
                    Model lastExpense = GreatJSON.getModelFromJsonString(split.getLinkedExpenseJson(), mContext, Model.MODEL_TYPE_EXPENSE);
                    ArrayList<Model> lentList = GreatJSON.getModelListFromJsonString(split.getLinkedLentsJson(), mContext, Model.MODEL_TYPE_LENT);
                    if(lastExpense != null) {
                        mExpenseRegister.setLastTransaction(lastExpense);
                    }
                    if(lentList != null && lentList.size() > 0) {
                        //add only last item of newly created lent list
                        mLentRegister.setLastTransaction(lentList.get(lentList.size()-1));
                    }
                }
                break;
            default:
                Log.d("SPLIT", "transaction_model not found");
        }
    }

    private Model getLastTransaction(int transaction_model) {
        Model last = null;
        switch (transaction_model) {
            case Model.MODEL_TYPE_INCOME:
                last = mIncomeRegister.getLastTransaction();
                break;
            case Model.MODEL_TYPE_EXPENSE:
                last = mExpenseRegister.getLastTransaction();
                break;
            case Model.MODEL_TYPE_BORROW:
                last = mBorrowRegister.getLastTransaction();
                break;
            case Model.MODEL_TYPE_LENT:
                last = mLentRegister.getLastTransaction();
                break;
            case Model.MODEL_TYPE_SPLIT:
                last = mSplitRegister.getLastTransaction();
                break;
            default:
                Log.d("SPLIT", "transaction_model not found");
        }
        return last;
    }

    public void updateRegisterInDb(int registerType) {
        AccountRegister register = null;
        switch (registerType) {
            case Model.MODEL_TYPE_INCOME:
                IncomeManager im = new IncomeManager(mContext);
                ArrayList<Model> incomes = im.getItemList();
                register = writeNewRegister(registerType, incomes);
                register.updateItemInDb(mContext);
                break;
            case Model.MODEL_TYPE_EXPENSE:
                ExpenseManager em = new ExpenseManager(mContext);
                ArrayList<Model> expenses = em.getItemList();
                register = writeNewRegister(registerType, expenses);
                register.updateItemInDb(mContext);

                break;
            case Model.MODEL_TYPE_BORROW:
                BorrowManager bm = new BorrowManager(mContext);
                ArrayList<Model> borrows = bm.getItemList();
                register = writeNewRegister(registerType, borrows);
                register.updateItemInDb(mContext);

                break;
            case Model.MODEL_TYPE_LENT:
                LentManager lm = new LentManager(mContext);
                ArrayList<Model> lents = lm.getItemList();
                register = writeNewRegister(registerType, lents);
                register.updateItemInDb(mContext);

                break;
            case Model.MODEL_TYPE_SPLIT:
                SplitManager sm = new SplitManager(mContext);
                ArrayList<Model> splits = sm.getItemList();
                register = writeNewRegister(registerType, splits);
                register.updateItemInDb(mContext);

                break;
            default:
                Log.d("SPLIT", "registerType not found");
        }
    }

    private AccountRegister writeNewRegister(int registerType, ArrayList<Model> models) {
        Log.i("SPLIT","WRITING REGISTER --------------------> "+registerType);
        if(models != null) {
            Log.i("SPLIT","TOTAL ITEM TO BE CHECKED : "+models.size());
        } else {
            Log.i("SPLIT","Models are NULL");
        }
        AccountRegister register = new AccountRegister(registerType);
        float totalOfCurrentDay = 0;
        float totalOfCurrentWeek = 0;
        float totalOfCurrentMonth = 0;
        float totalOfCurrentYear = 0;

        float totalOfLastDay = 0;
        float totalOfLastWeek = 0;
        float totalOflastMonth = 0;
        float totalOfLastYear = 0;

        float totalTillNow = 0;

        ArrayList<Model> upComingEventsOfToday = new ArrayList<Model>();
        ArrayList<Model> upComingEventsOfWeek = new ArrayList<Model>();
        ArrayList<Model> upComingEventsOfMonth = new ArrayList<Model>();

        ArrayList<Model> topItems = new ArrayList<Model>();


        String date = "";
        String dueDate = "";
        float amount;
        for (Model model : models) {
            if(model == null) {
                Log.d("SPLIT","While writing for register, Model is NULL");
                continue;
            } else {
                if(registerType == Model.MODEL_TYPE_BORROW ) {
                    Borrow borrow = (Borrow)model;
                    if(borrow.getState() == States.BORROW_PAYMENT_CLEARED) {
                        continue;
                    }
                } else if(registerType == Model.MODEL_TYPE_LENT ) {
                    Lent lent  = (Lent)model;
                    if(lent.getState() == States.LENT_AMOUNT_RECEIVED) {
                        continue;
                    }
                }
                Log.d("SPLIT","Writing from MODEL[type = "+registerType+"]["+model.getTitle()+"]: "+model.getUID());
            }
            amount = model.getAmount();
            date = model.getDateString();
            Log.d("SPLIT","---------GETTING AMOUNT----------");
            totalTillNow = totalTillNow + amount;

            if (DateUtils.isCurrDate(date)) {
                totalOfCurrentDay = totalOfCurrentDay + amount;
            }
            if (DateUtils.isCurrWeek(date)) {
                totalOfCurrentWeek = totalOfCurrentWeek + amount;
            }
            if (DateUtils.isCurrMonth(date)) {
                totalOfCurrentMonth = totalOfCurrentMonth + amount;
            }
            if (DateUtils.isCurrYear(date)) {
                totalOfCurrentYear = totalOfCurrentYear + amount;
            }

            if (DateUtils.isLastDate(date)) {
                totalOfLastDay = totalOfLastDay + amount;
            }
            if (DateUtils.isLastWeek(date)) {
                totalOfLastWeek = totalOfLastWeek + amount;

            }
            if (DateUtils.isLastMonth(date)) {
                totalOflastMonth = totalOflastMonth + amount;

            }
            if (DateUtils.isLastYear(date)) {
                totalOfLastYear = totalOfLastYear + amount;
            }

            if (registerType == Model.MODEL_TYPE_BORROW || registerType == Model.MODEL_TYPE_LENT
                    || registerType == Model.MODEL_TYPE_SPLIT) {
                Log.d("SPLIT","---------GETTING UPCOMINGS----------");
                dueDate = model.getDueDateString();
                if (DateUtils.isLiesInNextSevenDays(dueDate)) {
                    upComingEventsOfToday.add(model);
                }
                if (DateUtils.isLiesInNextFifteenDays(dueDate) && !DateUtils.isPassed(dueDate)) {
                    upComingEventsOfWeek.add(model);
                }
                if (DateUtils.isLiesInNextThirtyDays(dueDate) && !DateUtils.isPassed(dueDate)) {
                    upComingEventsOfMonth.add(model);
                }
            }

            if (registerType == Model.MODEL_TYPE_BORROW || registerType == Model.MODEL_TYPE_LENT
                    || registerType == Model.MODEL_TYPE_EXPENSE) {

                if (registerType == Model.MODEL_TYPE_BORROW) {
                    Log.d("SPLIT","---------GETTING TOP BORROW ITEMS----------");
                    Contact contact = model.getLinkedContact();
                    if (contact != null && contact.getBorrowedAmountfromThis() > 0) {
                         topItems.add(contact);
                    }
                } else if (registerType == Model.MODEL_TYPE_LENT) {
                    Log.d("SPLIT","---------GETTING TOP LENT ITEMS----------");
                    Contact contact = model.getLinkedContact();
                    if (contact != null && contact.getLentAmountToThis() > 0) {
                        topItems.add(contact);
                    }
                } else if (registerType == Model.MODEL_TYPE_EXPENSE) {
                    Log.d("SPLIT","--------- GETTING TOP SPEND AREAS ----------");
                    Category category = (Category) Tools.getDbInstance(mContext, model.getCategory(), Model.MODEL_TYPE_CATEGORY);
                    if (category != null && category.getSpentAmountOnThis() > 0) {
                        topItems.add(category);
                    }
                }
            }

        }
        topItems = getArrangedTopItems(topItems, registerType);

        register.setTotalOfCurrentDay(totalOfCurrentDay);
        register.setTotalOfCurrentWeek(totalOfCurrentWeek);
        register.setTotalOfCurrentMonth(totalOfCurrentMonth);
        register.setTotalOfCurrentYear(totalOfCurrentYear);

        register.setTotalOfLastDay(totalOfLastDay);
        register.setTotalOfLastWeek(totalOfLastWeek);
        register.setTotalOflastMonth(totalOflastMonth);
        register.setTotalOfLastYear(totalOfLastYear);

        register.setTotalTillNow(totalTillNow);

        register.setUpComingEventsOfToday(upComingEventsOfToday);
        register.setUpComingEventsOfWeek(upComingEventsOfWeek);
        register.setUpComingEventsOfMonth(upComingEventsOfMonth);

        register.setTopItems(topItems);
        Model lastTransaction = getLastTransaction(registerType);
        if(lastTransaction != null) {
            register.setLastTransaction(lastTransaction);
        }

        setRegister(register);
        Log.d("SPLIT"," <-----------------REGISTER WRITTEN :--------------------> ");
        register.printRegister();
        return register;
    }

    private boolean has(ArrayList<Model> list, String uid) {
        boolean has = false;
        for(Model model :  list) {
            if(model.getUID().equals(uid)){
                return true;
            }
        }
        return has;
    }

    private ArrayList<Model> getArrangedTopItems(ArrayList<Model> list, int registerType) {

        Contact c1;
        Contact c2;
        Category ct1;
        Category ct2;
        if(list == null ) return null;

        ArrayList<Model> arrangedList = new ArrayList<Model>();
        ArrayList<Model> sortedList = new ArrayList<Model>();

        //remove duplicate items
        for(Model model : list) {
            if(!has(arrangedList,model.getUID())) {
                arrangedList.add(model);
                sortedList.add(model);
            }
        }

        //perform sorting based on register type
        switch(registerType) {
                    case Model.MODEL_TYPE_BORROW:

                        for(int i = 0; i<arrangedList.size();i++) {
                            c1 = (Contact)arrangedList.get(i);
                            for(int j = i+1; j<arrangedList.size();j++) {
                               c2  =  (Contact)arrangedList.get(j);
                               if(c1.getBorrowedAmountfromThis() < c2.getBorrowedAmountfromThis()) {
                                   arrangedList.set(i, c2);
                                   arrangedList.set(j,c1);
                                   c1 = (Contact)arrangedList.get(i);
                                   c2 = (Contact)arrangedList.get(j);
                               }
                           }
                        }
                        break;
                    case Model.MODEL_TYPE_LENT:

                        for(int i = 0; i<arrangedList.size();i++) {
                            c1 = (Contact)arrangedList.get(i);
                            for(int j = i+1; j<arrangedList.size();j++) {
                                c2  =  (Contact)arrangedList.get(j);
                                if(c1.getLentAmountToThis() < c2.getLentAmountToThis()) {
                                    arrangedList.set(i, c2);
                                    arrangedList.set(j,c1);
                                    c1 = (Contact)arrangedList.get(i);
                                    c2 = (Contact)arrangedList.get(j);
                                }
                            }
                        }                        break;
                    case Model.MODEL_TYPE_EXPENSE:

                        for(int i = 0; i<arrangedList.size();i++) {
                            ct1 = (Category)arrangedList.get(i);
                            for(int j = i+1; j<arrangedList.size();j++) {
                                ct2  =  (Category)arrangedList.get(j);
                                if(ct1.getSpentAmountOnThis() < ct2.getSpentAmountOnThis()) {
                                    arrangedList.set(i, ct2);
                                    arrangedList.set(j,ct1);
                                    ct1 = (Category)arrangedList.get(i);
                                    ct2 = (Category)arrangedList.get(j);
                                }
                            }
                        }
                        break;
                }

         return arrangedList;
    }

    public void initializeDb() {
        AccountRegister iRegister = new AccountRegister(Model.MODEL_TYPE_INCOME);
        AccountRegister eRegister = new AccountRegister(Model.MODEL_TYPE_EXPENSE);
        AccountRegister bRegister = new AccountRegister(Model.MODEL_TYPE_BORROW);
        AccountRegister lRegister = new AccountRegister(Model.MODEL_TYPE_LENT);
        AccountRegister sRegister = new AccountRegister(Model.MODEL_TYPE_SPLIT);

        iRegister.insertItemInDB(mContext);
        eRegister.insertItemInDB(mContext);
        bRegister.insertItemInDB(mContext);
        lRegister.insertItemInDB(mContext);
        sRegister.insertItemInDB(mContext);
    }

    public static void performSettleUpWithContact(Context context, Contact contact) {
        //this should be done in accountant service
        LentManager lentManager = new LentManager(context);
        ArrayList<Model> items = lentManager.getItemList();
        Lent lent;
        Contact linkedContact;
        for(Model model : items) {
            lent = (Lent)model;
            linkedContact = lent.getLinkedContact();
            if(linkedContact.getPhone() == contact.getPhone()) {
                lent.setState(States.LENT_AMOUNT_RECEIVED);
                lent.updateItemInDb(context);
            }
        }

        Borrow borrow;
        BorrowManager borrowManager = new BorrowManager(context);
        items = borrowManager.getItemList();
        for(Model model : items) {
            borrow = (Borrow)model;
            linkedContact = borrow.getLinkedContact();
            if(linkedContact.getPhone() == contact.getPhone()) {
                borrow.setState(States.BORROW_PAYMENT_CLEARED);
                borrow.updateItemInDb(context);
            }
        }

        contact.setBorrowedAmountfromThis(0);
        contact.setLentAmountToThis(0);
        contact.setState(States.CONTACT_IDEAL);
        contact.updateItemInDb(context);
    }
}
