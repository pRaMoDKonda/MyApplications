package pramod.com.mystickynotes.realm;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import pramod.com.mystickynotes.model.StickyListNote;
import pramod.com.mystickynotes.model.StickyNote;

/**
 * Created by ipspl on 22/3/17.
 */

public class RealmNoteManipulator {

    private static RealmNoteManipulator dbManager;
    private static Realm realm;
    private String realmName = "RealmStickyNote";

    private RealmNoteManipulator(Context context) {
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

    public static RealmNoteManipulator getRealmNoteInstance(Context context) {

        if (dbManager == null) {
            dbManager = new RealmNoteManipulator(context);
            Log.e("DB","DBManager Object is Created");
        }

        return dbManager;
    }

    public void addOrUpdateRealmNote(StickyNote note) {
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
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(note);
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
