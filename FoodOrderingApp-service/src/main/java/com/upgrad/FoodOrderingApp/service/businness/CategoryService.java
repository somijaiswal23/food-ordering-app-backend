package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    /**
     * This method implements the business logic for 'category' endpoint
     *
     * @return
     */
    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        return categoryDao.getAllCategories().stream()
                .sorted(Comparator.comparing(CategoryEntity::getCategoryName))
                .collect(Collectors.toList());
    }
}
