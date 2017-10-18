package com.newcomb.pizzame.viewmodel;

import com.newcomb.pizzame.model.PizzaOption;

/**
 * When the user selects an option on the recyclerview...
 */

public interface PizzaOptionSelectedListener {
    void optionSelected(PizzaOption option);
}
