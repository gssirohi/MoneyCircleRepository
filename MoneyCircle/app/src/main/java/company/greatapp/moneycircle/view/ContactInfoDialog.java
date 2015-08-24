package company.greatapp.moneycircle.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.model.Contact;

/**
 * Created by Gyanendrasingh on 8/24/2015.
 */
public class ContactInfoDialog extends Dialog {

    private final Contact mContact;
    private final Context mContext;

    public ContactInfoDialog(Context context, Contact contact) {
        super(context);
        init(context, contact);
        mContact = contact;
        mContext = context;
    }

    public void init(Context context, Contact contact) {
        //Toast.makeText(this,"callback : "+model.getTitle()+"["+model.getModelType()+"]",Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.contact_info_dialog_layout, null, false);
        ImageView contact_image = (ImageView)viewGroup.findViewById(R.id.iv_dialog_contact_Image);
        contact_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleContactImageClicked();
            }
        });
        //Dialog dialog = new Dialog(context);
        if(C.CONTACT_INFO_DIALOG_TRANSPARENT) {
            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        //dialog.setContentView(viewGroup);
        setContentView(viewGroup);
        TextView tv_name = (TextView)viewGroup.findViewById(R.id.tv_contact_name);
        TextView tv_value = (TextView)viewGroup.findViewById(R.id.tv_contact_money_value);

        float value = contact.getLentAmountToThis() - contact.getBorrowedAmountfromThis();
        float value_negative = contact.getBorrowedAmountfromThis() - contact.getLentAmountToThis();
        String msg = "";
        if(value > 0) {
            msg = "owes you "+value;
            tv_value.setTextColor(context.getResources().getColor(R.color.text_info));
        } else if(value < 0) {
            tv_value.setTextColor(context.getResources().getColor(R.color.text_error));
            msg = "you owe "+value_negative;
        } else {
            tv_value.setTextColor(context.getResources().getColor(R.color.text_basic_light));
            msg = "settled";
        }
        tv_value.setText(msg);
        tv_name.setText(contact.getContactName());
        //dialog.setTitle("Contact Cash Flow");
        setTitle("Contact Cash Flow");
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(C.CONTACT_INFO_DIALOG_TRANSPARENT) {
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
//        dialog.show();
    }

    private void handleContactImageClicked() {
        Toast.makeText(mContext,"Start Contact Detail Activity for "+mContact.getContactName(),Toast.LENGTH_SHORT).show();
    }
}
