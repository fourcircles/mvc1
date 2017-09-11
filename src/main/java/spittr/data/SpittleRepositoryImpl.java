package spittr.data;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import spittr.Spittle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SpittleRepositoryImpl implements SpittleRepository {
    @Override
    public List<Spittle> findSpittles(long max, int count) {
        return createSpittles(count);
    }

    private List<Spittle> createSpittles(int count) {
        List<Spittle> spittles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            spittles.add(new Spittle("spittle number " + i, new Date()));
        }
        return spittles;
    }

}
