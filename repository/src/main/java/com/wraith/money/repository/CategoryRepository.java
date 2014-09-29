package com.wraith.money.repository;

import com.wraith.money.data.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:35
 */
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    public List<Category> findByName(String categoryName);

}