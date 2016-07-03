package com.twistedequations.mvl.util;

import com.google.common.truth.Truth;
import com.twistedequations.mvl.internal.CollectionFuncs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionFuncsTest {

    @Test
    public void testForEach() {

        List<String> startList = new ArrayList<>();
        startList.add("String1");
        startList.add("String2");
        startList.add("String3");
        startList.add("String4");

        List<String> endList = new ArrayList<>();

        CollectionFuncs.forEach(startList, endList::add);
        Truth.assertThat(endList).isEqualTo(startList);
    }

    @Test
    public void testForEachType() {

        StringBuilder stringBuilder = new StringBuilder();
        List<CharSequence> startList = new ArrayList<>();
        startList.add("String1");
        startList.add("String2");
        startList.add("String3");
        startList.add(stringBuilder);

        List<String> endList = new ArrayList<>();

        CollectionFuncs.forEachType(startList, String.class, endList::add);
        Truth.assertThat(endList).contains("String1");
        Truth.assertThat(endList).contains("String2");
        Truth.assertThat(endList).contains("String3");
        Truth.assertThat(endList).doesNotContain(stringBuilder);
    }
}