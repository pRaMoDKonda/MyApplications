package pramod.com.mystickynotes.realm;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import pramod.com.mystickynotes.model.StickyListNote;

/**
 * Created by ipspl on 4/4/17.
 */

public class RealmListNoteManipulator {

    private static RealmListNoteManipulator dbManager;
    private static Realm realm;
    private String realmName = "RealmStickyListNote";

    private RealmListNoteManipulator(Context context) {
        if (realm == null) {
            Realm.init(context);
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name(realmName)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(configuration);
            Log.e("DB","RealmList Object is Created");
        }
    }

    public static RealmListNoteManipulator getRealmListNoteInstance(Context context) {

        if (dbManager == null) {
            dbManager = new RealmListNoteManipulator(context);
            Log.e("DB","DBManager of List Object is Created");
        }

        return dbManager;
    }

    public void addOrUpdateRealmList(StickyListNote listNote) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(listNote);
        realm.commitTransaction();
        Log.e("DB","Sticky List Note Added in Realm");
    }

    public RealmResults<StickyListNote> getAllStickyListNotes() {
        realm.beginTransaction();
        RealmResults<StickyListNote> realmListNotes = realm.where(StickyListNote.class).findAll();
        realm.commitTransaction();

        Log.e("DB","Realm List Data Fetched");
        return realmListNotes;
    }
}
