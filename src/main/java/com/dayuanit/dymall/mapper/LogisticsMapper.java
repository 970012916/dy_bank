package com.dayuanit.dymall.mapper;

import com.dayuanit.dymall.domain.Logistics;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticsMapper {
    List<Logistics> listLogistics();
    Logistics getLogistics(int id);

}
