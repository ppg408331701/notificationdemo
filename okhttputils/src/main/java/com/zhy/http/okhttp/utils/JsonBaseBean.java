package com.zhy.http.okhttp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 基本解析格式
 * @author jie.yang
 *
 */
public class JsonBaseBean {

    public static int RESPONSE_TYPE_SUCCEED = 0xff11;
    public static int RESPONSE_TYPE_NOT_JSON = 0xff01;
    public static int RESPONSE_TYPE_CONNECT_FAULT = 0xff21;
//    public static int RESPONSE_TYPE_NOT_JSON = "";
//    public static int RESPONSE_TYPE_NOT_JSON = "";
	
	public static final int DATA_TYPE_OBJECT = 0x01;
	public static final int DATA_TYPE_ARRAY = 0x02;
	public static final int DATA_TYPE_CUSTION = 0x03;
	
//	public static String FIELD_NAME_RET = "";
//	public static String FIELD_NAME_MSG = "";
//	public static String FIELD_NAME_TIMESTAMP = "";

    private int responseType = RESPONSE_TYPE_SUCCEED;

    private JSONObject jsonData;
    protected String type = "";
	protected int ret = -1;
	protected String msg = null;
	protected long timestamp = -1;
	private int dataType = DATA_TYPE_CUSTION;

    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
    }

    public JSONObject getJsonData() {
        return jsonData;
    }

    public void setResponseType(int responseType) {
        this.responseType = responseType;
    }

    public int getResponseType() {
        return responseType;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public JsonBaseBean() {
		super();
	}

	public JsonBaseBean(int dataType) {
		this.dataType = dataType;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void analysisJson(String json) {
		try {
            //

            JSONObject jsonObject = new JSONObject(json);
            jsonData=jsonObject;
            paddingThis(jsonObject);
			switch (dataType) {
			case DATA_TYPE_OBJECT:
				onPaddingDataObject(jsonObject.getJSONObject("Data"));
				break;
			case DATA_TYPE_ARRAY:
				onPaddingDataArray(jsonObject.getJSONArray("Data"));
				break;
			case DATA_TYPE_CUSTION:
                onPaddingCustion(jsonObject);
			default:
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

    protected void paddingThis(JSONObject jsonObject) {
        ret = jsonObject.optInt("ret", -1);
        msg = jsonObject.optString("msg", "");
        timestamp = jsonObject.optLong("timestamp", -1);
    }
	
	protected void onPaddingDataArray(JSONArray array) {	
	}

    protected void onPaddingCustion(JSONObject jsonObject) {
    }
	
	protected void onPaddingDataObject(JSONObject object) {	
	}

	public static JSONArray addListJSONArray(JSONArray tagJSONArray, JSONArray addJSONArray) {
		if(addJSONArray == null)
			return  tagJSONArray;
		int tagSize = tagJSONArray.length();
		int addSize = addJSONArray.length();
		for(int i=0;i<addSize;++i)
			try {
				tagJSONArray.put(tagSize + i, addJSONArray.optJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
				return tagJSONArray;
			}
		return tagJSONArray;
	}
}
