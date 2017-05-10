package com.deng.recipes.api.persit;

import com.alibaba.fastjson.JSON;
import com.deng.recipes.api.persit.utils.ESUtils;
import com.deng.recipes.api.utils.ConfigManager;
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
public class ESPersistor extends Persistor {

    private static Client esClient;

    static  {
        try {
            esClient = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName(ConfigManager.instance().getProperty("es.host", "127.0.0.1")),
                            Integer.parseInt(ConfigManager.instance().getProperty("es.port", "9300"))));
        } catch (Exception e) {
            System.out.println("error when init es");
            throw new RuntimeException(e);
        }
    }

    public IndexResponse indexObject(Object object, String indexName) {
        return indexObject(object, indexName, object.getClass().getTypeName());
    }

    public IndexResponse indexObject(Object object, String indexName, String typeName) {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(indexName);
        Preconditions.checkNotNull(typeName);

        IndexResponse response = esClient.prepareIndex(indexName, typeName, ESUtils.getId(object))
                .setSource(JSON.toJSONString(object), XContentType.JSON).get();

        System.out.println("index: " + response.getResult());
        return response;
    }

    public BulkResponse indexBatch(List<?> objects, String indexName) {
        String typeName = null;
        try {
            objects.getClass().getMethod("get", Integer.class)
                    .getReturnType().getTypeName();
        } catch (NoSuchMethodException e) {
        }

        return indexBatch(objects, indexName, typeName);
    }

    public BulkResponse indexBatch(List<?> objects, String indexName, String typeName) {
        Preconditions.checkNotNull(objects);
        Preconditions.checkNotNull(indexName);
        Preconditions.checkNotNull(typeName);

        BulkRequestBuilder bulkRequest = esClient.prepareBulk();
        for (Object o : objects) {
            bulkRequest.add(esClient.prepareIndex(indexName, typeName, ESUtils.getId(o))
                    .setSource(JSON.toJSONString(o), XContentType.JSON)
            );
        }
        BulkResponse response = bulkRequest.get();

        return response;
    }

    @Override
    public boolean save(Object o, String dbName, String typeName) {
        int code = indexObject(o, dbName, typeName).status().getStatus();
        return isSuccess(code);
    }

    @Override
    public List<Boolean> saveAll(List<?> collection, String dbName, String typeName) {
        BulkResponse responses = indexBatch(collection, dbName, typeName);

        return Arrays.stream(responses.getItems())
                .map(item -> isSuccess(item.status().getStatus()))
                .collect(Collectors.toList());
    }

    private boolean isSuccess(int code) {
        return code >= 200 && code < 300;
    }

}
