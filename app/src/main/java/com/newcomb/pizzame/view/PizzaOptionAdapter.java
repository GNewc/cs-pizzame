package com.newcomb.pizzame.view;

import android.arch.lifecycle.LifecycleOwner;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newcomb.pizzame.R;
import com.newcomb.pizzame.ViewModel.PizzaDetailViewModel;
import com.newcomb.pizzame.ViewModel.PizzaOptionSelectedListener;
import com.newcomb.pizzame.ViewModel.PizzaOptionsViewModel;
import com.newcomb.pizzame.databinding.PizzaOptionItemBinding;
import com.newcomb.pizzame.model.PizzaOption;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter implementation for our pizza joints
 */

public class PizzaOptionAdapter extends RecyclerView.Adapter<PizzaOptionAdapter.BindingHolder> {

    private PizzaOptionsViewModel _viewModel;
    private PizzaOptionSelectedListener _selectionListener;
    private List<PizzaOption> _options = new ArrayList<>();

    public PizzaOptionAdapter(LifecycleOwner lcOwner,
                              PizzaOptionsViewModel viewModel,
                              PizzaOptionSelectedListener selectionListener) {
        _viewModel = viewModel;
        _selectionListener = selectionListener;
        _viewModel.getOptions().observe(lcOwner, options -> {
            _options = options;
             notifyDataSetChanged();
        });
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PizzaOptionItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.pizza_option_item, parent, false);

        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        PizzaOption po = _options.get(position);
        holder.binding.setOption(po);
    }

    @Override
    public int getItemCount() {
        return _options.size();
    }

    class BindingHolder extends RecyclerView.ViewHolder {
        private PizzaOptionItemBinding binding;

        BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            this.itemView.setOnClickListener((view)-> {
                _selectionListener.optionSelected(binding.getOption());
            });
        }
    }
}