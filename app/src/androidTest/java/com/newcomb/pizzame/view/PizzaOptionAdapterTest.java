package com.newcomb.pizzame.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;

import com.newcomb.pizzame.model.PizzaOption;
import com.newcomb.pizzame.viewmodel.PizzaOptionSelectedListener;
import com.newcomb.pizzame.viewmodel.PizzaOptionsViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Test
 */

@RunWith(AndroidJUnit4.class)
public class PizzaOptionAdapterTest {
    private PizzaOption TEST_OBJECT;
    private AppCompatActivity _mainActivity;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        TEST_OBJECT = new PizzaOption("Pizza Day",
                                      "10225 Research Blvd, Ste 110",
                                      "Austin",
                                      "TX",
                                      "(512) 345-7492",
                                      0.53,
                                      30.39537,
                                      -97.74545);
        _mainActivity = activityRule.getActivity();
    }

    @Test
    public void testNotification() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        PizzaOptionsViewModel viewModel = new PizzaOptionsViewModel();
        PizzaOptionSelectedListener selectionListener = option -> {
            // test this at some point...
        };
        PizzaOptionAdapter adapter = new PizzaOptionAdapter(_mainActivity,
                                                            viewModel,
                                                            selectionListener);

        AtomicReference<List<PizzaOption>> showMe = new AtomicReference<>(null);
        adapter.registerAdapterDataObserver(new AdapterDataObserver() {
            public void onChanged() {
                showMe.set(viewModel.getOptions().getValue());
                latch.countDown();
            }
        });
        List<PizzaOption> options = new ArrayList<>(Arrays.asList(TEST_OBJECT));
        _mainActivity.runOnUiThread(()->viewModel.updateOptions(options));
        if(latch.await(5000, TimeUnit.MILLISECONDS)) {
            Assert.assertArrayEquals(options.toArray(), showMe.get().toArray());
            return;
        }
        Assert.fail();
    }
}
