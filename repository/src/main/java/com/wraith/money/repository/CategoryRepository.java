package com.wraith.money.repository;

import com.wraith.money.data.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This class provides for the database repository access for the Category entity.
 *
 * User: rowan.massey Date: 24/02/13 Time: 16:35
 */
@RepositoryRestResource
public interface CategoryRepository extends MongoRepository<Category, Long> {

	public Page<Category> findByName(String categoryName, Pageable pageable);

}
