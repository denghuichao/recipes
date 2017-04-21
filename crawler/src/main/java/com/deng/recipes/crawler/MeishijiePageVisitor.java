package com.deng.recipes.crawler;

import com.deng.recipes.entity.CookStep;
import com.deng.recipes.entity.Recipe;
import com.deng.recipes.entity.RecipeEntity;
import net.vidageek.crawler.Page;
import net.vidageek.crawler.Url;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class MeishijiePageVisitor extends AbstractPageVisitor<RecipeEntity> {


    public MeishijiePageVisitor(BlockingQueue<RecipeEntity> blockingQueue) {
        super(blockingQueue);
    }

    public boolean followUrl(Url url) {
        return false;
    }

    protected RecipeEntity processPage(Page page) {

        if (!page.getUrl().contains("/zuofa/")) {
            return null;
        }

        Recipe recipe = new Recipe();

        Document doc = Jsoup.parse(page.getContent());
        Elements tmp = doc.select("h1"); // 标题
        String buffer = "";
        recipe.setName(tmp.text());

        tmp = doc.select("div.cp_headerimg_w > img"); // 成品图
        String srcUrl = tmp.attr("src");
        //if(srcUrl.startsWith(BASE_URL))srcUrl = BASE_URL + srcUrl;
        recipe.setImages(srcUrl);

        tmp = doc.select("span[class=icon icon_nd] ~ a"); // // 难度
        recipe.setRecruit(tmp.text());

        tmp = doc.select("li[class=w127 bb0]  a"); // //口味 li class="w127 bb0
        recipe.setTaste(tmp.text());

        tmp = doc.select("span[class=icon icon_zb] ~ a"); // // 准备时间
        recipe.setSetuptime(tmp.text());

        tmp = doc.select("span[class=icon icon_pr] ~ a"); // // 烹饪时间
        recipe.setCookingtime(tmp.text());

        tmp = doc.select("div[class=yl zl clearfix] h4"); // // 主料
        recipe.setMainingredient(tmp.text());

        tmp = doc.select("div[class=yl fuliao clearfix] a"); // // 配料
        if (tmp != null) {
            buffer = "";
            for (int i = 0; i < tmp.size(); i++) {
                buffer += tmp.get(i).text() + " ";
            }
            recipe.setIngredient(buffer);
        }

        tmp = doc.select("div.materials > p"); //功能
        recipe.setFuncational(tmp.text());

        recipe.setComment(page.getUrl()); //备注放url

        List<CookStep> cookSteps = new ArrayList<CookStep>();
        tmp = doc.select("em.step"); // 烹饪步骤
        if (tmp != null) {
            for (short i = 1; i <= tmp.size(); i++) {
                String text = tmp.get(i).parent().text();
                if (text != null && text.length() > 2) {
                    CookStep step = new CookStep();
                    step.setRecipeid(recipe.getRecipeid());
                    step.setSteporder(i);
                    step.setDescription(text);
                    cookSteps.add(step);
                }
            }
        }

        return new RecipeEntity(recipe, cookSteps);
    }
}
