package com.irm.blog.dao;

import com.irm.blog.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlogRepostory extends JpaRepository<Blog, Long> , JpaSpecificationExecutor<Blog> {





}
