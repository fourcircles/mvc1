package spittr.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HomeControllerTest {
    @Test
    public void homePage() throws Exception {
        HomeController homeController = new HomeController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("home"));
        mockMvc.perform(MockMvcRequestBuilders.get("/homepage"))
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }
}