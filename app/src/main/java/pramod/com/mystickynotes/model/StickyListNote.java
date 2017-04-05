package pramod.com.mystickynotes.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import pramod.com.mystickynotes.realm.RealmString;

/**
 * Created by ipspl on 4/4/17.
 */

public class StickyListNote extends RealmObject {

    @PrimaryKey
    private int id;
    private String listNoteTitle;
    private RealmList<RealmString> listNote;

    public StickyListNote() {
    }

    public StickyListNote(int id, String listNoteTitle, RealmList<RealmString> listNote) {
        this.id = id;
        this.listNoteTitle = listNoteTitle;
        this.listNote = listNote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListNoteTitle() {
        return listNoteTitle;
    }

    public void setListNoteTitle(String listNoteTitle) {
        this.listNoteTitle = listNoteTitle;
    }

    public RealmList<RealmString> getListNote() {
        return listNote;
    }

    public void setListNote(RealmList<RealmString> listNote) {
        this.listNote = listNote;
    }
}
