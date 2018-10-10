package com.tarys.nps;

import com.tarys.nps.ai.AI;
import com.tarys.nps.repository.NpsRepository;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class KafkaNpsListener {

    private final NpsRepository repository;
    private final AI ai;

    public KafkaNpsListener(NpsRepository repository, AI ai) {
        this.repository = repository;
        this.ai = ai;
    }

    @KafkaListener(topics = "nps")
    public void stringKafkaListener() throws IOException {
        final String analysisResult = ai.analyze(repository.getAverage());

        System.out.println(analysisResult);
    }
}
