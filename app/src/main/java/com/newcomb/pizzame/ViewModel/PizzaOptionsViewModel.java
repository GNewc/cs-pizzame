package com.newcomb.pizzame.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.newcomb.pizzame.model.PizzaOption;

import java.util.List;

/**
 * ViewModel implementation for our list of pizza joint options
 */

public class PizzaOptionsViewModel extends ViewModel {
    private MutableLiveData<List<PizzaOption>> _options = new MutableLiveData<>();

    public LiveData<List<PizzaOption>> getOptions() {
        return _options;
    }

    public void updateOptions(List<PizzaOption> options) {
        _options.setValue(options);
    }

}
