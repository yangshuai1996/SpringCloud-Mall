package com.estone.it.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.estone.it.stock.entity.TStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * * @author lidong
 *
 * @since 2019-09-04
 */
@Mapper
public interface TStockMapper extends BaseMapper<TStock> {

    /**
     * 扣减商品库存
     *
     * @Param: commodityCode 商品code  count扣减数量
     * @Return:
     */
    int decreaseStock(@Param("commodityCode") String commodityCode, @Param("count") Integer count);
}
