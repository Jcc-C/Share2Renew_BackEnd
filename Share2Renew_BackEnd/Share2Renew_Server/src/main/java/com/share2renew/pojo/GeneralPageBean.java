package com.share2renew.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: Share2Renew_BackEnd
 * @description:
 * @author: Junxian Cai
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralPageBean {

    /**
     * Total number of data
     */
    private Long total;

    /**
     * data
     */
    private List<?> data;
}
