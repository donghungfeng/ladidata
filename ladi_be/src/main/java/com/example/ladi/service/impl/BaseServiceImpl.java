package com.example.ladi.service.impl;

import com.example.ladi.repository.BaseRepository;
import com.example.ladi.service.BaseService;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected abstract BaseRepository<T> getRepository();
    @Override
    public List<T> getAll() {
        return this.getRepository().findAll();
    }

    @Override
    public T create(T t) {
        return this.getRepository().save(t);
    }

    @Override
    public T update(T t) throws NoSuchAlgorithmException {
        return this.getRepository().save(t);
    }
    @Override
    public T getById(int id) {
        return this.getRepository().findAllById(id);
    }

    @Override
    public String deleteById(int id){
        T t = this.getRepository().findAllById(id);
        this.getRepository().delete(t);
        return "delete success";
    }


}
