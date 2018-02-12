package com.shibedays.wordlistsql;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ttbot on 2/11/2018.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    /**
     *  Custom view holder with a text view and two buttons.
     */
    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        Button delete_button;
        Button edit_button;

        public WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = (TextView) itemView.findViewById(R.id.word);
            delete_button = (Button)itemView.findViewById(R.id.delete_button);
            edit_button = (Button)itemView.findViewById(R.id.edit_button);
        }
    }

    private static final String TAG = WordListAdapter.class.getSimpleName();

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_WORD = "WORD";
    public String EXTRA_POSITION = "POSITION";


    private WordListOpenHelper mDB;

    private final LayoutInflater mInflater;
    Context mContext;

    public WordListAdapter(Context context, WordListOpenHelper db) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDB = db;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        WordItem current = mDB.query(position);
        holder.wordItemView.setText(current.getmWord() + " " + current.getmID());

        final WordViewHolder h = holder;

        holder.delete_button.setOnClickListener(
                new MyButtonOnClickListener(current.getmID(), null) {
                    @Override
                    public void onClick(View v){
                        int deleted = mDB.delete(id);
                        if(deleted >= 0)
                            notifyItemRemoved(h.getAdapterPosition());
            }
        });

        holder.edit_button.setOnClickListener(
                new MyButtonOnClickListener(current.getmID(), current.getmWord()){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(mContext, EditWordActivity.class);

                        intent.putExtra(EXTRA_ID , id);
                        intent.putExtra(EXTRA_POSITION, h.getAdapterPosition());
                        intent.putExtra(EXTRA_WORD, word);

                        // Start an empty edit activity
                        ((Activity) mContext).startActivityForResult(intent, MainActivity.WORD_EDIT);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        // Placeholder so we can see some mock data.
        return (int) mDB.count();
    }

}
