package pramod.com.mystickynotes.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmResults;
import pramod.com.mystickynotes.R;
import pramod.com.mystickynotes.adapter.RVAdapter;
import pramod.com.mystickynotes.model.StickyNote;
import pramod.com.mystickynotes.realm.RealmManipulator;

public class StickyHome extends AppCompatActivity implements Filterable {

    RealmResults<StickyNote> realmNotes;
    RecyclerView recyclerView;
    TitleFilter titleFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sicky_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.drawable.ic_launcher);

//        toolbar.setNavigationIcon(R.drawable.ic_launcher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewStickyNoteActivity.class);
                startActivity(i);
//                finish();
            }
        });

        FloatingActionButton fabList = (FloatingActionButton) findViewById(R.id.fab_list);
        fabList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewListStickNoteActivity.class);
                startActivity(i);
//                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        realmNotes = RealmManipulator.getRealmInstance(getApplicationContext()).getAllStickyNotes();

        if (realmNotes.size() != 0) {

            Log.e("RealmData", realmNotes.get(0).getNoteTitle() + "\n" + realmNotes.get(0).getNoteContent());

            setAdapter(realmNotes);
        } else {
            Toast.makeText(getApplicationContext(), "There are Nothing to Show", Toast.LENGTH_LONG).show();
        }

    }

    public void setAdapter(List<StickyNote> noteList) {
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        RVAdapter rvAdapter = new RVAdapter(noteList, getApplicationContext());
        recyclerView.setAdapter(rvAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        realmNotes = RealmManipulator.getRealmInstance(getApplicationContext()).getAllStickyNotes();
        setAdapter(realmNotes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sicky_home, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final SearchView search_view = searchView;
        final SearchView finalSearchView = searchView;
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!finalSearchView.isIconified()) {
                    finalSearchView.setIconified(true);
                }
                searchItem.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getFilter().filter(newText);
                    }
                });

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Filter getFilter() {
        if (titleFilter == null) {
            titleFilter = new TitleFilter();
        }
        return titleFilter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            onResume();
            Toast.makeText(getApplicationContext(),"List Refreshed..!",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.exit)
            finish();
        else {

        }
        return super.onOptionsItemSelected(item);
    }


    public class TitleFilter extends Filter {

        List<StickyNote> stringList;

        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
            final FilterResults filterResults = new FilterResults();
            runOnUiThread(new Runnable() {
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
