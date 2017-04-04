package pramod.com.mystickynotes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import butterknife.ButterKnife;
import io.realm.RealmList;
import io.realm.RealmModel;
import pramod.com.mystickynotes.R;
import pramod.com.mystickynotes.realm.RealmString;

/**
 * Created by ipspl on 4/4/17.
 */

public class RVListRowAdapter extends RecyclerView.Adapter<RVListRowAdapter.ListViewHolder> {

    RealmList<RealmString> checkboxListItem;
    Context context;

    public RVListRowAdapter(RealmList<RealmString> checkboxListItem, Context context) {
        this.checkboxListItem = checkboxListItem;
        this.context = context;
    }

    @Override
    public RVListRowAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content_row,parent,false);
        RVListRowAdapter.ListViewHolder listViewHolder = new RVListRowAdapter.ListViewHolder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.checkBoxList.setText(checkboxListItem.get(position).getString());
    }

    @Override
    public int getItemCount() {
        return checkboxListItem.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {


        CheckBox checkBoxList;

        public ListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(itemView);

            checkBoxList = (CheckBox) itemView.findViewById(R.id.checkbox_list);

        }
    }
}
