package com.irm.blog.service;

import com.irm.blog.NotFoundException;
import com.irm.blog.dao.TypeRepository;
import com.irm.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class TypeSreviceImp implements TypeService {


    @Autowired
    private TypeRepository typeRepository;


    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return  typeRepository.findOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
         return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1=typeRepository.findOne(id);
        if (type1==null){
            throw new NotFoundException("不存在该类型");
        }
        //将更新的数据重新转换到type类型里面
        BeanUtils.copyProperties(type,type1);
        return typeRepository.save(type1);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
          typeRepository.delete(id);
    }

    @Override
    public List<Type> FindTypeByName(String name) {
        return typeRepository.findByName(name);
    }

}
