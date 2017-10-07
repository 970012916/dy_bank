package com.dayuanit.dymall.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.dymall.domain.GoodsCategory;
import com.dayuanit.dymall.mapper.GoodsCategoryMapper;

import junit.framework.Assert;

@ContextConfiguration("/spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GoodsCategoryTest {

	@Autowired
	private GoodsCategoryMapper goodsCatetoryMapper;
	
	
	@Test
	public void testGetCategoryStatus() {
		GoodsCategory goodsCategory = goodsCatetoryMapper.getCategoryStatus("1");
		int id = goodsCategory.getId();
		Assert.assertEquals(1, id);
	}
}
