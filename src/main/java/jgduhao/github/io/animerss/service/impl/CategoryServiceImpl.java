package jgduhao.github.io.animerss.service.impl;

import jgduhao.github.io.animerss.dao.CategoryDao;
import jgduhao.github.io.animerss.enums.Consts;
import jgduhao.github.io.animerss.enums.ErrorMsg;
import jgduhao.github.io.animerss.exception.SelfException;
import jgduhao.github.io.animerss.model.Category;
import jgduhao.github.io.animerss.service.CategoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private Log log = LogFactory.getLog(CategoryServiceImpl.class);

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> getCategoryAllList() {
        return categoryDao.findAll();
    }

    @Override
    public Page<Category> getCategoryByPage(int pageSize, int pageNum) {
        return categoryDao.findAll(PageRequest.of(pageNum-1, pageSize, Sort.by(Sort.Order.desc("categoryUpdateDate"))));
    }

    @Override
    public List<Category> getCategoryListVisible() {
        return categoryDao.findByCategoryVisible(Consts.CATEGORY_VISIBLE,
                Sort.by(Sort.Order.desc("categoryUpdateDate")));
    }

    @Override
    public Optional<Category> getOneCategoryById(long categoryId) {
        return categoryDao.findById(categoryId);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public Category changeCategoryVisible(Long categoryId) {
        Category category = categoryDao.findById(categoryId)
                .orElseThrow(() -> new SelfException(ErrorMsg.CategoryNotExists));
        if(Consts.CATEGORY_VISIBLE == category.getCategoryVisible()){
            category.setCategoryVisible(Consts.CATEGORY_INVISIBLE);
        } else {
            category.setCategoryVisible(Consts.CATEGORY_VISIBLE);
        }
        return categoryDao.save(category);
    }

    @Override
    public void deleteCategory(Category category) {

    }
}
