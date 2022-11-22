package com.example.ladi.repository;

import com.example.ladi.model.Config;

public interface ConfigRepository extends BaseRepository<Config> {
    public Config findAllByCode(String code);
}
