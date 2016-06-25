package com.twistedequations.mvl.internal;

import java.util.Collection;

import rx.functions.Action1;

public final class CollectionFuncs {

    private CollectionFuncs(){}

    public static <T> void forEach(Collection<T> collection, Action1<T> function) {
        for (T t : collection) {
            function.call(t);
        }
    }

    public static <T, R extends T> void forEachType(Collection<T> collection, Class<R> filterClass, Action1<R> function) {
        for (T t : collection) {
            if(t.getClass().isAssignableFrom(filterClass)) {
                function.call(filterClass.cast(t));
            }
        }
    }
}
