package com.share2renew.service.Impl;

import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.ShippingAddress;
import com.share2renew.mapper.ShippingAddressMapper;
import com.share2renew.service.IShippingAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share2renew.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@Service
public class ShippingAddressServiceImpl extends ServiceImpl<ShippingAddressMapper, ShippingAddress> implements IShippingAddressService {

    @Autowired
    private ShippingAddressMapper shippingAddressMapper;
    @Autowired
    private IUserService userService;

    /**
     * For adding shipping information
     * @param shippingAddress
     * @return
     */
    @Override
    public GeneralBean addShippingAddress(ShippingAddress shippingAddress) throws ParamsException {

        // 前端完成所有健壮性判断
        // Set the validity
        shippingAddress.setValidity(1);
        // Get current userId
        int currentUserId = userService.getCurrentUserId();
        shippingAddress.setUserId(currentUserId);
        int insert = shippingAddressMapper.insert(shippingAddress);
        if (insert == 1) {
            return GeneralBean.success("Insert shipping information successfully");
        }
        return GeneralBean.error("Insert failed, please try again");
    }

    @Override
    public GeneralBean updateShippingAddress(ShippingAddress shippingAddress) {
        int result = shippingAddressMapper.updateById(shippingAddress);
        if (result == 1) {
            return GeneralBean.success("Update shippingAddress successfully");
        }
        return GeneralBean.error("Update shippingAddress failed");
    }

    @Override
    public GeneralBean deleteShippingAddress(int addressId) {
        shippingAddressMapper.deleteById(addressId);
        return GeneralBean.success("Delete successfully");
    }

}
