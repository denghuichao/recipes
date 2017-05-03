package com.deng.recipes.api.ws.dao;

import com.alibaba.fastjson.JSON;
import com.deng.recipes.api.entity.RecipeEntity;
import com.deng.recipes.api.utils.FileUtils;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecipesDAO {
	public List<RecipeEntity> getRecipes(int pageIndex, int pageSize){
		Preconditions.checkArgument(pageIndex >= 0 );
		Preconditions.checkArgument(pageSize > 0);

		return readFromFile();
	}

	private List<RecipeEntity> readFromFile(){
		File dir = new File("D:\\recipes\\com.deng.recipes.api.entity.RecipeEntity");
		List<RecipeEntity> res = new ArrayList<>();
		for(File f: dir.listFiles()){
			res.add(readFromFile(f.getAbsolutePath()));
		}

		return res;
	}
	private RecipeEntity readFromFile(String fullPath){
		RecipeEntity recipeEntity = null;
		try {
			String json = FileUtils.readFile(fullPath);
			recipeEntity = JSON.parseObject(json).toJavaObject(RecipeEntity.class);
			recipeEntity.setID(FileUtils.getMD5(
					JSON.toJSONString(recipeEntity.getRecipe()) +
							JSON.toJSONString(recipeEntity.getCookSteps())));

		}catch (IOException e){}

		return recipeEntity;
	}
}