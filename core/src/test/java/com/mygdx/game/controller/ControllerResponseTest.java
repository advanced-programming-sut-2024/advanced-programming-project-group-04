package com.mygdx.game.controller;

import mygdx.game.controller.ControllerResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerResponseTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testGetters() {
        ControllerResponse response = new ControllerResponse(true, "wrong password");
        assertTrue(response.isFailed());
        assertSame(response.getError(), "wrong password");
    }
}
