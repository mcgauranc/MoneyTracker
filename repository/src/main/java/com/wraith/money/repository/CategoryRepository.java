package com.wraith.money.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.wraith.money.data.Category;

/**
 * This class provides for the database repository access for the Category entity.
 *
 * User: rowan.massey Date: 24/02/13 Time: 16:35
 */
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

	public List<Category> findByName(String categoryName);

}
