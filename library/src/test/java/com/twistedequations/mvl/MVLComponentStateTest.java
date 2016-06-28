package com.twistedequations.mvl;

import android.os.Bundle;

import com.google.common.truth.Truth;
import com.twistedequations.mvl.mock.MockLifecycle;
import com.twistedequations.mvl.mock.MockMVLActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.observers.TestSubscriber;

public class MVLComponentStateTest {

    private MVLActivity mvlActivity = new MockMVLActivity();
    private MockLifecycle mockLifecycle = new MockLifecycle();

    @Before
    public void setUp() throws Exception {
        mvlActivity.registerLifecycle(mockLifecycle);
    }

    @Test
    public void testGettingPreviousSate() {
        Bundle bundle = new Bundle();
        mvlActivity.onCreate(bundle);

        TestSubscriber<Bundle> testSubscriber = new TestSubscriber<>();
        mvlActivity.getPrevState().subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(bundle);
    }

    @Test
    public void testGettingPreviousSateMissing() {
        mvlActivity.onCreate(null);

        TestSubscriber<Bundle> testSubscriber = new TestSubscriber<>();
        mvlActivity.getPrevState().subscribe(testSubscriber);
        List<Bundle> events = testSubscriber.getOnNextEvents();
        Truth.assertThat(events.size()).isEqualTo(0);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertNoValues();
        testSubscriber.assertValueCount(0);
    }

    @Test
    public void testBackButtonObs() {
        TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
        mvlActivity.backClicks().subscribe(testSubscriber);
        mvlActivity.onBackPressed();

        testSubscriber.assertNotCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(null);
    }

    @Test
    public void testBackButtonObsNoClicks() {
        TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
        mvlActivity.backClicks().subscribe(testSubscriber);

        testSubscriber.assertNotCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(0);
    }

}