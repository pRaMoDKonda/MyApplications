package pramod.com.mystickynotes.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import pramod.com.mystickynotes.R;
import pramod.com.mystickynotes.adapter.RVNoteRowAdapter;
import pramod.com.mystickynotes.fragment.StickyListNoteFragment;
import pramod.com.mystickynotes.fragment.StickyNoteFragment;
import pramod.com.mystickynotes.model.StickyNote;
import pramod.com.mystickynotes.realm.RealmNoteManipulator;

public class StickyHome extends AppCompatActivity implements Filterable {


    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    ViewPagerAdapter viewPagerAdapter;

    RealmResults<StickyNote> realmNotes;

    TitleFilter titleFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sicky_home);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.drawable.ic_launcher);

        addViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
//        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher_round);

        realmNotes = RealmNoteManipulator.getRealmNoteInstance(getApplicationContext()).getAllStickyNotes();

    }

    private void addViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new StickyNoteFragment(), "Note");
        viewPagerAdapter.addFragment(new StickyListNoteFragment(), "List Note");
        viewPager.setAdapter(viewPagerAdapter);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            onResume();
            Toast.makeText(getApplicationContext(), "List Refreshed..!", Toast.LENGTH_LONG).show();
        } else if (id == R.id.exit)
            finish();
        else {

        }
        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> FragmentList = new ArrayList<>();
        private final List<String> TitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentList.get(position);
        }

        @Override
        public int getCount() {
            return FragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TitleList.get(position);
//            return null;
        }

        public void addFragment(Fragment fragment, String title) {
            FragmentList.add(fragment);
            TitleList.add(title);
        }
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
            final StickyNoteFragment noteFragment = (StickyNoteFragment) viewPagerAdapter.getItem(0);
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
                        noteFragment.setAdapter(tempList);
                    } else {

                        /*filterResults.count = stringList.size();
                        filterResults.values = stringList;*/
                        noteFragment.setAdapter(realmNotes);
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
