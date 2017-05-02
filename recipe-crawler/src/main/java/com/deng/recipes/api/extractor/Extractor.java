package com.deng.recipes.api.extractor;

/**
 * Created by hcdeng on 2017/4/26.
 */
public interface Extractor<T, M> {
    public T extract(M content);
}
