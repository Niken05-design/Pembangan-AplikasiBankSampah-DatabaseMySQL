package com.example.aplikasibanksampah.api.register;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}