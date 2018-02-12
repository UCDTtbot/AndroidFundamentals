package com.shibedays.wordlistsql;

/**
 * Created by ttbot on 2/12/2018.
 */

public class WordItem {

    private int mID;
    private String mWord;

    public WordItem(){

    }

    //region GET
    public int getmID(){
        return mID;
    }

    public String getmWord(){
        return mWord;
    }
    //endregion

    //region SET
    public void setmID(int id){
        mID = id;
    }

    public void setmWord(String word){
        mWord = word;
    }
    //endregion

}
