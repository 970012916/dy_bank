package com.dayuanit.dymall.controller;

import com.dayuanit.dymall.dao.GoodsServiceDao;
import com.dayuanit.dymall.dao.ShoppingCarDao;
import com.dayuanit.dymall.domain.*;
import com.dayuanit.dymall.dto.AjaxResultDTO;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.vo.ShoppingCarVO;
import com.mysql.jdbc.log.Log;
import com.qq.connect.utils.json.HTTP;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController{

	@Autowired
	private GoodsServiceDao goodsServiceDao;
	
	@Autowired
	private ShoppingCarDao shoppingCarDao;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;


	private static final Logger log = LoggerFactory.getLogger(GoodsController.class);
	
	@RequestMapping("/getGoods")
	@ResponseBody
	public AjaxResultDTO getGoods(int categoryId) {

		if(categoryId <=0) {
			throw new DyMallException("类目不合法");
		}
		try {
			List<Goods> goodsList= goodsServiceDao.listGoods(categoryId);
			return AjaxResultDTO.success(goodsList);
		} catch (DyMallException e) {
			e.printStackTrace();
			return AjaxResultDTO.failed(e.getMessage());
		}

	}
	
	
	@RequestMapping("/getCategorys")
	@ResponseBody
	public AjaxResultDTO getCategory() {
		try {
			List<GoodsCategory> categoryList = goodsServiceDao.listGoodsCategory();
			return AjaxResultDTO.success(categoryList);
		} catch (DyMallException e) {
			e.printStackTrace();
			return AjaxResultDTO.failed(e.getMessage());
		}
	}
	
	@RequestMapping("/readImg")
	public void readImg(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String root = req.getServletContext().getRealPath("/");
		String filePath = root + "/IndexImg";
		String imgName = req.getParameter("action");
		String imgFullName = filePath + "/" + imgName+".jpg";
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			File file = new File(imgFullName);
			if (!file.exists()) {
				throw new DyMallException("goodsImg not exist");
			}
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream((resp.getOutputStream()));
			byte[] buff = new byte[1024];
			int length = -1;
			while (-1 != (length=(bis.read(buff)))) {
				bos.write(buff, 0,length);
				bos.flush();	
			}
		} catch (Exception e) {
			if(null != bis) {
				bis.close();
			}   
			if(null != bos) {
				bos.close();
			}
		}
	}
	
	
	@RequestMapping("/toGoodsBref")
	public String toGoodsBref(Integer goodsId,HttpSession session,HttpServletRequest req) {
		String id = req.getParameter("action");
		if(StringUtils.isBlank(id) || StringUtils.isNumeric(id)) {

		}
		String token = UUID.randomUUID().toString();
		req.setAttribute("goodsId", Integer.parseInt(id));
		session.setAttribute("cart_token", token);
		return "commodity_introduce";
	}
	
	@RequestMapping("/getGoodsBrief")
	@ResponseBody
	public AjaxResultDTO getGoodsBrief(int id) {
		try {
			Goods goods = goodsServiceDao.getOneGoods(id);
			return AjaxResultDTO.success(goods);
		} catch (DyMallException e) {
			return  AjaxResultDTO.failed(e.getMessage());
		}
	}
	
	@RequestMapping("/toGoodsCar")
	public String toGoodsCar() {
		return "car";
	}
	
	@RequestMapping("/insertIntoCar")
	@ResponseBody
	public AjaxResultDTO insertIntoCar(ShoppingCarVO shoppingCarVO,HttpServletRequest req) {
		
		if(null == shoppingCarVO) {
			throw new DyMallException("购物车信息不合法");
		}
		//token什么作用
		String cartToken = shoppingCarVO.getCartToken();
		log.info(">>>>>>" + cartToken);
		String cartKey = "%s:%s";
		final String key = String.format(cartKey, getUser(req).getId(), String.valueOf(shoppingCarVO.getGoodsId()));

		
		int goodsId = shoppingCarVO.getGoodsId();
		int amount = shoppingCarVO.getGoodsAmout();

		User user = getUser(req);
		int userId = user.getId();
		try {
			
			boolean setFlag = redisTemplate.execute(new RedisCallback<Boolean>() {
			
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.setNX(key.getBytes(), "xxx".getBytes());
				}
			});
			
			if (!setFlag) {
				return AjaxResultDTO.failed("重复提交，稍后再试");
			}
			shoppingCarDao.insertGoods(goodsId, userId, amount);
			return AjaxResultDTO.success();
		} catch (DyMallException e) {
			return AjaxResultDTO.failed(e.getMessage());
		} catch (Exception e) {
			log.error("购物车增加商品异常信息{}", e.getMessage(), e);
			return AjaxResultDTO.failed("系统异常  请联系客服");
		} finally {
			boolean flag = redisTemplate.expire(key, 10, TimeUnit.SECONDS);
			System.out.println(">>>>>>>>" + flag);
		//	redisTemplate.opsForValue().set(key, "nnn", 5,TimeUnit.SECONDS);
		}
	}
	
	
	
	@RequestMapping("/listShoppingCar")
	@ResponseBody
	public AjaxResultDTO listShoppingCar(HttpServletRequest req) {
		try {
			User user = getUser(req);
			Map<String , Object> map = shoppingCarDao.listCars(user.getId());
			Object obj = map.get("carList");
			List<ShoppingCar> carList = new ArrayList<ShoppingCar>();
			if(obj instanceof List<?>) {
				carList = (List<ShoppingCar>)obj;
			}
			List<Goods> goodsList = new ArrayList<Goods>(carList.size());
			for(ShoppingCar shoppingCar: carList) {
				int goodsId = shoppingCar.getGoodsId();
				Goods goods = goodsServiceDao.getOneGoods(goodsId);
				goodsList.add(goods);
			}
			map.put("goodsList", goodsList);
			return AjaxResultDTO.success(map);
		} catch (DyMallException e) {
			return AjaxResultDTO.failed(e.getMessage());
		}
	}
	
	
	
}
