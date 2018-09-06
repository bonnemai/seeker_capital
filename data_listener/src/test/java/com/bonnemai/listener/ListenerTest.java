package com.bonnemai.listener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;


@RunWith(SpringJUnit4ClassRunner.class)
public class ListenerTest {
    private MockMvc mvc;
    Listener listener=new Listener();

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(listener).build();
    }


    @Test
    public void testIndex() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }


    @Test
    public void testError() throws Exception {
        mvc.perform(get("/error")).andExpect(status().isOk());
    }


    @Test
    public void testAvgError() throws Exception {
        listener.setValues(Arrays.asList(1,2,3,4));
        mvc.perform(get("/average")).andExpect(status().isOk());
    }

    @Test
    public void testAvgFalseNegative() throws Exception {
        try {
            mvc.perform(get("/average")).andExpect(status().isInternalServerError());
            Assert.assertTrue("Should not have enough Values", false);
        }catch (Exception e){
            Assert.assertTrue(true);
        }
    }
}
