package com.example.ladi.repository;

import com.example.ladi.model.Account;
import com.example.ladi.model.Data;
import com.example.ladi.model.QAccount;
import com.example.ladi.model.QData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.annotation.Annotation;
import java.util.List;

@Repository
public class CustomDateRepositoryImpl implements CustomDataRepository{

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Data> finDataByConditions(String status, String startDate, String endDate, Account account) {
        JPAQuery<Data> queryData = new JPAQuery<>(entityManager);
        QData qData = QData.data;
        BooleanBuilder filter = new BooleanBuilder();
        String[] statusArray = status.split(",");

        for (String item : statusArray){
            filter.or(qData.status.eq(Integer.parseInt(item)));
        }
        filter.and(qData.date.loe(Long.parseLong(endDate)));
        filter.and(qData.date.goe(Long.parseLong(startDate)));
        if (account != null) {
            filter.and(qData.account.eq(account));
        }
        List<Data> dataList = queryData.from(qData).where(filter).fetch();
        System.out.println(dataList);
        return dataList;
    }


    @Override
    public String value() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
