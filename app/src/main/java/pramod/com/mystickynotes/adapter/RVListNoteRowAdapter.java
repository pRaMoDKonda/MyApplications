package pramod.com.mystickynotes.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import io.realm.RealmList;
import pramod.com.mystickynotes.R;
import pramod.com.mystickynotes.model.StickyListNote;
import pramod.com.mystickynotes.realm.RealmString;

/**
 * Created by ipspl on 4/4/17.
 */

public class RVListNoteRowAdapter extends RecyclerView.Adapter<RVListNoteRowAdapter.DataListViewHolder> {

    List<StickyListNote> stickyNotesList;
    Context context;

    public RVListNoteRowAdapter(List<StickyListNote> stickyNotesList, Context context) {
        this.stickyNotesList = stickyNotesList;
        this.context = context;
    }

    @Override
    public RVListNoteRowAdapter.DataListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticky_list_note_row,parent,false);
        RVListNoteRowAdapter.DataListViewHolder dataListViewHolder = new RVListNoteRowAdapter.DataListViewHolder(view);
        return dataListViewHolder;
    }

    @Override
    public void onBindViewHolder(RVListNoteRowAdapter.DataListViewHolder holder, int position) {
        holder.textTitle.setText(stickyNotesList.get(position).getListNoteTitle());

        RealmList<RealmString> realmListStrings = stickyNotesList.get(position).getListNote();
        RealmList<RealmString> checkBoxes = new RealmList<>();
        for (int i=0; i<realmListStrings.size(); i++){
            RealmString realmString = new RealmString();
            realmString.setString(realmListStrings.get(i).getString().toString());
            checkBoxes.add(realmString);
        }

        LinearLayoutManager llm = new LinearLayoutManager(context);
        holder.checkBoxContent.setHasFixedSize(true);
        holder.checkBoxContent.setLayoutManager(llm);

        RVListRowAdapter rowAdapter = new RVListRowAdapter(checkBoxes, context);

        holder.checkBoxContent.setAdapter(rowAdapter);
    }

    @Override
    public int getItemCount() {
        return stickyNotesList.size();
    }

    public class DataListViewHolder extends RecyclerView.ViewHolder {

        ImageButton update, delete;
        TextView textTitle;
        RecyclerView checkBoxContent;

        public DataListViewHolder(View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.txt_list_title);
            checkBoxContent = (RecyclerView) itemView.findViewById(R.id.chbox_list_content);

            update = (ImageButton) itemView.findViewById(R.id.update_list);
            delete = (ImageButton) itemView.findViewById(R.id.delete_list);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*StickyNote stickyNote = stickyNotesList.get(getAdapterPosition());
                    Intent i = new Intent(context, NewStickyNoteActivity.class);
                    i.putExtra("StickyNoteData", stickyNote);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    notifyDataSetChanged();*/
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*StickyNote stickyNote = stickyNotesList.get(getAdapterPosition());

                    final StickyNote stickyNoteBackup = new StickyNote(stickyNote.getId(), stickyNote.getNoteTitle(), stickyNote.getNoteContent());

                    RealmNoteManipulator.getRealmNoteInstance(context).deleteStickyNote(stickyNote);

                    notifyDataSetChanged();

                    Snackbar snackbar = Snackbar.make(v, "Note is Deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO DELETE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    RealmNoteManipulator.getRealmNoteInstance(context).updateStickyNote(stickyNoteBackup);

                                    notifyDataSetChanged();

                                    Snackbar snackbar1 = Snackbar.make(view, "Note is Restored!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });

                    snackbar.show();*/
                }

            });
        }
    }
}