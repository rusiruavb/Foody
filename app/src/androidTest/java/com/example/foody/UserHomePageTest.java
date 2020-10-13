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

public class UserHomePageTest {

    private TestClassActivity  userHomePageActivity = null;

    @Rule
    public ActivityTestRule<TestClassActivity> userHomePageRule = new ActivityTestRule<TestClassActivity>(TestClassActivity.class);

    @Before
    public void setUp() throws Exception {
        userHomePageActivity = userHomePageRule.getActivity();
    }

    @Test
    public void testUserHomeFragmentLaunch() {
        RelativeLayout relativeContainer = userHomePageActivity.findViewById(R.id.test_container);
        assertNotNull(relativeContainer);
        UserHomePage fragmentUserHome = new UserHomePage();
        userHomePageActivity.getSupportFragmentManager().beginTransaction().add(relativeContainer.getId(), fragmentUserHome).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();
        View view = fragmentUserHome.getView().findViewById(R.id.fragment_user_home_page);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        userHomePageActivity = null;
    }
}