package com.korit.board.repository;

import com.korit.board.entity.Order;
import com.korit.board.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    public int saveOrder(Order order);
}
