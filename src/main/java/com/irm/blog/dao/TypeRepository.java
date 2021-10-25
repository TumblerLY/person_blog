package com.irm.blog.dao;

import com.irm.blog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    List<Type> findByName(String name);
    boolean deleteById(Long id);

}
