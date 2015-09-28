package company.greatapp.moneycircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.model.User;
import company.greatapp.moneycircle.tools.Tools;

/**
 * Created by gyanendra on 19/9/15.
 */
public class DrawerView extends LinearLayout {


    private final ViewGroup drawer;
    private final Context context;
    private final ViewGroup header;
    private final ImageView profile_pic;
    private final TextView name;
    private final TextView email;
    private final TextView phone;
    private final CircleItemView civ_account;

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        Log.d("Spilt", "called super");
        setOrientation(LinearLayout.VERTICAL);
        // setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        drawer = (ViewGroup) inflater.inflate(R.layout.drawer, this, true);
        header = (ViewGroup) drawer.findViewById(R.id.header_drawer);
        profile_pic = (ImageView)header.findViewById(R.id.iv_drawer_header_profile_pic);
        name = (TextView)header.findViewById(R.id.tv_drawer_header_name);
        email = (TextView)header.findViewById(R.id.tv_drawer_header_email);
        phone = (TextView)header.findViewById(R.id.tv_drawer_header_phone);

        civ_account = (CircleItemView)findViewById(R.id.civ_drawer_account);
        initView();
    }


    public void initView() {
        User user = new User(context);
        setName(user.getName());
        setPhone(user.getPhoneNumber());
        setEmail(user.getEmail());
        setProfilePic(Tools.getContactAvatorResId(user.getGender()));

        setAccountBalance(""+55000);
    }

    private void setAccountBalance(String balance)
    {
        civ_account.setItemName("ACCOUNT BALANCE");
        civ_account.setItemValue(balance);
    }
    private void setProfilePic(int contactAvatorResId) {
        this.profile_pic.setImageResource(contactAvatorResId);
    }

    private void setEmail(String email) {
        this.email.setText(email);
    }

    private void setPhone(String phoneNumber) {
        this.phone.setText(phoneNumber);
    }

    private void setName(String name) {
        this.name.setText(name);
    }
}
