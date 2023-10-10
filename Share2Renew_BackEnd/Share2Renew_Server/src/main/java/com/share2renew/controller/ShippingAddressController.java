package com.share2renew.controller;


import com.share2renew.exception.ParamsException;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.pojo.ShippingAddress;
import com.share2renew.service.IShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@RestController
@RequestMapping("/shippingAddress")
public class ShippingAddressController {

    @Autowired
    private IShippingAddressService shippingAddressService;

    /**
     * For adding new shipping information
     * @param shippingAddress
     * @return
     */
    @PostMapping("/addAddress")
    public GeneralBean addShippingAddress(@RequestBody ShippingAddress shippingAddress) throws ParamsException {
        return shippingAddressService.addShippingAddress(shippingAddress);
    }

    /**
     * Get all the address
     * @return
     */
    @GetMapping("/getAllAddress")
    public List<ShippingAddress> getALlShippingAddress() {
        return shippingAddressService.list();
    }

    @PostMapping("/updateAddress")
    public GeneralBean updateShippingAddress(@RequestBody ShippingAddress shippingAddress) {
        return shippingAddressService.updateShippingAddress(shippingAddress);
    }



}
