package com.deng.recipes.api.ws.controller;

import com.deng.recipes.api.entity.subscriber.NumberSubscriberResultInfo;
import com.deng.recipes.api.entity.subscriber.RecipeSubscriberResultInfo;
import com.deng.recipes.api.ws.dao.RecipesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/restapi")
public class SearchRestController {

	private static final RecipesDAO recipesDAO = new RecipesDAO();

	@GetMapping("/recipes/recommendation")
	public RecipeSubscriberResultInfo getRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size) {
		return recipesDAO.queryHotRecipes(page_index, page_size);
	}

	@GetMapping("/recipes/search")
	public RecipeSubscriberResultInfo searchRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size,String queryString){
		return recipesDAO.queryRecipes(queryString, page_index, page_size);
	}

	@PutMapping("/recipes/collections/addition")
	public NumberSubscriberResultInfo increaseCollection(@RequestParam String id){
		return recipesDAO.incrCollectionNum(id);
	}

	@PutMapping("/recipes/collections/reduction")
	public NumberSubscriberResultInfo decreaseCollections(@RequestParam String id){
		return recipesDAO.decrCollectionNum(id);
	}

	@PutMapping("/recipes/likeness/addition")
	public NumberSubscriberResultInfo increaseLikeness(@RequestParam String id){
		return recipesDAO.incrLikeNum(id);
	}

	@PutMapping("/recipes/likeness/reduction")
	public NumberSubscriberResultInfo decreaseLikeness(@RequestParam String id){
		return recipesDAO.decrLikeNum(id);
	}

	@PutMapping("/recipes/cookness/addition")
	public NumberSubscriberResultInfo increaseCookness(@RequestParam String id){
		return recipesDAO.incrCookNum(id);
	}

    @PutMapping("/recipes/cookness/reduction")
    public NumberSubscriberResultInfo decreaseCookness(@RequestParam String id){
        return recipesDAO.decrCookNum(id);
    }
}