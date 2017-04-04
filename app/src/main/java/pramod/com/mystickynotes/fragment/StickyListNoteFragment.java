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
import android.widget.Toast;

import java.util.List;

import io.realm.RealmResults;
import pramod.com.mystickynotes.R;
import pramod.com.mystickynotes.activity.NewListStickNoteActivity;
import pramod.com.mystickynotes.adapter.RVListNoteRowAdapter;
import pramod.com.mystickynotes.adapter.RVNoteRowAdapter;
import pramod.com.mystickynotes.model.StickyListNote;
import pramod.com.mystickynotes.model.StickyNote;
import pramod.com.mystickynotes.realm.RealmListNoteManipulator;
import pramod.com.mystickynotes.realm.RealmNoteManipulator;


public class StickyListNoteFragment extends Fragment {

    RealmResults<StickyListNote> realmNotes;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sticky_list_note, container, false);;

        FloatingActionButton fabList = (FloatingActionButton) view.findViewById(R.id.fab_list);
        fabList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), NewListStickNoteActivity.class);
                startActivity(i);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        realmNotes = RealmListNoteManipulator.getRealmListNoteInstance(getContext()).getAllStickyListNotes();

        if (realmNotes.size() != 0) {

            Log.e("RealmData", realmNotes.get(0).getListNoteTitle() + "\n" + realmNotes.get(0).getListNote());

            setAdapter(realmNotes);
        } else {
            Toast.makeText(getContext(), "There are Nothing to Show", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public void setAdapter(List<StickyListNote> noteList) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        RVListNoteRowAdapter rvAdapter = new RVListNoteRowAdapter(noteList, getContext());
        recyclerView.setAdapter(rvAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        realmNotes = RealmListNoteManipulator.getRealmListNoteInstance(getContext()).getAllStickyListNotes();
        setAdapter(realmNotes);
    }
}
