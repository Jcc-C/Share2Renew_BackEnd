package com.share2renew.service;

import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.ShippingAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.security.Principal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface IShippingAddressService extends IService<ShippingAddress> {

    /**
     * For adding shipping information
     * @param shippingAddress
     * @return
     */
    GeneralBean addShippingAddress(ShippingAddress shippingAddress) throws ParamsException;

    GeneralBean updateShippingAddress(ShippingAddress shippingAddress);
}
