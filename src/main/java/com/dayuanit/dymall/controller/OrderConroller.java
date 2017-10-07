package com.dayuanit.dymall.controller;

import com.dayuanit.dymall.dao.OrderServiceDao;
import com.dayuanit.dymall.dao.ShoppingCarDao;
import com.dayuanit.dymall.domain.Order;
import com.dayuanit.dymall.dto.AjaxResultDTO;
import com.dayuanit.dymall.dto.OrderGoodsInfDTO;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.imp.RedisTemplateServiceImp;
import com.dayuanit.dymall.vo.OrderVO;
import com.dayuanit.dymall.vo.ShopCarOrderVO;
import com.dayuanit.pay.domain.PayOrder;
import com.dayuanit.pay.domain.PayResultInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("order")
public class OrderConroller extends  BaseController{


    private static final Logger log = LoggerFactory.getLogger(OrderConroller.class);

    @Autowired
    private OrderServiceDao orderServiceDao;

    @Resource(name="redisTemplateServiceImp")
    private RedisTemplateServiceImp redisTemplateServiceImp;
    
    @Autowired
    private ShoppingCarDao shoppingCarDao;

    
    @RequestMapping("/createOrder")
    @ResponseBody
    public AjaxResultDTO createOrder(@RequestBody List<ShopCarOrderVO> list, HttpServletRequest req){
        try {
            if(0 == list.size()) {
                throw new DyMallException("结算信息不合法");
            }
            int userId = getUser(req).getId();
            Order order = orderServiceDao.createOrder(list,userId);
            List<Integer> goodslist = new ArrayList<Integer>(list.size());
            for(ShopCarOrderVO vo : list) {
            	
            	goodslist.add(vo.getCarId());
            }
            redisTemplateServiceImp.saveCartId(goodslist, userId);
            return AjaxResultDTO.success(order);
        } catch (DyMallException e) {
            log.error("生成订单异常｛｝",e.getMessage(),e);
            return AjaxResultDTO.failed(e.getMessage());
        } catch (Exception e) {
            log.error("生成订单异常｛｝",e.getMessage(),e);
            return AjaxResultDTO.failed("系统异常，请联系客服");
        }
    }
    
    
    @RequestMapping("/createOrderFromBuyNow")
	@ResponseBody
	public AjaxResultDTO createOrderFromBuyNow(int goodsId,int amount,HttpServletRequest req) {
		try {
			ShopCarOrderVO  shopCarOrderVO = new ShopCarOrderVO();
			shopCarOrderVO.setGoodsAmount(amount);
			shopCarOrderVO.setGoodsId(goodsId);
			List<ShopCarOrderVO> list = new ArrayList<ShopCarOrderVO>();
			list.add(shopCarOrderVO);
			int userId = getUser(req).getId();
			Order order = orderServiceDao.createOrder(list, userId);
			
			return AjaxResultDTO.success(order);
		} catch (DyMallException e) {
            log.error("生成订单异常｛｝",e.getMessage(),e);
            return AjaxResultDTO.failed(e.getMessage());
        } catch (Exception e) {
            log.error("生成订单异常｛｝",e.getMessage(),e);
            return AjaxResultDTO.failed("系统异常，请联系客服");
        }
	}


    @RequestMapping("/toOrderPage")
    public ModelAndView toOrderPage(int orderId) {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("orderId",orderId);
    	modelAndView.setViewName("order");
        return modelAndView;
    }
    
    @RequestMapping("/toMyOrder")
    public ModelAndView toMyOrder() {
    	ModelAndView mv = new ModelAndView();
		mv.setViewName("myorder");
		return mv;
    }

    	

    @RequestMapping("/listOrder")
    @ResponseBody
    public AjaxResultDTO listOrder(int orderId,HttpServletRequest req) {
        try {
            OrderGoodsInfDTO orderGoodsInfDTO = orderServiceDao.listOrder(orderId,getUser(req).getId());
            return AjaxResultDTO.success(orderGoodsInfDTO);
        } catch (DyMallException e) {
            log.error("返回订单失败{}",e.getMessage(),e);
            return AjaxResultDTO.failed(e.getMessage());
        } catch (Exception e) {
            log.error("返回订单异常{}",e.getMessage(),e);
            return AjaxResultDTO.failed("系统异常，请联系客服");
        }
        
    }
    
    //提交订单，跳转到支付收银台页面
    @RequestMapping("/submitOrder")
    @ResponseBody
    public AjaxResultDTO submitOrder(OrderVO orderVO,HttpServletRequest req) {
        try {
            if(null == orderVO) {
                throw new DyMallException("没有相关的订单信息");
            }
            int userId = getUser(req).getId();
            PayResultInfo payResultInfo =orderServiceDao.changeOrderStatus(orderVO,userId);

            Set<Integer> set = redisTemplateServiceImp.getCartId(getUser(req).getId());
            for (int cartId : set) {
                log.info(">>>>删除购物车ID{}", cartId);
                shoppingCarDao.deleShopCar(cartId,getUser(req).getId());
            }

            String key = "card:cach:" + getUser(req).getId();
            redisTemplateServiceImp.delKey(key);

            return AjaxResultDTO.success(payResultInfo);
        } catch (DyMallException e) {
            log.error("提交订单异常{}",e.getMessage(),e);
            return AjaxResultDTO.failed(e.getMessage());
        }catch (Exception e) {
            log.error("提交订单异常{}",e.getMessage(),e);
            return AjaxResultDTO.failed("系统异常");
        }
    }
    
    @RequestMapping("/payFromOrder")
    @ResponseBody
    public AjaxResultDTO payFromOrder(int orderId,HttpServletRequest req) {
    	try {
    		PayResultInfo payResultInfo = orderServiceDao.changeOrderStatusFromOrder(getUser(req).getId(), orderId);
    		int userId = getUser(req).getId();
    		Set<Integer> set = redisTemplateServiceImp.getCartId(userId);
    		
    		for(Integer carId : set) {
    			log.info(">>>>删除购物车ID{}", carId);
    			shoppingCarDao.deleShopCar(carId, userId);
    		}
    		return AjaxResultDTO.success(payResultInfo);
    	} catch (DyMallException e) {
    		log.error("处理来源订单列表的支付订单失败{}",e.getMessage(),e);
    		return AjaxResultDTO.failed(e.getMessage());
		} catch (Exception e) {
			log.error("处理来源订单列表的支付订单失败{}",e.getMessage(),e);
    		return AjaxResultDTO.failed("系统异常，请联系客服");
		}
    	
    }
    
    @RequestMapping("/listMyOrder")
    @ResponseBody
    public AjaxResultDTO listMyOrder(int orderStatus,int currentPage,HttpServletRequest req) {
    	try {

            Map<Object ,Object> map = orderServiceDao.listMyOrders(getUser(req).getId(), orderStatus,currentPage);
			return AjaxResultDTO.success(map);
		} catch (DyMallException e) {
			log.error("返回订单异常{}",e.getMessage(),e);
			return AjaxResultDTO.failed(e.getMessage());
		} catch (Exception e) {
			log.error("返回订单异常{}",e.getMessage(),e);
			return AjaxResultDTO.failed("系统异常，请联系客服");
		}
    }

}
