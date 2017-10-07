package com.dayuanit.dymall.util;

import org.springframework.stereotype.Component;

@Component
public class PageUtil {

	public static final int PER_NUM = 2;
	
	public static int gettotalPageNum(int totalNum) {
		
		int totalPageNum = totalNum % PER_NUM == 0 ? (totalNum /PER_NUM) :((totalNum / PER_NUM)+1);
		return totalPageNum;
	}
	
	public static int getOffNum(int currentPageNum) {
		int offNum = (currentPageNum - 1) * PER_NUM;
		return offNum;
	}
}
