package com.deng.recipes.persit;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hcdeng on 2017/4/24.
 */
public class ESUtils {
    private static Client esClient;

    static {
        init();
    }

    private static void init() {
        try {
            esClient = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (Exception e) {
            System.out.println("error when init es");
        }
    }

    public static int indexObject(Object object, String indexName) {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(indexName);

        IndexResponse response = esClient.prepareIndex(indexName, object.getClass().getName())
                .setSource(JSON.toJSONString(object), XContentType.JSON).get();

        System.out.println("saving object:" +response.status().getStatus());
        return response.status().getStatus();
    }


    public static List<Integer> indexBatch(List<?> objects, String indexName) {
        Preconditions.checkNotNull(objects);
        Preconditions.checkNotNull(indexName);

        BulkRequestBuilder bulkRequest = esClient.prepareBulk();
        for (Object o : objects) {
            bulkRequest.add(esClient.prepareIndex(indexName, o.getClass().getName())
                    .setSource(JSON.toJSONString(o), XContentType.JSON)
            );
        }

        BulkResponse response = bulkRequest.get();

        return Arrays.stream(response.getItems())
        .map(r -> r.status().getStatus()).collect(Collectors.toList());
    }

    public static void main(String[] args) {

    }
}
