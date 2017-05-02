package com.deng.recipes.api.ws.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.deng.recipes.api.entity.Recipe;
import com.deng.recipes.api.entity.RecipeEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CustomerRestController {

	@GetMapping("/recipes/recommendation")
	public List<RecipeEntity> getRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size) {
		System.out.println("pageIndex: "+page_index+" pageSize= "+page_size);
		return Arrays.asList(new RecipeEntity(new Recipe(), new ArrayList<>()));
	}

	@GetMapping("/recipes")
	public List<RecipeEntity> getRecipes(@RequestParam Integer page_index, @RequestParam Integer page_size, @RequestParam Map<String, Object> params) {
		System.out.println("pageIndex: "+page_index+" pageSize= "+page_size+" params= "+params);
		return Arrays.asList(new RecipeEntity(new Recipe(), new ArrayList<>()));
	}

	@GetMapping("recipes/collections/{id}")
	public int getCollectionCount(@PathVariable String id){
		return 1000;
	}

	@PutMapping("/recipes/collections/addition/{id}")
	public int increaseCollection(@PathVariable String id){
		return 1000;
	}

	@PutMapping("/recipes/collections/reduction/{id}")
	public int decreaseCollections(@PathVariable String id){
		return 999;
	}

	@GetMapping("recipes/likeness/{id}")
	public int getLikeCount(@PathVariable String id){
		return 999;
	}

	@PutMapping("/recipes/likeness/addition/{id}")
	public int increaseLikeness(@PathVariable String id){
		return 1000;
	}

	@PutMapping("/recipes/likeness/reduction/{id}")
	public int decreaseLikeness(@PathVariable String id){
		return 999;
	}

	@GetMapping("recipes/cookness/{id}")
	public int getCooknessCount(@PathVariable String id){
		return 1000;
	}

	@PutMapping("/recipes/cookness/addition/{id}")
	public int increaseCookness(@PathVariable String id){
		return 999;
	}
}