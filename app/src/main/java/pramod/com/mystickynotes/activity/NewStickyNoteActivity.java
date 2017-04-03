package pramod.com.mystickynotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.RealmResults;
import pramod.com.mystickynotes.R;
import pramod.com.mystickynotes.model.StickyNote;
import pramod.com.mystickynotes.realm.RealmManipulator;

public class NewStickyNoteActivity extends AppCompatActivity {

    EditText etTitle, etContent;
    Button btnSave;

    StickyNote stickyNote;

    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sticky_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);

        btnSave = (Button) findViewById(R.id.btn_save);

        final StickyNote parcelableStickyNote = getIntent().getParcelableExtra("StickyNoteData");
        if(parcelableStickyNote != null) {
            etTitle.setText(parcelableStickyNote.getNoteTitle());
            etContent.setText(parcelableStickyNote.getNoteContent());
            etTitle.setSelection(etTitle.getText().length());
            btnSave.setText("Update Note");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                if(title.equals("") && content.equals("")){
                    Snackbar.make(v, "Plese Enter Title and Content", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {

                    if(btnSave.getText().equals("Update Note")){
                        parcelableStickyNote.setNoteTitle(etTitle.getText().toString());
                        parcelableStickyNote.setNoteContent(etContent.getText().toString());
                        RealmManipulator.getRealmInstance(getApplicationContext()).updateStickyNote(parcelableStickyNote);
                        finish();
                    }
                    else {

                        RealmResults<StickyNote> realmNotes = RealmManipulator.getRealmInstance(getApplicationContext()).getAllStickyNotes();
                        if (realmNotes.size() != 0)
                            id = realmNotes.last().getId();

                        stickyNote = new StickyNote();
                        stickyNote.setId(id + 1);
                        stickyNote.setNoteTitle(etTitle.getText().toString());
                        stickyNote.setNoteContent(etContent.getText().toString());

                        RealmManipulator.getRealmInstance(getApplicationContext()).addOrUpdateRealmList(stickyNote);

                        Snackbar.make(v, "Your Note Save Successfully..!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    /*Intent i = new Intent(getApplicationContext(),StickyHome.class);
                    startActivity(i);*/
                        finish();
                    }
                }
            }
        });

        etContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Toast.makeText(getApplicationContext(), etContent.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (actionId == KeyEvent.KEYCODE_ENTER)) {
                    Toast.makeText(getApplicationContext(), etContent.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                // Respond to the action bar's Up/Home button
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            }
        return super.onOptionsItemSelected(item);

    }
}
