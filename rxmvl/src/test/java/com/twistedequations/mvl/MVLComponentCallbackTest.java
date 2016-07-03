package com.twistedequations.mvl;

import android.os.Bundle;

import com.google.common.truth.Truth;
import com.twistedequations.mvl.mock.MockLifecycle;
import com.twistedequations.mvl.mock.MockMVLActivity;

import org.junit.Before;
import org.junit.Test;

public class MVLComponentCallbackTest {

    private MockMVLActivity activity = new MockMVLActivity();
    private MVLComponent mvlBaseActivity = activity;
    private MockLifecycle mockLifecycle = new MockLifecycle();

    @Before
    public void setUp() throws Exception {
        mvlBaseActivity.registerLifecycle(mockLifecycle);
    }

    @Test
    public void onCreate() throws Exception {
        activity.onCreate(new Bundle());
        Truth.assertThat(mockLifecycle.onCreate).isTrue();
        Truth.assertThat(mockLifecycle.onStart).isFalse();
        Truth.assertThat(mockLifecycle.onResume).isFalse();
        Truth.assertThat(mockLifecycle.onPause).isFalse();
        Truth.assertThat(mockLifecycle.onStop).isFalse();
        Truth.assertThat(mockLifecycle.onDestroy).isFalse();
    }

    @Test
    public void onStart() throws Exception {
        activity.onStart();
        Truth.assertThat(mockLifecycle.onStart).isTrue();
        Truth.assertThat(mockLifecycle.onCreate).isFalse();
        Truth.assertThat(mockLifecycle.onResume).isFalse();
        Truth.assertThat(mockLifecycle.onPause).isFalse();
        Truth.assertThat(mockLifecycle.onStop).isFalse();
        Truth.assertThat(mockLifecycle.onDestroy).isFalse();
    }

    @Test
    public void onResume() throws Exception {
        activity.onResume();
        Truth.assertThat(mockLifecycle.onStart).isFalse();
        Truth.assertThat(mockLifecycle.onCreate).isFalse();
        Truth.assertThat(mockLifecycle.onResume).isTrue();
        Truth.assertThat(mockLifecycle.onPause).isFalse();
        Truth.assertThat(mockLifecycle.onStop).isFalse();
        Truth.assertThat(mockLifecycle.onDestroy).isFalse();
    }

    @Test
    public void onPause() throws Exception {
        activity.onPause();
        Truth.assertThat(mockLifecycle.onStart).isFalse();
        Truth.assertThat(mockLifecycle.onCreate).isFalse();
        Truth.assertThat(mockLifecycle.onResume).isFalse();
        Truth.assertThat(mockLifecycle.onPause).isTrue();
        Truth.assertThat(mockLifecycle.onStop).isFalse();
        Truth.assertThat(mockLifecycle.onDestroy).isFalse();
    }

    @Test
    public void onStop() throws Exception {
        activity.onStop();
        Truth.assertThat(mockLifecycle.onStart).isFalse();
        Truth.assertThat(mockLifecycle.onCreate).isFalse();
        Truth.assertThat(mockLifecycle.onResume).isFalse();
        Truth.assertThat(mockLifecycle.onPause).isFalse();
        Truth.assertThat(mockLifecycle.onStop).isTrue();
        Truth.assertThat(mockLifecycle.onDestroy).isFalse();
    }

    @Test
    public void onDestroy() throws Exception {
        activity.onDestroy();
        Truth.assertThat(mockLifecycle.onStart).isFalse();
        Truth.assertThat(mockLifecycle.onCreate).isFalse();
        Truth.assertThat(mockLifecycle.onResume).isFalse();
        Truth.assertThat(mockLifecycle.onPause).isFalse();
        Truth.assertThat(mockLifecycle.onStop).isFalse();
        Truth.assertThat(mockLifecycle.onDestroy).isTrue();
    }
}