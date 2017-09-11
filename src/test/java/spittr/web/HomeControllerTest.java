package spittr.web;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import spittr.Spitter;
import spittr.Spittle;
import spittr.data.SpitterRepository;
import spittr.data.SpittleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class HomeControllerTest {
    @Test
    public void homePage() throws Exception {
        HomeController homeController = new HomeController();
        MockMvc mockMvc = standaloneSetup(homeController).build();

        mockMvc.perform(get("/"))
                .andExpect(view().name("home"));
        mockMvc.perform(get("/homepage"))
                .andExpect(view().name("home"));
    }

    @Test
    public void recentSpittles() throws Exception {
        SpittleRepository repository = mock(SpittleRepository.class);
        List<Spittle> expectedSpittles = createSpittles(0, 20);
        when(repository.findSpittles(Long.MAX_VALUE, 20))
                .thenReturn(expectedSpittles);

        SpittleController spittleController = new SpittleController(repository);

        MockMvc mockMvc = standaloneSetup(spittleController)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();
        mockMvc.perform(get("/spittles"))
                .andExpect(model().attribute("spittleList", expectedSpittles))
                .andExpect(view().name("spittles"));
    }

    @Test
    public void spittlesWithParams() throws Exception {
        SpittleRepository repository = mock(SpittleRepository.class);
        List<Spittle> expectedSpittles = createSpittles(4000, 10);
        when(repository.findSpittles(4000, 10))
                .thenReturn(expectedSpittles);

        SpittleController spittleController = new SpittleController(repository);

        MockMvc mockMvc = standaloneSetup(spittleController)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();
        mockMvc.perform(get("/spittles?before=4000&count=10"))
                .andExpect(model().attribute("spittleList", expectedSpittles))
                .andExpect(view().name("spittles"));
    }

    private List<Spittle> createSpittles(int before, int count) {
        List<Spittle> spittles = new ArrayList<>();
        for (int i = before; i < before + count; i++) {
            spittles.add(new Spittle("spittle number " + i, new Date()));
        }
        return spittles;
    }

    @Test
    public void singleSpittle() throws Exception {
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        Spittle spittle55 = new Spittle("spittle55", new Date());
        when(mockRepository.findSpittle(55l)).thenReturn(spittle55);

        SpittleController spittleController = new SpittleController(mockRepository);
        MockMvc mockMvc = standaloneSetup(spittleController).build();

        mockMvc.perform(get("/spittles/55"))
                .andExpect(view().name("spittle"))
                .andExpect(model().attribute("spittle", spittle55));
    }

    @Test
    public void shouldShowRegistration() throws Exception {
        SpittrController controller = new SpittrController(null);

        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/spitter/register"))
                .andExpect(view().name("registerForm"));
    }

    @Test
    public void addSpitter() throws Exception {
        SpitterRepository repo = mock(SpitterRepository.class);
        Spitter unsaved = new Spitter("mitch", "jones", "mjones", "3434");
        Spitter saved = new Spitter(55L, "mitch", "jones", "mjones", "3434");
        when(repo.save(unsaved)).thenReturn(saved);

        SpittrController controller = new SpittrController(repo);

        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/spitter/register")
                .param("firstName", "mitch")
                .param("lastName", "jones")
                .param("userName", "mjones")
                .param("password", "3434"))
                .andExpect(redirectedUrl("/spitter/mjones"));
        verify(repo, atLeastOnce()).save(unsaved);
    }
}