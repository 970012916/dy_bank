package com.dayuanit.dymall.dto;


public class AjaxResultDTO {

	private Object data;
	private boolean flag;
	private String message;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public AjaxResultDTO(boolean flag,Object data){
		this.flag = flag;
		this.data = data;
		
	}
	
	public AjaxResultDTO(boolean flag){
		this.flag = flag;
		
	}
	public AjaxResultDTO(Object data){
		this.data = data;
		
	}
	
	public AjaxResultDTO(boolean flag,String message){
		this.flag = flag;
		this.message = message;
		
	}
	
	
	public static AjaxResultDTO success(Object data){
		return new AjaxResultDTO(true,data);
	}
	
	public static AjaxResultDTO success(){
		return new AjaxResultDTO(true);
	}
	
	public static AjaxResultDTO success(String message){
		return new AjaxResultDTO(true,message);
	}
	
	public static AjaxResultDTO failed(String message) {
		return new AjaxResultDTO(false,message);
	}
}
