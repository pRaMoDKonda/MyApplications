package pramod.com.mystickynotes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import pramod.com.mystickynotes.R;

public class NewListStickNoteActivity extends AppCompatActivity {

    @BindView(R.id.et_list_content)
    EditText etListContent;

    @BindView(R.id.txt_list_note)
    CheckBox txtListNote;

    @BindView(R.id.btn_add_list_note)
    Button btnAddListNote;

    @BindView(R.id.check_list_data)
    ListView listView;

    ArrayList<String> checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_stick_note);

        ButterKnife.bind(this);

        checkBoxes = new ArrayList<>();

        /*btnAddListNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtListNote.setChecked(false);
                txtListNote.setText(etListContent.getText());
            }
        });*/

        etListContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.d("TAG","Enter clicked");
                    return true;
                }

                return false;
            }
        });

        etListContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.d("TAG", "enter_key_called");
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btn_add_list_note)
    void onClick() {
        txtListNote.setVisibility(View.VISIBLE);
        txtListNote.setChecked(false);
        txtListNote.setText(etListContent.getText());

        checkBoxes.add(etListContent.getText().toString());
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_multiple_choice,checkBoxes);

        listView.setAdapter(adapter);
        etListContent.setText("");
    }

}
