
package com.bonnemai.viewer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = Viewer.class)
public class ApplicationTest {
    private static Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(Viewer.class).build();
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
        logger.info("content: {}", content());
    }


    @Test
    public void testAvgFalseNegative()  {
        try {
            mockMvc.perform(get("/average")).andExpect(status().isInternalServerError());
            Assert.assertTrue("Should not have enough Values", false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
}
