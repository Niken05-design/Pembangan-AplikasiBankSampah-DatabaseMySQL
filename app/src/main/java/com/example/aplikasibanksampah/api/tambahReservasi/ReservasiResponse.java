package com.example.aplikasibanksampah.api.tambahReservasi;

import com.google.gson.annotations.SerializedName;

public class ReservasiResponse{

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