package spittr.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import spittr.Spitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SpitterRepositoryImpl implements SpitterRepository {
    private List<Spitter> spitters = new ArrayList<>();
    private Long lastId;

    @Override
    public Spitter save(Spitter spitter) {
        Spitter spitter1 = new Spitter(lastId++, spitter.getFirstName(), spitter.getLastName(), spitter.getUserName(), spitter.getPassword());
        spitters.add(spitter1);
        return spitter1;
    }

    @Override
    public Optional<Spitter> get(Long id) {
        return spitters.stream()
                .filter(spitter -> id.equals(spitter.getId()))
                .findFirst();
    }
}
