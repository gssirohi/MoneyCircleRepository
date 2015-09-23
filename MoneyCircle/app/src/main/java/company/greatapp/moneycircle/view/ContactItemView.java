package company.greatapp.moneycircle.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.ContactDetailActivity;
import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.DB;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra on 19/9/15.
 */
public class ContactItemView extends LinearLayout {
    private LinearLayout ll_contact_frame;
    private Button b_invite;
    private TextView tv_number;
    private TextView tv_name;
    private ImageView iv_contact;
    private ViewGroup viewGroup;
    private Contact mContact;


    public ContactItemView(Context context, AttributeSet attrs, Contact contact) {
        super(context, attrs);
        mContact = contact;

        Log.d("Spilt", "called super");
        setOrientation(LinearLayout.VERTICAL);
        // setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.contact_item_layout, this, true);
        iv_contact = (ImageView)viewGroup.findViewById(R.id.iv_contact);
        tv_name = (TextView)viewGroup.findViewById(R.id.tv_contact_name);
        tv_number = (TextView)viewGroup.findViewById(R.id.tv_contact_number);
        b_invite = (Button)viewGroup.findViewById(R.id.b_contact_invite);
        ll_contact_frame = (LinearLayout)viewGroup.findViewById(R.id.ll_contact_name_number_frame);
        b_invite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleInviteButtonClicked();
            }
        });
        ll_contact_frame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFrameClicked();
            }
        });
        initView(mContact);
    }

    private void handleFrameClicked() {

        Intent intent = new Intent(getContext(),ContactDetailActivity.class);
        intent.putExtra(DB.UID,mContact.getUID());
        getContext().startActivity(intent);
    }

    private void handleInviteButtonClicked() {

    }

    public void initView(Contact contact) {
        mContact = contact;

        if(mContact.isRegistered()) {
            iv_contact.setImageResource(Tools.getContactAvatorResId(mContact.getGender()));
            b_invite.setVisibility(View.GONE);
        } else {
            //use normal avator independent of gender
            b_invite.setVisibility(View.VISIBLE);
        }

        tv_name.setText(mContact.getContactName());
        tv_number.setText(mContact.getPhone());

    }
}
