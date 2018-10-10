package com.tarys.nps;

import com.tarys.nps.ai.AI;
import com.tarys.nps.ai.impl.SuperSmartAI;
import com.tarys.nps.repository.NpsRepository;
import com.tarys.nps.repository.impl.ElasticsearchRepository;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class SpringBootConfiguration {

    @Bean
    public AI npsAI() {
        return new SuperSmartAI();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(@Value("${spring.elasticsearch.host}") final String host,
                                                   @Value("${spring.elasticsearch.port}") final int port,
                                                   @Value("${spring.elasticsearch.schema}") final String schema) {
        HttpHost httpHost = new HttpHost(host, port, schema);
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }

    @Bean
    public NpsRepository averageNpsRepository(final RestHighLevelClient restHighLevelClient) {
        return new ElasticsearchRepository(restHighLevelClient);
    }

    @Bean
    public KafkaNpsListener kafkaNpsListener(final NpsRepository repository, final AI ai) {
        return new KafkaNpsListener(repository, ai);
    }
}
