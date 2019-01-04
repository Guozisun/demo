package com.guo.controller;

import com.guo.controller.viewObject.ItemVO;
import com.guo.responese.ResponeseResult;
import com.guo.responese.errorResponese.BusinessException;
import com.guo.service.ItemService;
import com.guo.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.guo.controller.BaseController.CONTENT_VALUE;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/25 11:03
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/insert",method = RequestMethod.POST,consumes = {CONTENT_VALUE})
    public ResponeseResult insert(@RequestParam(name = "title") String title,
                                  @RequestParam(name = "price") BigDecimal price,
                                  @RequestParam(name = "description") String description,

                                  @RequestParam(name = "stock") Integer stock,
                                  @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setDescription(description);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModelDD=itemService.createItem(itemModel);
        ItemVO itemVO = converFromModel(itemModelDD);
        return ResponeseResult.create(itemVO);
    }
    private ItemVO converFromModel(ItemModel itemModel){
        if (itemModel==null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        return itemVO;
    }
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ResponeseResult getById(@RequestParam(name = "id") Integer id){
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = converFromModel(itemModel);
        return ResponeseResult.create(itemVO);
    }
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public ResponeseResult getAll(){
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.converFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return ResponeseResult.create(itemVOList);
    }
}
