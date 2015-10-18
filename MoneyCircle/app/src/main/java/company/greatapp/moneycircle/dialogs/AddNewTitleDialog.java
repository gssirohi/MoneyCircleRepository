package company.greatapp.moneycircle.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.constants.C;

/**
 * Created by Gyanendrasingh on 8/24/2015.
 */
public class AddNewTitleDialog extends Dialog {

    TitleAddedListner mTitleAddedListner;
    private final Context mContext;
    private TextView tv_title;
    private EditText et_title;
    private Button b_add;

    public AddNewTitleDialog(Context context, TitleAddedListner listner) {
        super(context);
        init(context);
        mTitleAddedListner = listner;
        mContext = context;
    }

    public void init(Context context) {
        //Toast.makeText(this,"callback : "+model.getTitle()+"["+model.getModelType()+"]",Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.add_new_title_dialog_layout, null, false);
        tv_title = (TextView)viewGroup.findViewById(R.id.tv_add_new_title);
        et_title = (EditText)viewGroup.findViewById(R.id.et_add_new_title);
        b_add = (Button)viewGroup.findViewById(R.id.b_add_title);
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddButtonClick();
            }
        });
        //Dialog dialog = new Dialog(context);
        if(C.CONTACT_INFO_DIALOG_TRANSPARENT) {
            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        //dialog.setContentView(viewGroup);
        setContentView(viewGroup);

        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    private void handleAddButtonClick() {
        String title = et_title.getText().toString();
        mTitleAddedListner.onTitleAdded(title);
        et_title.setText("");
        this.dismiss();
    }

    public void setTitleAddedListner(TitleAddedListner listner) {
        mTitleAddedListner = listner;
    }

    public void setDialogTitle(String s) {
        tv_title.setText(s);
    }

    public void setButtonText(String s) {
        b_add.setText(s);
    }
    public interface TitleAddedListner {
        public void onTitleAdded(String title);

    }
}
