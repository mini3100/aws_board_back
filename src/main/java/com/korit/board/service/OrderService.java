package com.korit.board.service;

import com.korit.board.dto.OrderReqDto;
import com.korit.board.dto.ProductRespDto;
import com.korit.board.repository.OrderMapper;
import com.korit.board.repository.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;

    public boolean order(OrderReqDto orderReqDto) {
        return orderMapper.saveOrder(orderReqDto.toOrderEntity()) > 0;
    }
}
