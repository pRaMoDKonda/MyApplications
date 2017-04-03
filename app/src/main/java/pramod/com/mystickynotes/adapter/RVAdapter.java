package pramod.com.mystickynotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pramod.com.mystickynotes.R;
import pramod.com.mystickynotes.activity.NewStickyNoteActivity;
import pramod.com.mystickynotes.model.StickyNote;
import pramod.com.mystickynotes.realm.RealmManipulator;

/**
 * Created by ipspl on 22/3/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DataViewHolder> {

    List<StickyNote> stickyNotesList;
    Context context;

    public RVAdapter(List<StickyNote> stickyNotesList, Context context) {
        this.stickyNotesList = stickyNotesList;
        this.context = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticky_note_row,parent,false);
        DataViewHolder dataViewHolder = new DataViewHolder(view);
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.textTitle.setText(stickyNotesList.get(position).getNoteTitle());
        holder.textContent.setText(stickyNotesList.get(position).getNoteContent());
    }

    @Override
    public int getItemCount() {
        return stickyNotesList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        ImageView noteIcon;
        ImageButton update, delete;
        TextView textTitle, textContent;

        public DataViewHolder(View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.txt_title);
            textContent = (TextView) itemView.findViewById(R.id.txt_content);

            update = (ImageButton) itemView.findViewById(R.id.update);
            delete = (ImageButton) itemView.findViewById(R.id.delete);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StickyNote stickyNote = stickyNotesList.get(getAdapterPosition());
                    Intent i = new Intent(context, NewStickyNoteActivity.class);
                    i.putExtra("StickyNoteData", stickyNote);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    notifyDataSetChanged();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StickyNote stickyNote = stickyNotesList.get(getAdapterPosition());

                    final StickyNote stickyNoteBackup = new StickyNote(stickyNote.getId(), stickyNote.getNoteTitle(), stickyNote.getNoteContent());

                    RealmManipulator.getRealmInstance(context).deleteStickyNote(stickyNote);

                    notifyDataSetChanged();

                    Snackbar snackbar = Snackbar.make(v, "Note is Deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO DELETE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    RealmManipulator.getRealmInstance(context).updateStickyNote(stickyNoteBackup);

                                    notifyDataSetChanged();

                                    Snackbar snackbar1 = Snackbar.make(view, "Note is Restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });

                    snackbar.show();
                }

            });
        }
    }
}
