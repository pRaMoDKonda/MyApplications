package pramod.com.mystickynotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ipspl on 22/3/17.
 */

public class StickyNote extends RealmObject implements Parcelable {

    @PrimaryKey
    private int id;
    private String noteTitle, noteContent;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.noteTitle);
        dest.writeString(this.noteContent);
    }

    public StickyNote() {
    }

    protected StickyNote(Parcel in) {
        this.id = in.readInt();
        this.noteTitle = in.readString();
        this.noteContent = in.readString();
    }

    public static final Parcelable.Creator<StickyNote> CREATOR = new Parcelable.Creator<StickyNote>() {
        @Override
        public StickyNote createFromParcel(Parcel source) {
            return new StickyNote(source);
        }

        @Override
        public StickyNote[] newArray(int size) {
            return new StickyNote[size];
        }
    };
}
