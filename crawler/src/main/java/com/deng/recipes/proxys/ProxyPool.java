package com.deng.recipes.proxys;

import java.util.List;

/**
 * Created by hcdeng on 2017/4/28.
 */
public interface ProxyPool {
    /**
     * 从代理池获取一个代理
     */
    public Proxy getProxy();

    /**
     * 从代理池获取<p>numOfProxys</p>个代理
     * @param numOfProxys 请求代理数量
     */
    public List<Proxy> getProxys(int numOfProxys);
}
