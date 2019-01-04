package com.guo.service;

import com.guo.responese.errorResponese.BusinessException;
import com.guo.service.model.ItemModel;
import com.guo.service.model.UserModel;

import java.util.List;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/20 17:32
 */
public interface ItemService {
   //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    //商品列表浏览
    List<ItemModel> listItem();
    //商品详情浏览
    ItemModel getItemById(Integer id);
}
