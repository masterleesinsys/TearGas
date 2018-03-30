package com.fly.teargas.entity;

import java.io.Serializable;

public class VisitInfo implements Serializable {

	private static final long serialVersionUID = -3373113900827957038L;

	public String mplatform; // 平台
	public String mdevice_UID; // 设备id
	public String mstart_time; // 进入时间
	public String mnetwork_type; // 网络类型
	public String mip; // IP
	public String mdevice_machine; // 手机型号
	public String mresolution; // 分辨率
	public String mos_version; // 系统版本
	public String mapp_version; // App版本号
	public String mend_time; // 退出时间

	public String getplatform() {
		return mplatform;
	}

	public void setplatform(String value) {
		mplatform = value;
	}

	public String getdevice_UID() {
		return mdevice_UID;
	}

	public void setdevice_UID(String value) {
		mdevice_UID = value;
	}

	public String getstart_time() {
		return mstart_time;
	}

	public void setstart_time(String value) {
		mstart_time = value;
	}

	public String getnetwork_type() {
		return mnetwork_type;
	}

	public void setnetwork_type(String value) {
		mnetwork_type = value;
	}

	public String getip() {
		return mip;
	}

	public void setip(String value) {
		mip = value;
	}

	public String getdevice_machine() {
		return mdevice_machine;
	}

	public void setdevice_machine(String value) {
		mdevice_machine = value;
	}

	public String getresolution() {
		return mresolution;
	}

	public void setresolution(String value) {
		mresolution = value;
	}

	public String getos_version() {
		return mos_version;
	}

	public void setos_version(String value) {
		mos_version = value;
	}

	public String getapp_version() {
		return mapp_version;
	}

	public void setapp_version(String value) {
		mapp_version = value;
	}

	public String getend_time() {
		return mend_time;
	}

	public void setend_time(String value) {
		mend_time = value;
	}

}
