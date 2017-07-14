package com.deng.recipes.api.ws.dao;

import com.deng.recipes.api.entity.RecipeEntity;
import com.deng.recipes.api.entity.SearchRecipeResultInfo;
import com.deng.recipes.api.entity.subscriber.NumberSubscriberResultInfo;
import com.deng.recipes.api.entity.subscriber.RecipeSubscriberResultInfo;
import com.deng.recipes.api.utils.ConfigManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.ArrayList;

public class RecipesDAO {

    private static Client esClient;

    private static final String INDEX_NAME = "recipes";

    static {
        init();
    }

    private static void init() {
        try {
            esClient = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName(ConfigManager.instance().getProperty("es.host", "127.0.0.1")),
                            Integer.parseInt(ConfigManager.instance().getProperty("es.port", "9300"))));
        } catch (Exception e) {
            System.out.println("error when init es");
        }
    }

    public RecipeSubscriberResultInfo queryRecipeById(String id) {

        Preconditions.checkNotNull(id);

        RecipeSubscriberResultInfo resultInfo = new RecipeSubscriberResultInfo();

        SearchResponse response = esClient.prepareSearch(INDEX_NAME)
                .setTypes(RecipeEntity.class.getSimpleName())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("id", id))
                .setFetchSource(true)
                .get();


        resultInfo.setMsg(response.status().name());
        resultInfo.setRetCode(response.status().getStatus());
        if(response.status().equals(RestStatus.OK)) {
            resultInfo.setResult(getRecipeEntities(response));
        }

        return resultInfo;
    }

    public RecipeSubscriberResultInfo queryRecipes(String queryStr, int pageIndex, int pageSize) {

        Preconditions.checkNotNull(queryStr);
        Preconditions.checkArgument(pageIndex >= 0);
        Preconditions.checkArgument(pageSize > 0);

        RecipeSubscriberResultInfo resultInfo = new RecipeSubscriberResultInfo();

        SearchResponse response = esClient.prepareSearch(INDEX_NAME)
                .setTypes(RecipeEntity.class.getSimpleName())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setFetchSource(true)
                .setQuery(QueryBuilders.multiMatchQuery(
                        queryStr, "recipe.title", "recipe.desc"))
                .setMinScore(10.0f)
                .setFrom(pageIndex * pageSize)
                .setSize(pageSize)
                .get();

        resultInfo.setMsg(response.status().name());
        resultInfo.setRetCode(response.status().getStatus());
        if(response.status().equals(RestStatus.OK)) {
            resultInfo.setResult(getRecipeEntities(response));
        }

        return resultInfo;
    }

    public RecipeSubscriberResultInfo queryHotRecipes(int pageIndex, int pageSize) {

        Preconditions.checkArgument(pageIndex >= 0);
        Preconditions.checkArgument(pageSize > 0);

        RecipeSubscriberResultInfo resultInfo = new RecipeSubscriberResultInfo();

        SearchResponse response = esClient.prepareSearch(INDEX_NAME)
                .setTypes(RecipeEntity.class.getSimpleName())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setFetchSource(true)
                .addSort("recipe.likedNum", SortOrder.DESC)
                .setFrom(pageIndex * pageSize)
                .setSize(pageSize)
                .get();

        resultInfo.setMsg(response.status().name());
        resultInfo.setRetCode(response.status().getStatus());
        if(response.status().equals(RestStatus.OK)) {
            resultInfo.setResult(getRecipeEntities(response));
        }

        return resultInfo;
    }

    private SearchRecipeResultInfo getRecipeEntities(SearchResponse response) {
        SearchRecipeResultInfo result = new SearchRecipeResultInfo();
        ArrayList<RecipeEntity> res = Lists.newArrayList();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            //System.out.print(hit.getScore() + ":");
            ObjectMapper mapper = new ObjectMapper();
            RecipeEntity e = mapper.convertValue(hit.getSource(), RecipeEntity.class);
            //System.out.println(e.getRecipe().getTitle());
            res.add(e);
        }

        result.setTotal((int)response.getHits().getTotalHits());
        result.setList(res);

        return result;
    }

    public NumberSubscriberResultInfo incrNumberField(String fieldName, String id){
        NumberSubscriberResultInfo resultInfo = NumberSubscriberResultInfo.fail();

        Preconditions.checkNotNull(fieldName);

        UpdateRequest updateRequest = new UpdateRequest("recipes", RecipeEntity.class.getSimpleName(), id)
                .script(new Script("ctx._source."+fieldName+"+=1")).retryOnConflict(3);

        executeUpdate(resultInfo, updateRequest);

        return resultInfo;
    }

    public NumberSubscriberResultInfo decrNumberField(String fieldName, String id){
        NumberSubscriberResultInfo resultInfo = NumberSubscriberResultInfo.fail();

        Preconditions.checkNotNull(fieldName);

        UpdateRequest updateRequest = new UpdateRequest("recipes", RecipeEntity.class.getSimpleName(), id)
                .script(new Script("ctx._source."+fieldName+"-=1")).retryOnConflict(3);

        executeUpdate(resultInfo, updateRequest);

        return resultInfo;
    }

    private void executeUpdate(NumberSubscriberResultInfo resultInfo, UpdateRequest updateRequest) {
        try {
            UpdateResponse response = esClient.update(updateRequest).get();
            resultInfo.setMsg(response.status().name());
            resultInfo.setRetCode(response.status().getStatus());
            resultInfo.setResult(response.status().equals(RestStatus.OK) ? 1 : 0);
        }catch (Exception e){
            resultInfo.setMsg(e.getMessage());
        }
    }

    public NumberSubscriberResultInfo incrCookNum(String id){
        return incrNumberField("recipe.cookedNum", id);
    }

    public NumberSubscriberResultInfo decrCookNum(String id){
        return decrNumberField("recipe.cookedNum", id);
    }

    public NumberSubscriberResultInfo incrCollectionNum(String id){
        return incrNumberField("recipe.collectedNum", id);
    }

    public NumberSubscriberResultInfo decrLikeNum(String id){
        return decrNumberField("recipe.collectedNum", id);
    }

    public NumberSubscriberResultInfo incrLikeNum(String id){
        return incrNumberField("recipe.cookedNum", id);
    }

    public NumberSubscriberResultInfo decrCollectionNum(String id){
        return decrNumberField("recipe.cookedNum", id);
    }

    public static void main(String[] args)throws Exception {
        RecipesDAO recipesDAO = new RecipesDAO();
        recipesDAO.incrNumberField("recipe.cookedNum", "573F1A533DB4F6DE7CB4FE01E0BA0D07");
    }
}