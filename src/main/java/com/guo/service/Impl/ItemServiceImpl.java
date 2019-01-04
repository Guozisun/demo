package com.guo.service.Impl;

import com.guo.dao.ItemDOMapper;
import com.guo.dao.ItemStockDOMapper;
import com.guo.dao.UserDOMapper;
import com.guo.dao.UserPassWordDOMapper;
import com.guo.pojo.ItemDO;
import com.guo.pojo.ItemStockDO;
import com.guo.pojo.UserDO;
import com.guo.pojo.UserPassWordDO;
import com.guo.responese.ResponeseResult;
import com.guo.responese.errorResponese.BusinessException;
import com.guo.responese.errorResponese.EmBusinessError;
import com.guo.service.ItemService;
import com.guo.service.TestService;
import com.guo.service.model.ItemModel;
import com.guo.service.model.UserModel;
import com.guo.validator.ValidateResult;
import com.guo.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/20 17:33
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    ItemDOMapper itemDOMapper;
    @Autowired
    ItemStockDOMapper itemStockDOMapper;

    @Override
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {

      /*  ValidateResult validateResult =validator.validateResult(itemModel);
        if (validateResult.isHashErr()){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validateResult.getErrMsg());

        }*/
        //转换为
        ItemDO itemDO = converFromObject(itemModel);
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = converFromStockObject(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        //存入数据库

        //返回值
        return this.getItemById(itemModel.getId());
    }
    private ItemDO converFromObject(ItemModel itemModel) throws BusinessException {
        if (itemModel==null){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息错误");
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }
    private ItemStockDO converFromStockObject(ItemModel itemModel) throws BusinessException {
        if (itemModel==null){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息错误");
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> listItemDO = itemDOMapper.selectAll();
        List<ItemModel> itemModelList =listItemDO.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.converFromModel(itemDO,itemStockDO);
            return  itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if (itemDO==null){
            return  null;
        }
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        ItemModel itemModel = converFromModel(itemDO,itemStockDO);
        return itemModel;
    }

    private ItemModel converFromModel(ItemDO itemDO,ItemStockDO itemStockDO){
        if (itemDO==null){
            return null;
        }
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;

    }
}
