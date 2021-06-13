package jgduhao.github.io.animerss.dao;

import jgduhao.github.io.animerss.model.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Long> {

    List<Category> findByCategoryVisible(int categoryVisible, Sort sort);

}
