package com.dayuanit.test;

import java.math.BigDecimal;

public class Test {
	public static void main(String[] args) {
		insertSort();
	}
	
	public static void insertSort() {
		 int a[]={49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51}; 
		 int temp = 0;
		 for(int i=1;i<a.length;i++) {
			 temp = a[i];
			 int j = i-1;
			 for(j=i-1;j>=0&&a[j]>temp;j--) {
				 a[j+1] = a[j];//将大于temp的值往后整体移动一位
				// temp = a[j];
				 a[j] = temp;
			 }
			 
		 }
		 for(int i : a) {
			 System.out.println(i);
		 }
		 
	}
}
