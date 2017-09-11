package spittr.data;

import spittr.Spitter;

import java.util.Optional;

public interface SpitterRepository {
    Spitter save(Spitter spitter);
    Optional<Spitter> get(Long id);
}
