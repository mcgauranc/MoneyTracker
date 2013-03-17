package com.wraith.repository;

import com.wraith.repository.entity.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 16:35
 */
public interface CategoryRepository  extends CrudRepository<Category, Integer> {
}
