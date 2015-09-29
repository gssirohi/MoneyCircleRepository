package company.greatapp.moneycircle;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.constants.S;
import company.greatapp.moneycircle.constants.States;
import company.greatapp.moneycircle.dialogs.AddNewEntryDialog;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.manager.Transporter;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.InPackage;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.Tools;
import company.greatapp.moneycircle.view.CashFlowView;
import company.greatapp.moneycircle.view.CircleItemView;
import company.greatapp.moneycircle.view.ContactItemView;
import company.greatapp.moneycircle.view.NotificationItemView;



public class ContactDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int CONTACT_LODER_ID = 33;
    private static final int NOTIFICATION_LODER_ID = 34;
    private ImageView iv_contact_image;
    private TextView tv_contact_name;
    private TextView tv_contact_number;
    private TextView tv_contact_message;




    private Button b_settle_up;
    private String contactUid;
    private Contact mContact;

    private LinearLayout ll_notification_frame;
    private InPackage mInPackage;
    private FloatingActionButton fb_add_entry;
    private Toolbar toolbar;
    private CashFlowView cfv;
    private ImageView iv_share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactUid = getIntent().getStringExtra(DB.UID);
        mContact = (Contact)Tools.getDbInstance(this, contactUid, Model.MODEL_TYPE_CONTACT);

        setContentView(R.layout.activity_contact_detail);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        iv_contact_image = (ImageView)findViewById(R.id.iv_contact_detail);
        tv_contact_name = (TextView)findViewById(R.id.tv_contact_detail_name);
        tv_contact_number = (TextView)findViewById(R.id.tv_contact_detail_number);
        tv_contact_message = (TextView)findViewById(R.id.tv_contact_detail_message);


        cfv = (CashFlowView)findViewById(R.id.cfv_contact_detail);

        b_settle_up = (Button)findViewById(R.id.b_contact_detail_settle_up);
        fb_add_entry = (FloatingActionButton)findViewById(R.id.fb_contact_detail_add_new_entry);

        fb_add_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNewEntryButton();
            }
        });
        ll_notification_frame = (LinearLayout)findViewById(R.id.ll_contact_detail_notification_frame);

        b_settle_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSettleUpClicked();
            }
        });

    }




    private void handleNewEntryButton() {
        AddNewEntryDialog dialog = new AddNewEntryDialog(this,mContact);
        dialog.showDialogWithAnimation();
    }


    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(CONTACT_LODER_ID, null, this);
        getLoaderManager().initLoader(NOTIFICATION_LODER_ID, null, this);
    }


    private void handleSettleUpClicked() {
        Transporter transporter = new Transporter(this);
        transporter.transportSettleUpRequest(mContact);
    }

    private void initView(Contact contact) {
        if(contact == null) return ;

        mContact = contact;
        b_settle_up.setVisibility(View.VISIBLE);
        if(mContact.isRegistered()) {
            iv_contact_image.setImageResource(Tools.getContactAvatorResId(mContact.getGender()));
        } else {
            //use default avator

        }

        String title = "";
        String balanceAmount = "";
        float borrow = mContact.getBorrowedAmountfromThis();
        float lent = mContact.getLentAmountToThis();

        float balance = lent - borrow;

        if(balance > 0) {
            title = mContact.getContactName()+" owes you";
            balanceAmount = ""+Tools.floatString(balance);
        } else if (balance < 0) {
            title = "You owe "+mContact.getContactName();
            balanceAmount = ""+Tools.floatString(borrow - lent);
        } else {
            title = "You both CHILL";
            balanceAmount = "SETTLED UP";
            b_settle_up.setVisibility(View.GONE);
        }

        tv_contact_name.setText(mContact.getContactName());
        tv_contact_number.setText(mContact.getPhone());

        cfv.setFirstItemTitle("You lent to him");
        cfv.setFirstItemValue(Tools.floatString(lent));

        cfv.setSecondItemTitle("You borrowed from him");
        cfv.setSecondItemValue(Tools.floatString(borrow));

        cfv.setResultTitle(title);
        cfv.setResultValue(balanceAmount);

        switch(mContact.getState()) {
            case States.CONTACT_IDEAL:
                //b_settle_up.setVisibility(View.GONE);
                //ll_agree_disagree_frame.setVisibility(View.GONE);
                break;
            case States.CONTACT_SETTLE_REQ_SENDING:
                //ll_agree_disagree_frame.setVisibility(View.GONE);
                b_settle_up.setVisibility(View.GONE);
                break;
            case States.CONTACT_SETTLE_REQ_NOT_SENT:
                //ll_agree_disagree_frame.setVisibility(View.GONE);
                b_settle_up.setVisibility(View.GONE);
                break;
            case States.CONTACT_SETTLE_REQ_APPROVAL_PENDING:
                //ll_agree_disagree_frame.setVisibility(View.VISIBLE);
                b_settle_up.setVisibility(View.GONE);
                break;
            case States.CONTACT_WAITING_FOR_SETTLE_APPROVAL:
                //ll_agree_disagree_frame.setVisibility(View.GONE);
                b_settle_up.setVisibility(View.GONE);
                break;
            case States.CONTACT_SETTLE_REQUEST_DISAPPROVED_ACTION_PENDING:
                //ll_agree_disagree_frame.setVisibility(View.GONE);
                b_settle_up.setVisibility(View.VISIBLE);
                b_settle_up.setText("REQUEST AGAIN");
                break;
        }
        setContactStateMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_contact_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }


        switch (id){



            case R.id.action_share:

               // Toast.makeText(this,"Menu shared Clicked",Toast.LENGTH_SHORT).show();
                handleShare();
                break;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleShare() {

       // Toast.makeText(this,"AAAAA",Toast.LENGTH_SHORT).show();

        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        waIntent.setAction("text/plain");

        String msg = "";
        msg = msg + "Hey,\n";
        waIntent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(waIntent, "Share via"));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if(id == CONTACT_LODER_ID) {
            String selection=DB.UID + "=" + "?";
            String [] selArgs = new String[]{""+contactUid};
            return new CursorLoader(this, DB.CONTACT_TABLE_URI,
                    DB.CONTACT_TABLE_PROJECTION, selection, selArgs,
                    "data DESC");
        } else if(id == NOTIFICATION_LODER_ID) {
            if(mContact != null) {
                String selection = DB.REQUEST_SENDER_PHONE + "=" + "?" + " AND " + DB.REQUEST_CODE
                        + "=" + "?" + " AND " + DB.RESPONSE_STATE + "=" + "?";
                String[] selArgs = new String[]{"" + mContact.getPhone(), "" + S.TRANSPORT_REQUEST_CODE_SETTLE,
                                                 ""+InPackage.RESPONSE_STATE_NOT_RESPONDED};
                return new CursorLoader(this, DB.PACKAGE_FROM_SERVER_TABLE_URI,
                        DB.PACKAGE_FROM_SERVER_TABLE_PROJECTION, selection, selArgs,
                        "data DESC");
            }

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(loader.getId() == CONTACT_LODER_ID) {
            if(data.getCount() == 1) {
                data.moveToFirst();
                mContact = (Contact)ContactManager.createLightItemFromCursor(data);
                initView(mContact);
            }
        } else if(loader.getId() == NOTIFICATION_LODER_ID) {
            if(data.getCount() >= 1) {
                data.moveToFirst();
                mInPackage = new InPackage(data);
                initNotificationView(mInPackage);
            } else if(data.getCount() == 0) {
                removeNotificationViews();
            }
        }
    }

    private void removeNotificationViews() {
        ll_notification_frame.removeAllViews();
    }

    private void initNotificationView(InPackage mInPackage) {
        ll_notification_frame.removeAllViews();
        NotificationItemView view = new NotificationItemView(this,null);
        view.initView(mInPackage);
        ll_notification_frame.addView(view);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setContactStateMessage() {
        if(mContact == null) return;
        String message = "";

        String title = "";
        String balanceAmount = "";
        float borrow = mContact.getBorrowedAmountfromThis();
        float lent = mContact.getLentAmountToThis();

        float balance = lent - borrow;

        if(balance > 0) {

        } else if (balance < 0) {

        } else {

        }

        switch(mContact.getState()) {
            case States.CONTACT_IDEAL:

                message = "No pending settle up request from "+mContact.getContactName();
                break;
            case States.CONTACT_SETTLE_REQ_SENDING:
                message = "Requesting for settle up .... ";
                break;
            case States.CONTACT_SETTLE_REQ_NOT_SENT:
                message = "Settle up request not sent .... ";
                break;
            case States.CONTACT_SETTLE_REQ_APPROVAL_PENDING:
                message = mContact.getContactName()+" has requested for Settle Up. If you agree,"
                        +"all lent and borrow items linked with this contact will be removed automatically!";
                break;
            case States.CONTACT_WAITING_FOR_SETTLE_APPROVAL:
                message ="Settle Up requested Successfully. Once "+mContact.getContactName()
                        +" accepts you request, all lent and borrow items linked with this contact will be removed automatically!" ;
                break;
            case States.CONTACT_SETTLE_REQUEST_DISAPPROVED_ACTION_PENDING:
                message =mContact.getContactName()+" has disapproved Settle Up request, You can request Again";
                break;
        }
        tv_contact_message.setText(message);
    }
}
