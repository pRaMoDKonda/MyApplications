package pramod.com.mystickynotes.realm;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import pramod.com.mystickynotes.model.StickyNote;

/**
 * Created by ipspl on 22/3/17.
 */

public class RealmManipulator {

    private static RealmManipulator dbManager;
    private static Realm realm;
    private String realmName = "RealmStickyNote";

    private RealmManipulator(Context context) {
        if (realm == null) {
            Realm.init(context);
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name(realmName)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(configuration);
            Log.e("DB","Realm Object is Created");
        }
    }

    public static RealmManipulator getRealmInstance(Context context) {

        if (dbManager == null) {
            dbManager = new RealmManipulator(context);
            Log.e("DB","DBManager Object is Created");
        }

        return dbManager;
    }

    public void addOrUpdateRealmList(StickyNote note) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(note);
        realm.commitTransaction();
        Log.e("DB","Sticky Note Added in Realm");
    }

    public RealmResults<StickyNote> getAllStickyNotes() {
        realm.beginTransaction();
        RealmResults<StickyNote> realmNotes = realm.where(StickyNote.class).findAll();
        realm.commitTransaction();

        Log.e("DB","Realm Data Fetched");
        return realmNotes;
    }

    public void updateStickyNote(StickyNote note) {
        StickyNote stickyNote = realm.where(StickyNote.class).equalTo("id",note.getId()).findFirst();
        realm.beginTransaction();
//        stickyNote.setId(note.getId());
        stickyNote.setNoteTitle(note.getNoteTitle());
        stickyNote.setNoteContent(note.getNoteContent());
        realm.commitTransaction();

        Log.e("DB","Realm Data Updated");
    }

    public void deleteStickyNote(StickyNote note) {
        realm.beginTransaction();
        note.deleteFromRealm();
        realm.commitTransaction();

        Log.e("DB","Realm Data Deleted");
    }

}
