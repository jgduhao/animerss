package jgduhao.github.io.animerss.service;

import jgduhao.github.io.animerss.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategoryAllList();

    Page<Category> getCategoryByPage(int pageSize, int pageNum);

    List<Category> getCategoryListVisible();

    Optional<Category> getOneCategoryById(long categoryId);

    Category saveCategory(Category category);

    void deleteCategory(Category category);

    Category changeCategoryVisible(Long categoryId);


}
