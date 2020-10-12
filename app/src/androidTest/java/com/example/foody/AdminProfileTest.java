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

public class AdminProfileTest {

    private TestClassActivity adminProfile = null;

    @Rule
    public ActivityTestRule<TestClassActivity> adminRule = new ActivityTestRule<TestClassActivity>(TestClassActivity.class);


    @Before
    public void setUp() throws Exception {
        adminProfile = adminRule.getActivity();
    }

    @Test
    public void testAddProductFragmentLaunch() {
        RelativeLayout relativeContainer = adminProfile.findViewById(R.id.test_container);
        assertNotNull(relativeContainer);
        AdminProfile fragmentAddProduct = new AdminProfile();
        adminProfile.getSupportFragmentManager().beginTransaction().add(relativeContainer.getId(), fragmentAddProduct).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();
        View view = fragmentAddProduct.getView().findViewById(R.id.fragment_admin_profile);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        adminProfile = null;
    }
}