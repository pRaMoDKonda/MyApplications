package pramod.com.mystickynotes.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import pramod.com.mystickynotes.R;
import pramod.com.mystickynotes.adapter.RVListRowAdapter;
import pramod.com.mystickynotes.model.StickyListNote;
import pramod.com.mystickynotes.realm.RealmListNoteManipulator;
import pramod.com.mystickynotes.realm.RealmString;

public class NewListStickNoteActivity extends AppCompatActivity {

    @BindView(R.id.et_list_content)
    EditText etListContent;

    @BindView(R.id.et_list_title)
    EditText etListTite;

    @BindView(R.id.btn_add_list_note)
    Button btnAddListNote;

    @BindView(R.id.recyclerView_list)
    RecyclerView listView;

    RealmList<RealmString> checkBoxes;

    RealmList<RealmString> listNote;

    StickyListNote stickyListNote;

    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_stick_note);

        ButterKnife.bind(this);

        checkBoxes = new RealmList<>();
        listNote = new RealmList<>();

        etListContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onClick();
                etListContent.requestFocus();
                return true;
            }
        });
    }

    @OnClick(R.id.btn_add_list_note)
    void onClick() {
        if (!etListContent.getText().equals("")) {

            RealmString realmString = new RealmString();
            realmString.setString(etListContent.getText().toString());

            checkBoxes.add(realmString);

            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            listView.setHasFixedSize(true);
            listView.setLayoutManager(llm);

            RVListRowAdapter rowAdapter = new RVListRowAdapter(checkBoxes, getApplicationContext());

            listView.setAdapter(rowAdapter);
            etListContent.setText("");
        }
    }

    @OnClick(R.id.btn_list_save)
    void addListNote(View v) {
        String title = etListTite.getText().toString();

        listNote.addAll(checkBoxes);

        if (title.equals("") && listNote.size() == 0) {
            Snackbar.make(v, "Plese Enter Title and Task", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        else {

            RealmResults<StickyListNote> realmNotes = RealmListNoteManipulator
                    .getRealmListNoteInstance(getApplicationContext())
                    .getAllStickyListNotes();

            if (realmNotes.size() != 0)
                id = realmNotes.last().getId();

            stickyListNote = new StickyListNote();
            stickyListNote.setId(id + 1);
            stickyListNote.setListNoteTitle(etListTite.getText().toString());
            stickyListNote.setListNote(listNote);

            RealmListNoteManipulator.getRealmListNoteInstance(getApplicationContext()).addOrUpdateRealmList(stickyListNote);

            Snackbar.make(v, "Your Note Save Successfully..!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            finish();
        }
    }

}
