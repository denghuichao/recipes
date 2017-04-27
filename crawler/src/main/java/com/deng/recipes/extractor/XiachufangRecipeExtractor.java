package com.deng.recipes.extractor;

import com.deng.recipes.entity.RecipeEntity;
import com.google.common.base.Preconditions;

/**
 * Created by hcdeng on 2017/4/27.
 */
public class XiachufangRecipeExtractor extends RecipeExtractor{
    @Override
    public RecipeEntity extract(String content) {
        Preconditions.checkNotNull(content);
        return null;
    }
}
