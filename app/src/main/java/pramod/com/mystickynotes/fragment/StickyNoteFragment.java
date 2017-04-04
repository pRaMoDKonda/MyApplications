package pramod.com.mystickynotes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.OnClick;
import io.realm.RealmResults;
import pramod.com.mystickynotes.R;
import pramod.com.mystickynotes.activity.NewStickyNoteActivity;
import pramod.com.mystickynotes.activity.StickyHome;
import pramod.com.mystickynotes.adapter.RVNoteRowAdapter;
import pramod.com.mystickynotes.model.StickyNote;
import pramod.com.mystickynotes.realm.RealmNoteManipulator;


public class StickyNoteFragment extends Fragment implements Filterable {

    RealmResults<StickyNote> realmNotes;
    RecyclerView recyclerView;

    TitleFilter titleFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sticky_note, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), NewStickyNoteActivity.class);
                startActivity(i);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        realmNotes = RealmNoteManipulator.getRealmNoteInstance(getContext()).getAllStickyNotes();

        if (realmNotes.size() != 0) {

            Log.e("RealmData", realmNotes.get(0).getNoteTitle() + "\n" + realmNotes.get(0).getNoteContent());

            setAdapter(realmNotes);
        } else {
            Toast.makeText(getContext(), "There are Nothing to Show", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public void setAdapter(List<StickyNote> noteList) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        RVNoteRowAdapter rvAdapter = new RVNoteRowAdapter(noteList, getContext());
        recyclerView.setAdapter(rvAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        realmNotes = RealmNoteManipulator.getRealmNoteInstance(getContext()).getAllStickyNotes();
        setAdapter(realmNotes);
    }

    @Override
    public Filter getFilter() {

        if (titleFilter == null) {
            titleFilter = new TitleFilter();
        }
        return titleFilter;
    }

    public class TitleFilter extends Filter {

        List<StickyNote> stringList;

        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
            final FilterResults filterResults = new FilterResults();
            new StickyHome().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StickyNote[] resultArray = realmNotes.toArray(new StickyNote[realmNotes.size()]);
                    stringList = new ArrayList<>(Arrays.asList(resultArray));

                    if (constraint != null && constraint.length() > 0) {
                        List<StickyNote> tempList = new ArrayList<>();
                        for (StickyNote note : stringList) {
                            if (note.getNoteTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                tempList.add(note);
                            }
                        }
                        /*filterResults.count = tempList.size();
                        filterResults.values = tempList;*/
                        setAdapter(tempList);
                    } else {

                        /*filterResults.count = stringList.size();
                        filterResults.values = stringList;*/
                        setAdapter(realmNotes);
                    }
                }
            });
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }
}
