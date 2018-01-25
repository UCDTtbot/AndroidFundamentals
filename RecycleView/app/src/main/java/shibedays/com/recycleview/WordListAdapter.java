package shibedays.com.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ttbot on 1/24/18.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter){
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }
    }

    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(WordListAdapter.WordViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
