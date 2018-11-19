package com.shibedays.roomwordsample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by ttbot on 2/21/2018.
 */

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepo;

    private LiveData<List<Word>> mAllWords;

    public WordViewModel(Application application){
        super(application);
        mRepo = new WordRepository(application);
        mAllWords = mRepo.getAllWords();
    }

    LiveData<List<Word>> getmAllWords() { return mAllWords; }

    public void insert(Word word){ mRepo.insert(word); }
}
