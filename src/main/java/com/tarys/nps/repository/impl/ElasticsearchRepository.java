package com.tarys.nps.repository.impl;

import com.tarys.nps.repository.NpsRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class ElasticsearchRepository implements NpsRepository {
    private final RestHighLevelClient restHighLevelClient;

    public ElasticsearchRepository(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public double getAverage() throws IOException {
        final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        final AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("nps_average").field("nps");
        final SearchRequest searchRequest = new SearchRequest("nps");

        searchSourceBuilder.aggregation(avgAggregationBuilder);
        searchRequest.source(searchSourceBuilder);

        final SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        final Avg npsAverage = response.getAggregations().get("nps_average");

        return npsAverage.getValue();
    }
}
