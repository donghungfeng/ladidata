package com.example.ladi.service;

import java.util.List;


public interface BaseService<T> {
    public List<T> getAll();
    public T create(T t);
    public T update(T t);
    public T getById(int id);
}
