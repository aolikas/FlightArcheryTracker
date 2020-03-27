package com.example.flightarcherytracker.adapters;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

public class TrainingRecyclerViewAdapterTest {

    @Mock
    Context context;

    private TrainingRecyclerViewAdapter mAdapter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        //mAdapter = spy(new TrainingRecyclerViewAdapter(context));
    }

    @After
    public void tearDown() throws Exception {
    }
}