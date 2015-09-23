package company.greatapp.moneycircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.manager.Transporter;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.Model;
import company.greatapp.moneycircle.tools.Tools;
import company.greatapp.moneycircle.view.CircleItemView;

public class ContactDetailActivity extends AppCompatActivity {

    private ImageView iv_contact_image;
    private TextView tv_contact_name;
    private TextView tv_contact_number;
    private TextView tv_contact_message;
    private CircleItemView civ_lent;
    private CircleItemView civ_borrow;
    private CircleItemView civ_balance;
    private Button b_settle_up;
    private String contactUid;
    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactUid = getIntent().getStringExtra(DB.UID);
        mContact = (Contact)Tools.getDbInstance(this,contactUid, Model.MODEL_TYPE_CONTACT);

        setContentView(R.layout.activity_contact_detail);

        iv_contact_image = (ImageView)findViewById(R.id.iv_contact_detail);
        tv_contact_name = (TextView)findViewById(R.id.tv_contact_detail_name);
        tv_contact_number = (TextView)findViewById(R.id.tv_contact_detail_number);
        tv_contact_message = (TextView)findViewById(R.id.tv_contact_detail_message);

        civ_lent = (CircleItemView)findViewById(R.id.civ_contact_detail_lent);
        civ_borrow = (CircleItemView)findViewById(R.id.civ_contact_detail_borrow);
        civ_balance = (CircleItemView)findViewById(R.id.civ_contact_detail_balance);

        b_settle_up = (Button)findViewById(R.id.b_contact_detail_settle_up);

        b_settle_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSettleUpClicked();
            }
        });
        initView(mContact);
    }

    private void handleSettleUpClicked() {
        Transporter transporter = new Transporter(this);
        transporter.transportSettleUpRequest(mContact);
    }

    private void initView(Contact contact) {
        if(contact == null) return ;

        mContact = contact;

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
        civ_lent.setItemName("You lent to him");
        civ_lent.setItemValue("" + Tools.floatString(lent));
        civ_borrow.setItemName("You borrowed from him");
        civ_borrow.setItemValue("" + Tools.floatString(borrow));

        civ_balance.setItemName(title);
        civ_balance.setItemValue(balanceAmount);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
