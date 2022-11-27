package com.example.ladi.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
@AllArgsConstructor
@Getter
@Setter
public class AssignWorkRequest {
    int id;
//    List<DataRequest> dataList;
}
