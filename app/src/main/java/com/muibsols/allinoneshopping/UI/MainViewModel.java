package com.muibsols.allinoneshopping.UI;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    public void setMutableLiveData(String text) {
        mutableLiveData.setValue(text);
        Log.i("HELL", "setText: "+text);
    }

    public LiveData<String> getMutableLiveData() {
        return mutableLiveData;
    }
}
