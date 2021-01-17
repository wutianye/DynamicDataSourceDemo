package com.example.demo.common;

public class Result<T> {

	private static String SUCCESS = "I00001";
	private static String ERROR = "E9999";
	private String code;
	private String msg;
	private T data;

	public Result() {
	}

	public Result(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static <T> Result<T> success(T data) {
		Result<T> result = new Result<T>();
		result.setCode(SUCCESS);
		result.setData(data);
		return result;
	}
	
    public static <T> Result<T> fail(String msg) {
    	Result<T> result = new Result<T>();
        result.setCode(ERROR);
        result.setMsg(msg);
        return result;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
	}
}
