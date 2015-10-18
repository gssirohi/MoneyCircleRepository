package company.greatapp.moneycircle.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import company.greatapp.moneycircle.R;
import company.greatapp.moneycircle.adapters.TitleSelectionAdapter;


/**
 * Created by root on 26/9/15.
 */
public class TitleSelectionDialog extends Dialog {

    private boolean isConfirmationRequired;
    private ListView list;
    private String titleList[];
    private Context context;
    private RadioButton radioBtnSelected;
    private int selectedPos = -1;
    private TextView txtTitle;
    private TitleSelectionAdapter adapter;
    private Button cancelButton;
    private Button confirmButton;
    private TitleSelectedListener titleSelectListener;
    private String selectedTitleName;
    private boolean isHeaderUpdateEnabled;
    private boolean isSelectionChanged;
    private String headerTitle = "Title";
    private boolean isCancelButtonRequired;

    public TitleSelectionDialog(Context context, String titleList[], String header) {
        super(context);
        this.context = context;
        this.titleList = titleList;
        selectedPos = -1;
        headerTitle = header;
        this.isConfirmationRequired = true;
    }

    public TitleSelectionDialog(Context context, String titleList[], int initialSelectionIndex, String header) {
        super(context);
        this.context = context;
        this.titleList = titleList;
        this.headerTitle = header;
        this.isConfirmationRequired = true;
        this.selectedPos = initialSelectionIndex;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.title_selection_dialog_layout);


        txtTitle = (TextView)findViewById(R.id.text_title);
        txtTitle.setText(headerTitle);
        confirmButton = (Button)findViewById(R.id.button_confirm);
        cancelButton = (Button)findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleSelectionCancel();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmTitleSelection();
            }
        });

        confirmButton.setVisibility(View.GONE);
        if(isCancelButtonRequired) {
            cancelButton.setVisibility(View.VISIBLE);
        } else {
            cancelButton.setVisibility(View.GONE);
        }


        list = (ListView) findViewById(R.id.listView_in_passenger_title);
        initAdapter();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 RadioButton radio =  (RadioButton) view.findViewById(R.id.radioButton);
                 selectView(radio, i);

            }
        });


    }

    private void initAdapter() {

        adapter = null;
        adapter = new TitleSelectionAdapter(context, TitleSelectionDialog.this, titleList);
        if(selectedPos >= 0) {
            adapter.selectItemAtIndex(selectedPos);
            selectedTitleName = titleList[selectedPos];
        }

        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setItemsCanFocus(true);
        adapter.notifyDataSetChanged();

    }

    public void setConfirmationRequired(boolean isRequired) {
        this.isConfirmationRequired =  isRequired;
    }

    public void enableHeaderUpdateOnSelection(boolean enable) {
        this.isHeaderUpdateEnabled = enable;
    }
    private void titleSelectionCancel() {
        dismiss();
    }


    private void confirmTitleSelection() {
        if(titleSelectListener != null) {
            titleSelectListener.onTitleSelected(selectedTitleName,selectedPos);
            dismiss();
        }

    }

    public void setTitleSelectListener(TitleSelectedListener listener) {
        this.titleSelectListener = listener;
    }

    public void selectView(RadioButton radioButton, int selectedPos) {
        if (radioBtnSelected != null) {
            radioBtnSelected.setChecked(false);
        }
        if(this.selectedPos != selectedPos) {
            isSelectionChanged = true;
        }
        this.radioBtnSelected = radioButton;
        this.selectedPos = selectedPos;
        radioBtnSelected.setChecked(true);
        selectedTitleName = titleList[selectedPos];

        adapter.selectItemAtIndex(selectedPos);

        if(isHeaderUpdateEnabled) {
            txtTitle.setText(selectedTitleName);
        }


        if(!isConfirmationRequired) {
            confirmTitleSelection();
        } else {
            if(isSelectionChanged) {
                confirmButton.setVisibility(View.VISIBLE);
            }
        }

    }

    public void setSelection(int selectedIndex) {
        this.selectedPos = selectedIndex;
        if(adapter != null) {
            initAdapter();
            adapter.selectItemAtIndex(selectedIndex);
           // selectView((RadioButton)list.getChildAt(selectedIndex),selectedIndex);
            list.invalidate();
        }
    }

    public void setDialogHeaderTitle(String header) {
        this.headerTitle = header;
        if(txtTitle != null) {
            txtTitle.setText(header);
        }
    }

    public void setCancelButtonRequired(boolean isDialogCancelButtonRequired) {
        this.isCancelButtonRequired = isDialogCancelButtonRequired;
    }

    public interface TitleSelectedListener {

        public void onTitleSelected(String title, int index);
    }
}
