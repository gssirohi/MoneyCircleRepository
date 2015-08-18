package company.greatapp.moneycircle.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Split;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.GreatJSON;

/**
 * Created by gyanendra.sirohi on 7/15/2015.
 */
public class SplitManager extends BaseModelManager {
    private final ContactManager contactManager;
    private final CircleManager circleManager;
    private final ExpenseManager expenseManager;
    private final LentManager lentManager;
    Context context;
    ArrayList<Model> splits = new ArrayList<Model>();
    ArrayList<String> titles = new ArrayList<String>();

    public ArrayList<String> getTitles() {
        return this.titles;
    }

    public SplitManager(Context context){
        this.context = context;
        contactManager = new ContactManager(context);
        circleManager = new CircleManager(context);
        expenseManager = new ExpenseManager(context);
        lentManager = new LentManager(context);

        loadItemsFromDB();
    }


    @Override
    public Model createItemFromCursor(Cursor cursor) {
        if(cursor == null) return null;

        int dbId               =cursor.getInt(cursor.getColumnIndex(DB.DB_ID));
        String uid             = cursor.getString(cursor.getColumnIndex(DB.UID));
        String title           = cursor.getString(cursor.getColumnIndex(DB.TITLE));
        String category           = cursor.getString(cursor.getColumnIndex(DB.CATEGORY));
        int amount             = cursor.getInt(cursor.getColumnIndex(DB.AMOUNT));
        String description     = cursor.getString(cursor.getColumnIndex(DB.DESCRIPTION));
        String dueDateString   = cursor.getString(cursor.getColumnIndex(DB.DUE_DATE_STRING));

        int participantsCount = cursor.getInt(cursor.getColumnIndex(DB.SPLIT_TOTAL_PARTICIPANTS));

        String linkedContactsJson     = cursor.getString(cursor.getColumnIndex(DB.SPLIT_LINKED_CONTACTS_JSON));
        String linkedCircleJson     = cursor.getString(cursor.getColumnIndex(DB.SPLIT_LINKED_CIRCLE_JSON));
        String linkedParticipantsJson     = cursor.getString(cursor.getColumnIndex(DB.SPLIT_LINKED_PARTICIPANTS_JSON));

        String linkedExpenseJson     = cursor.getString(cursor.getColumnIndex(DB.SPLIT_LINKED_EXPENSE_JSON));
        String linkedLentsJson     = cursor.getString(cursor.getColumnIndex(DB.SPLIT_LINKED_LENTS_JSON));

        String json_string     = cursor.getString(cursor.getColumnIndex(DB.JSON_STRING));
        String date_string     = cursor.getString(cursor.getColumnIndex(DB.DATE_STRING));
        int date               = cursor.getInt(cursor.getColumnIndex(DB.DATE));
        int dom                = cursor.getInt(cursor.getColumnIndex(DB.DAY_OF_MONTH));
        int wom                = cursor.getInt(cursor.getColumnIndex(DB.WEEK_OF_MONTH));
        int m                  = cursor.getInt(cursor.getColumnIndex(DB.MONTH));
        int y                  = cursor.getInt(cursor.getColumnIndex(DB.YEAR));


        Split split =new Split();
        split.setDbId(dbId);
        split.setUID(uid);
        split.setTitle(title);
        split.setCategory(category);
        split.setAmount(amount);
        split.setDescription(description);
        split.setDueDateString(dueDateString);

        split.setTotalParticipants(participantsCount);

        split.setLinkedParticipantsJson(linkedParticipantsJson);
        split.setLinkedParticipants(GreatJSON.getContactListFromJsonString(linkedParticipantsJson, contactManager));
        split.setLinkedContactsJson(linkedContactsJson);
        split.setLinkedContacts(GreatJSON.getContactListFromJsonString(linkedContactsJson, contactManager));
        split.setLinkedCircleJson(linkedCircleJson);
        split.setLinkedCircle(GreatJSON.getCircleFromJsonString(linkedCircleJson, circleManager));
        split.setLinkedExpenseJson(linkedExpenseJson);
        split.setLinkedExpense(GreatJSON.getExpenseFromJsonString(linkedExpenseJson, expenseManager));
        split.setLinkedLentsJson(linkedLentsJson);
        split.setLinkedLents(GreatJSON.getLentListFromJsonString(linkedLentsJson,lentManager));

        split.setDateString(date_string);
        split.setJsonString(json_string);
        return split;
    }

    @Override
    public Model createItemFromIntent(Intent intent) {
        return null;
    }

    @Override
    protected void loadItemsFromDB() {
        splits.clear();
        titles.clear();
        Cursor c = context.getContentResolver().query(DB.SPLIT_TABLE_URI,
                DB.SPLIT_TABLE_PROJECTION, null, null, null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                Model model = createItemFromCursor(c);
                splits.add(model);
                titles.add(model.getTitle());
                c.moveToNext();
            }
            c.close();
        }}

    @Override
    protected Context getContext() {
        return context;
    }

    @Override
    protected Uri getTableUri() {
        return DB.SPLIT_TABLE_URI;
    }

    @Override
    protected int getModelType() {
        return Model.MODEL_TYPE_SPLIT;
    }

    @Override
    public ArrayList<Model> getItemList() {
        return this.splits;
    }

}
