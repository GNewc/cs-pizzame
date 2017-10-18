package com.newcomb.pizzame.viewmodel;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.newcomb.pizzame.model.PizzaOption;
import com.newcomb.pizzame.view.MainActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Example of androidTest that demonstrates:
 * 1. The need to update LiveData on the main UI thread, and how to use a rule to do it.
 * 2. How to test Observable pattern
 */
@RunWith(AndroidJUnit4.class)
public class PizzaDetailViewModelTest {


    private PizzaOption TEST_OBJECT;
    private Activity _mainActivity;

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
    public void testSelect() throws Exception {
        CountDownLatch showMe = new CountDownLatch(1);
        PizzaDetailViewModel viewModel = new PizzaDetailViewModel();
        final AtomicReference<PizzaOption> selected = new AtomicReference<>(null);
        viewModel.getSelected().observeForever(pizzaOption -> {
            selected.set(pizzaOption);
            showMe.countDown();
        });
        _mainActivity.runOnUiThread(()->viewModel.select(TEST_OBJECT));
        if (showMe.await(2000, TimeUnit.MILLISECONDS)) {
            Assert.assertEquals(TEST_OBJECT, selected.get());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetSelected() throws Exception {
    }

}