package com.ronakcode.onlinelibrary.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequestDTO {
    private Date startDate;
    private Date endDate;
    private List<String> bookIds;
}
