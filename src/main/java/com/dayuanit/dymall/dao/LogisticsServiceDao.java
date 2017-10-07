package com.dayuanit.dymall.dao;

import com.dayuanit.dymall.domain.Logistics;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticsServiceDao {
    List<Logistics> listLogistics();
}
