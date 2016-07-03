package com.twistedequations.mvl.internal;

import rx.Observable;
import rx.Subscriber;

class RelayOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private Subscriber<? super T> subscriber;

    @Override
    public void call(Subscriber<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    boolean sendEvent(T event) {
        if(subscriber == null) {
            return false;
        } else if(subscriber.isUnsubscribed()) {
            return false;
        } else {
            //subscribed and non null, valid observable to send the event
            subscriber.onNext(event);
            return true;
        }
    }

}
