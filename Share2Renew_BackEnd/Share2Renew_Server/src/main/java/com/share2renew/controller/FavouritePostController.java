package com.share2renew.controller;


import com.share2renew.pojo.GeneralBean;
import com.share2renew.service.IFavouritePostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@RestController
@RequestMapping("/favouritePost")
public class FavouritePostController {
    @Autowired
    private IFavouritePostService iFavouritePostService;

    @PostMapping("/addTofavourite")
    @ApiOperation("Add a post to favourite")
    public GeneralBean AddToFavourite(@RequestParam(value = "userId") Integer userId,
                                      @RequestParam(value = "postId") Integer postId){
        return iFavouritePostService.AddTofavourite(userId,postId);
    }

    @GetMapping("getFavouriteList")
    @ApiOperation("Get the favourite list")
    public GeneralBean GetFavouriteList(@RequestParam(value = "userId") Integer userId,
                                        @RequestParam(value = "title", required = false) String title){
        return iFavouritePostService.GetFavouriteList(userId,title);
    }

    @DeleteMapping("/deleteFavourite")
    @ApiOperation("Delete a favoutire post")
    public GeneralBean DeleteFavourite(@RequestParam(value = "userId") Integer userId,
                                       @RequestParam(value = "postId") Integer postId){
        return iFavouritePostService.DeleteFavourite(userId,postId);
    }
}
