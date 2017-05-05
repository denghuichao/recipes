package com.deng.recipes.api.ws.controller;

import java.util.List;
import java.util.Map;
import com.deng.recipes.api.entity.RecipeEntity;
import com.deng.recipes.api.ws.dao.RecipesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CustomerRestController {

	@Autowired
	RecipesDAO recipesDAO;

	@GetMapping("/recipes/recommendation")
	public List<RecipeEntity> getRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size) {
		System.out.println("pageIndex: "+page_index+" pageSize= "+page_size);
		return recipesDAO.getRecipes(page_index, page_size);
	}

	@GetMapping("/recipes")
	public List<RecipeEntity> getRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size, @RequestParam Map<String, Object> params) {
		System.out.println("pageIndex: "+page_index+" pageSize= "+page_size+" params= "+params);
		return recipesDAO.getRecipes(page_index, page_size);
	}

	@GetMapping("/recipes/search")
	public List<RecipeEntity> searchRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size,String queryString){
		return recipesDAO.getRecipes(page_index, page_size);
	}

	@GetMapping("recipes/collections")
	public int getCollectionCount(@RequestParam String id){
		return 1000;
	}

	@PutMapping("/recipes/collections/addition")
	public int increaseCollection(@RequestParam String id){
		return 1000;
	}

	@PutMapping("/recipes/collections/reduction")
	public int decreaseCollections(@RequestParam String id){
		return 999;
	}

	@GetMapping("recipes/likeness")
	public int getLikeCount(@RequestParam String id){
		return 999;
	}

	@PutMapping("/recipes/likeness/addition")
	public int increaseLikeness(@RequestParam String id){
		return 1000;
	}

	@PutMapping("/recipes/likeness/reduction")
	public int decreaseLikeness(@RequestParam String id){
		return 999;
	}

	@GetMapping("recipes/cookness")
	public int getCooknessCount(@RequestParam String id){
		return 1000;
	}

	@PutMapping("/recipes/cookness/addition")
	public int increaseCookness(@RequestParam String id){
		return 999;
	}
}