package com.newcomb.pizzame.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.newcomb.pizzame.R;
import com.newcomb.pizzame.ViewModel.PizzaDetailViewModel;
import com.newcomb.pizzame.databinding.PizzaDetailsLayoutBinding;
import com.newcomb.pizzame.model.PizzaOption;

/**
 * Fragment to display the selected PizzaOption and provide access to call/map
 */

public class OptionDetailFragment extends Fragment {
    private PizzaOption _option;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PizzaDetailViewModel selectedModel = ViewModelProviders.of(getActivity()).get(PizzaDetailViewModel.class);
        PizzaDetailsLayoutBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.pizza_details_layout, container, false);
        View view = binding.getRoot();
        view.findViewById(R.id.call_button).setOnClickListener(cv->{
            if(_option!=null) {
                String number = _option.getPhone();
                if(!TextUtils.isEmpty(number)) {
                    Uri uri = Uri.fromParts("tel", number, null);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return;
                }
            }
            Toast.makeText(getContext(), "No number associated with this listing", Toast.LENGTH_LONG);
        });
        view.findViewById(R.id.map_button).setOnClickListener(cv->{
            if(_option!=null) {
                Uri geoLocation = Uri.parse(String.format("geo:0,0?q=%3.5f,%3.5f(%s)",
                                                          _option.getLatitude(),
                                                          _option.getLongitude(),
                                                          _option.getName()));
                Intent intent = new Intent(Intent.ACTION_VIEW, geoLocation);
                startActivity(intent);
                return;
            }
            Toast.makeText(getContext(), "Issues resolving the location", Toast.LENGTH_LONG);
        });
        selectedModel.getSelected().observe(this, option -> {
            _option = option;
            binding.setOption(option);
        });
        return view;
    }
}
