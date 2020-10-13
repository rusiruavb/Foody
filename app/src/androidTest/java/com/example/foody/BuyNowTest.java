package com.example.foody;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class BuyNowTest {

    private TestClassActivity buyNowActivity = null;

    @Rule
    public ActivityTestRule<TestClassActivity> buyNowRule = new ActivityTestRule<TestClassActivity>(TestClassActivity.class);

    @Before
    public void setUp() throws Exception {
        buyNowActivity = buyNowRule.getActivity();
    }

    @Test
    public void testBuyNowtFragmentLaunch() {
        RelativeLayout relativeContainer = buyNowActivity.findViewById(R.id.test_container);
        assertNotNull(relativeContainer);
        BuyNow fragmentBuyNow = new BuyNow();
        buyNowActivity.getSupportFragmentManager().beginTransaction().add(relativeContainer.getId(), fragmentBuyNow).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();
        View view = fragmentBuyNow.getView().findViewById(R.id.fragment_buy_now_id);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        buyNowActivity = null;
    }
}