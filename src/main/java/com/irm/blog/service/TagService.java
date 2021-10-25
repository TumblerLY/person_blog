package com.irm.blog.service;

import com.irm.blog.po.Tag;
import com.irm.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tumbler
 */

public interface TagService {

    Tag saveTag(Type type);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    Tag updateTag(Long id,Tag type);

    void deleteTag(Long id);
}
