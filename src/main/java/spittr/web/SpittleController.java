package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import spittr.Spittle;
import spittr.data.SpittleRepository;

import javax.ws.rs.PathParam;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

    private SpittleRepository spittleRepository;

    @Autowired
    public SpittleController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String spittles(@RequestParam(defaultValue = Long.MAX_VALUE + "") long before,
                           @RequestParam(defaultValue = "20") int count,
                           Model model) {

        model.addAttribute(spittleRepository.findSpittles(before, count));
        return "spittles";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String singleSpittle(@PathVariable Long id, Model model) {
        Spittle spittle = spittleRepository.findSpittle(id);
        model.addAttribute(spittle);
        return "spittle";
    }
}
