package com.deng.recipes.api.ws.controller;

import java.util.List;
import java.util.Map;
import com.deng.recipes.api.entity.RecipeEntity;
import com.deng.recipes.api.entity.subscriber.RecipeSubscriberResultInfo;
import com.deng.recipes.api.ws.dao.RecipesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CustomerRestController {

	@Autowired
	RecipesDAO recipesDAO;

	@GetMapping("/recipes/recommendation")
	public RecipeSubscriberResultInfo getRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size) {
		System.out.println("pageIndex: "+page_index+" pageSize= "+page_size);
		return null;
	}

	@GetMapping("/recipes")
	public RecipeSubscriberResultInfo getRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size, @RequestParam Map<String, Object> params) {
		System.out.println("pageIndex: "+page_index+" pageSize= "+page_size+" params= "+params);
		return null;
	}

	@GetMapping("/recipes/search")
	public RecipeSubscriberResultInfo searchRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size,String queryString){
		return null;
	}

	@GetMapping("recipes/collections")
	public RecipeSubscriberResultInfo getCollectionCount(@RequestParam String id){
		return null;
	}

	@PutMapping("/recipes/collections/addition")
	public RecipeSubscriberResultInfo increaseCollection(@RequestParam String id){
		return null;
	}

	@PutMapping("/recipes/collections/reduction")
	public RecipeSubscriberResultInfo decreaseCollections(@RequestParam String id){
		return null;
	}

	@GetMapping("recipes/likeness")
	public RecipeSubscriberResultInfo getLikeCount(@RequestParam String id){
		return null;
	}

	@PutMapping("/recipes/likeness/addition")
	public RecipeSubscriberResultInfo increaseLikeness(@RequestParam String id){
		return null;
	}

	@PutMapping("/recipes/likeness/reduction")
	public RecipeSubscriberResultInfo decreaseLikeness(@RequestParam String id){
		return null;
	}

	@GetMapping("recipes/cookness")
	public RecipeSubscriberResultInfo getCooknessCount(@RequestParam String id){
		return null;
	}

	@PutMapping("/recipes/cookness/addition")
	public RecipeSubscriberResultInfo increaseCookness(@RequestParam String id){
		return null;
	}
}