package com.twistedequations.mvl;

import android.os.Bundle;

import com.twistedequations.mvl.lifecycle.Lifecycle;

import java.util.Set;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public interface MVLComponent {

    /**
     * Gets the previous state as an observable
     * @return Observable that emits the previous state. If no state exists then on complete is called
     */
    Observable<Bundle> getPrevState();

    /**
     * Update the saved state for this activity
     * @param updateFunction function to update the state
     */
    void updateSaveState(Action1<Bundle> updateFunction);

    /**
     * Get the currently registered lifecycles
     * @return
     */
    Set<Lifecycle> getLifecycles();

    /**
     * Register a lifecycle for an activity
     * @param lifecycle the lifecycle to register
     * @return
     */
    boolean registerLifecycle(Lifecycle lifecycle);

    boolean unRegisterLifecycle(Lifecycle lifecycle);

    /**
     * Main method for activitys, register any lifecycles here.
     * <code>
     *     <pre>
     *         registerLifecycle(exampleLifecycle);
     *     </pre>
     * </code>
     */
    void main();

    /**
     * Observe when the back button is pressed
     * @return  Observable<Void>
     */
    Observable<Void> backClicks();
}
