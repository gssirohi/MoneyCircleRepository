package company.greatapp.moneycircle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import company.greatapp.moneycircle.adapters.ManageCircleMemberListAdapter;
import company.greatapp.moneycircle.chooser.ChooserActivity;
import company.greatapp.moneycircle.constants.C;
import company.greatapp.moneycircle.manager.CircleManager;
import company.greatapp.moneycircle.manager.ContactManager;
import company.greatapp.moneycircle.model.Circle;
import company.greatapp.moneycircle.model.Contact;
import company.greatapp.moneycircle.tools.GreatJSON;

/**
 * Created by Prateek on 12-07-2015.
 */
public class ManageCircleActivity extends AppCompatActivity {

    ArrayList<Contact> mRegisteredContactList = new ArrayList<>();

    private EditText mEdNewCircleName;
    private TextView mTvCircleName;

    private ListView mMemberListView;
    private TextView mTvNoMember;

    private ManageCircleMemberListAdapter mAdapter = null;

    private String mCircleName;
    private ContactManager mContactManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_circles);

        /*mRegisteredContactList.add(new Contact("Prateek"));
        mRegisteredContactList.add(new Contact("Gyanendra"));
        mRegisteredContactList.add(new Contact("Ashish"));
        mRegisteredContactList.add(new Contact("kshitij"));*/
        /*mRegisteredContactList.add(new Contact("Gandhi"));
        mRegisteredContactList.add(new Contact("Pandey"));
        mRegisteredContactList.add(new Contact("Ajay"));
        mRegisteredContactList.add(new Contact("Amol"));
        mRegisteredContactList.add(new Contact("Nitish"));
        mRegisteredContactList.add(new Contact("Uday"));*/

        mCircleName = getIntent().getStringExtra(C.NEW_CIRCLE_NAME);
        if (mCircleName == null) {
            createCircleNameEntryDialog();
            mCircleName = "New Circle";
        }

        mContactManager = new ContactManager(this);

        mTvCircleName = (TextView) findViewById(R.id.tvManageCircleNameId);
        mTvCircleName.setText(mCircleName);

        mTvNoMember = (TextView) findViewById(R.id.tv_noMemberId);
        mMemberListView = (ListView) findViewById(R.id.lvMemberListId);

        if (mRegisteredContactList.size() <= 0) {
            mTvNoMember.setVisibility(View.VISIBLE);
            return;
        }

        mMemberListView.setVisibility(View.VISIBLE);
        mAdapter = new ManageCircleMemberListAdapter(this, mRegisteredContactList);
        mMemberListView.setAdapter(mAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == C.TAG_REGISTERED_CONTACTS) {
        if (requestCode == C.TAG_CONTACTS && data != null) {
            ArrayList<String> contactUIDList = data.getStringArrayListExtra("uids");
            addContactsToMemberList(contactUIDList);
        }
    }

    public void onClickAddNewMember(View view) {
        Log.d("Prateek", "onClickAddNewMember");
        Intent i = new Intent(this, ChooserActivity.class);
        int requestCode = C.TAG_CONTACTS;
//        int requestCode = C.TAG_REGISTERED_CONTACTS;

        i.putExtra(C.CHOOSER_REQUEST, requestCode);
        i.putExtra(C.CHOOSER_CHOICE_MODE, ListView.CHOICE_MODE_MULTIPLE);
        startActivityForResult(i, requestCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_save_circle_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_save_circle_info) {
            saveCircle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickEditCircleName(View view) {
        RelativeLayout llCircleName = (RelativeLayout) findViewById(R.id.rlManageCircleNameId);
        llCircleName.setVisibility(View.GONE);

        EditText edCircleName = (EditText) findViewById(R.id.edManageCircleNameId);
        edCircleName.setText(mCircleName);
        edCircleName.setVisibility(View.VISIBLE);
    }

    public void onClickDeleteCircleMember(View view) {
        int position = (Integer)view.getTag(R.string.circle_member_id);
        if (position >= 0 || position < mRegisteredContactList.size()) {
            mRegisteredContactList.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void createCircleNameEntryDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.circle_name);

        mEdNewCircleName = new EditText(this);
        dialogBuilder.setMessage(R.string.enter_new_circle_name);

        dialogBuilder.setView(mEdNewCircleName);
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton(R.string.action_create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Do nothing here because we override this button later to change the close behaviour.
                //However, we still need this because on older versions of Android unless we
                //pass a handler the button doesn't get instantiated
            }
        });
        dialogBuilder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        // Overriding the dismissing of dialog on Click of Button.
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCircleName = mEdNewCircleName.getText().toString();
                if (!TextUtils.isEmpty(newCircleName)) {
                    mCircleName = newCircleName;
                    mTvCircleName.setText(mCircleName);
                    dialog.dismiss();
                } else {
                    Toast.makeText(ManageCircleActivity.this, "Enter Circle Name", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addContactsToMemberList(ArrayList<String> contactUIDList) {

        if (contactUIDList.size() == 0) {
            return;
        }

        Contact contact = null;
        for (String contactUID : contactUIDList) {

            contact = (Contact) mContactManager.getItemFromListByUID(contactUID);
            mRegisteredContactList.add(contact);
        }

        if (mMemberListView.getVisibility() != View.VISIBLE) {
            Log.d("Prateek", "mMemberListView is not visible");
            mTvNoMember.setVisibility(View.GONE);
            mMemberListView.setVisibility(View.VISIBLE);
            mAdapter = new ManageCircleMemberListAdapter(this, mRegisteredContactList);
            mMemberListView.setAdapter(mAdapter);
        }

        mAdapter.notifyDataSetChanged();

    }

    public void saveCircle() {
        if (mRegisteredContactList.size() > 0) {

            EditText edCircleName = (EditText) findViewById(R.id.edManageCircleNameId);
            if (edCircleName.getVisibility() == View.VISIBLE) {
                mCircleName = edCircleName.getText().toString();
            }
            Circle circle = new Circle(mCircleName);
            circle.setContactsJson(GreatJSON.getJsonArrayForContactList(mRegisteredContactList).toString());

            CircleManager circleManager = new CircleManager(this, mContactManager);
            circleManager.insertItemInDB(circle);
            finish();
        } else {
            createAlertDialogEmptyCircle();
        }
    }

    public void createAlertDialogEmptyCircle() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Empty Circle");
        dialogBuilder.setMessage("Circle with No members will not be created.\nDo you want to continue");

        dialogBuilder.setPositiveButton(R.string.action_continue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        dialogBuilder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

//        AlertDialog dialog = dialogBuilder.create();
        dialogBuilder.show();
    }
}
