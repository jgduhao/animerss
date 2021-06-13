package jgduhao.github.io.animerss.service.impl;

import jgduhao.github.io.animerss.enums.Consts;
import jgduhao.github.io.animerss.enums.ErrorMsg;
import jgduhao.github.io.animerss.exception.SelfException;
import jgduhao.github.io.animerss.model.Category;
import jgduhao.github.io.animerss.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;


    @Test
    void getCategoryAllList() {
        System.out.println(categoryService.getCategoryAllList());
    }

    @Test
    void getCategoryListVisible() {
        List<Category> categoryList = categoryService.getCategoryListVisible();
        System.out.println(categoryList);
    }

    @Test
    void getOneCategoryById() {
       Category category = categoryService.getOneCategoryById(2L)
               .orElseThrow(() -> new SelfException(ErrorMsg.CategoryNotExists));
       System.out.println(category);
    }

    @Test
    void saveCategory() {
        Date dateNow = new Date();
        Category category = new Category();
        category.setCategoryName("测试类别4");
        category.setCategoryCreateDate(dateNow);
        category.setCategoryVisible(Consts.CATEGORY_VISIBLE);
        category.setCategoryUpdateDate(dateNow);
        categoryService.saveCategory(category);

        Date dateNow2 = new Date();
        Category category2 = new Category();
        category2.setCategoryName("测试类别5");
        category2.setCategoryCreateDate(dateNow2);
        category2.setCategoryVisible(Consts.CATEGORY_VISIBLE);
        category2.setCategoryUpdateDate(dateNow2);
        categoryService.saveCategory(category2);

        Date dateNow3 = new Date();
        Category category3 = new Category();
        category3.setCategoryName("测试类别6");
        category3.setCategoryCreateDate(dateNow3);
        category3.setCategoryVisible(Consts.CATEGORY_INVISIBLE);
        category3.setCategoryUpdateDate(dateNow3);
        categoryService.saveCategory(category3);
    }

    @Test
    void changeCategoryVisible() {
        Category category = categoryService.changeCategoryVisible(2L);
        System.out.println(category);
    }

    @Test
    void getCategoryByPage() {
        Page<Category> page = categoryService.getCategoryByPage(2, 1);
        System.out.println(page.toList());
    }
}