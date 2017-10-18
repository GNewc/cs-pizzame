package com.newcomb.pizzame.view;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.newcomb.pizzame.R;
import com.newcomb.pizzame.viewmodel.PizzaDetailViewModel;
import com.newcomb.pizzame.viewmodel.PizzaOptionSelectedListener;
import com.newcomb.pizzame.viewmodel.PizzaOptionsViewModel;
import com.newcomb.pizzame.viewmodel.RequestNearestPizzaTask;
import com.newcomb.pizzame.model.PizzaOption;
import com.newcomb.pizzame.utils.PermissionUtils;


public class PizzaOptionsFragment extends Fragment
                                  implements PizzaOptionSelectedListener
{
    private static final String LOG_TAG = PizzaOptionsFragment.class.getSimpleName();
    private PizzaOptionsViewModel _viewModel;
    private PizzaDetailViewModel  _selectedModel;

    //private View               _progressView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the ViewModels
        _viewModel = ViewModelProviders.of(getActivity()).get(PizzaOptionsViewModel.class);
        _selectedModel = ViewModelProviders.of(getActivity()).get(PizzaDetailViewModel.class);

        View v = inflater.inflate(R.layout.pizza_options_layout, container, false);
        //_progressView = v.findViewById(R.id.progress_view);
        RecyclerView recycler     = v.findViewById(R.id.options_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        PizzaOptionAdapter adapter = new PizzaOptionAdapter(this, _viewModel, this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Log.d(LOG_TAG, "Will refresh view");
            if(ContextCompat.checkSelfPermission(getActivity(),
                                                 Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                new RequestNearestPizzaTask(_viewModel).execute((AppCompatActivity) getActivity());
            }
            else {
                PermissionUtils.requestPermissionsFromFragment(this, 666);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if(requestCode==666) {
            new RequestNearestPizzaTask(_viewModel).execute((AppCompatActivity) getActivity());
        }
    }
    //-------------------------------------------------------------------------
    // PizzaOptionSelectedListener implementation
    @Override
    public void optionSelected(PizzaOption option) {
        _selectedModel.select(option);
        try {
            FragmentManager fragMgr = getActivity().getSupportFragmentManager();

            FragmentTransaction transaction = fragMgr.beginTransaction();
            transaction.replace(R.id.fragment_holder, new OptionDetailFragment(), "detail");
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // TODO: Progress handling if this is going to take cycles ...
//    private void showProgress(final boolean show) {
//        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//        _progressView.setVisibility(show ? View.VISIBLE : View.GONE);
//        _progressView.bringToFront();
//        _progressView.animate().setDuration(shortAnimTime).alpha(
//                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                _progressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            }
//        });
//    }
}
