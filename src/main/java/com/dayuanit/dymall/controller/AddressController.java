package com.dayuanit.dymall.controller;

import com.dayuanit.dymall.dao.AddressServiceDao;
import com.dayuanit.dymall.domain.Address;
import com.dayuanit.dymall.dto.AjaxResultDTO;
import com.dayuanit.dymall.enums.AddressStatus;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.vo.AddressVO;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController extends  BaseController{

    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressServiceDao addressService;

    @RequestMapping("/listProvince")
    @ResponseBody
    public AjaxResultDTO listProvince() {
        return AjaxResultDTO.success(addressService.listProvince());
    }

    @RequestMapping("/listCity")
    @ResponseBody
    public AjaxResultDTO listCity(String provinceCode) {

        return AjaxResultDTO.success(addressService.listCity(provinceCode));
    }

    @RequestMapping("/listArea")
    @ResponseBody
    public AjaxResultDTO listArea(String cityCode) {
       // log.info("*****cityCode******" + cityCode);
        return AjaxResultDTO.success(addressService.listArea(cityCode));
    }

    @RequestMapping("/toAddress")
    public ModelAndView toAddress() {
        ModelAndView model = new ModelAndView() ;
        model.setViewName("address");
        return model;
    }

    @RequestMapping("/addAddress")
    @ResponseBody
    public AjaxResultDTO addAddress(AddressVO addressVO, HttpServletRequest req) {
        Address address = new Address();
        address.setAreaCode(addressVO.getAreaCode());
        address.setAreaName(addressVO.getAreaName());
        address.setCityCode(addressVO.getCityCode());
        address.setCityName(addressVO.getCityName());
        address.setDetailAddress(addressVO.getDetailAddress());
        address.setIsDefault(addressVO.getIsDefault());
        address.setProvinceCode(addressVO.getProvinceCode());
        address.setProvinceName(addressVO.getProvinceName());
        address.setMobileNum(addressVO.getMobileNum());
        address.setStatus(AddressStatus.NORMAL_STATUS.getCode());
        address.setUsername(addressVO.getUsername());
        address.setUserId(getUser(req).getId());
        try {
            addressService.addAddress(address);
            return AjaxResultDTO.success(address);
        } catch (DyMallException e) {
            return AjaxResultDTO.failed(e.getMessage());
        }
    }

    @RequestMapping("/listAddress")
    @ResponseBody
    public AjaxResultDTO listAddress() {
        try {
        	
            List<Address> addressList =  addressService.listAddress();
            return AjaxResultDTO.success(addressList);
        } catch (DyMallException e) {
            return AjaxResultDTO.failed(e.getMessage());
        }
    }


    @RequestMapping("/modifyAddress")
    @ResponseBody
    public AjaxResultDTO modifyAddress(AddressVO addressVO,HttpServletRequest req) {
        if(null == addressVO) {
            log.info("addressVO is null");
        }
        Address address = new Address();
        address.setAreaCode(addressVO.getAreaCode());
        address.setAreaName(addressVO.getAreaName());
        address.setCityCode(addressVO.getCityCode());
        address.setCityName(addressVO.getCityName());
        address.setDetailAddress(addressVO.getDetailAddress());
        address.setIsDefault(addressVO.getIsDefault());
        address.setProvinceCode(addressVO.getProvinceCode());
        address.setProvinceName(addressVO.getProvinceName());
        address.setMobileNum(addressVO.getMobileNum());
        address.setStatus(AddressStatus.NORMAL_STATUS.getCode());
        log.info("username is" + addressVO.getUsername());
        address.setUsername(addressVO.getUsername());
        address.setUserId(getUser(req).getId());
        address.setId(addressVO.getId());
        log.info("******addressVO.getId()*****"+addressVO.getId());
        log.info("******address*****"+address.getUsername());
        try {
            int row  = addressService.updateAddress(address);
            if( 1 != row ) {
                throw  new DyMallException("修改地址失败");
            }
            return AjaxResultDTO.success(address);
        } catch(DyMallException e) {
            return AjaxResultDTO.failed(e.getMessage());
        }

    }
    @RequestMapping("/deleteAddress")
    @ResponseBody
    public AjaxResultDTO deleteAddress(AddressVO addressVO,HttpServletRequest req) {
        if(null == addressVO) {
            log.info("addressVO is null");
        }
        int status = AddressStatus.UNNORMAL_STATUS.getCode();
        int addressId = addressVO.getId();
        int userId = getUser(req).getId();
        log.info("******addressVO.getId()*****"+addressVO.getId());
        try {
            int row  = addressService.deleteAddress(status,userId,addressId);
            if( 1 != row ) {
                throw  new DyMallException("删除地址失败");
            }
            return AjaxResultDTO.success();
        } catch(DyMallException e) {
            return AjaxResultDTO.failed(e.getMessage());
        }

    }

}