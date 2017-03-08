package webapps.MOrangeCheck.com.Constants;


import webapps.MOrangeCheck.com.Tool.LT;

/**
 * Created by ppg on 2016/10/31.
 */

public class Constans {

    	public static final int DEBUGLEVEL = LT.LEVEL_ALL;// LEVEL_ALL显示所有日志;LEVEL_OFF关闭日志显示
    //    public static final int DEBUGLEVEL = LT.LEVEL_OFF;// LEVEL_ALL显示所有日志;LEVEL_OFF关闭日志显示


	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public static final int NOTIFICATION_FLAG = 901;
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	public final static int BUTTON_PREV_ID = 111;
	public final static int webapps = 22;
	public final static String NOTIFICATION_ACTION = "notification_action";
	public final static int ACTION_CHECK = 2001;
	public final static int ACTION_OUT = 2002;



	public static final String baseUrl = "http://orange_api.moreapi.com/";

	/**
	 * 获取验证码
	 * 请求方式：GET
	 * 请求地址：http://{{host}}/auth/verifyCode
	 * Body参数名	类型	必需	描述	示例 e.g.
	 * mobile_phone	string	是	手机号	18677773173
	 * verify_code	string	是	短信验证码	639999
	 */
	public static final String verifyCode = baseUrl + "auth/verifyCode";

	/**
	 * 登录
	 * 请求方式：POST
	 * 请求地址：http://www.auth.com/api/login
	 * Body参数名	类型	必需	描述	示例 e.g.
	 * email	string	是		kk@kk.com
	 * password	string	是		123456
	 */
	public static final String login = baseUrl + "auth/login2";

	/**
	 * 当日签到规则/班次
	 * Headers
	 参数名	类型	必需	描述	示例 e.g.
	 Authorization	string	是		Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vd3d3Lm9yYW5nZS5jb20vYXBpL2F1dGgvbG9naW4yIiwiaWF0IjoxNDgwOTI2ODQ5LCJleHAiOjE0ODA5ODY4NDksIm5iZiI6MTQ4MDkyNjg0OSwianRpIjoiWExaZVJva1NEMERDVVZPdCIsInN1YiI6MTIzfQ.i3Si41qFPe_xQO_p9NECSFoowkKrh6IvlpHWW2DPGP8
	 Body
	 参数名	类型	必需	描述	示例 e.g.
	 member_id	string	是		321
	 */
	public static final String checkrule = baseUrl + "checkin/checkrule";

	/**	//根据日期区间获取签到规则
	 * 参数名	类型	必需	描述	示例 e.g.
	 member_id	string	是		127.0.0.1_1480920580_0001
	 time_start	string	是		2016-12-06
	 time_end	string	是		2016-12-11
	 */
	public static final String checkrulebytimes = baseUrl + "checkin/check-rule-by-times";



	/***
	 * 参数名	类型	必需	描述	示例 e.g.
	 member_id	string	是		127.0.0.1_1480920580_0001
	 checkin_method	string	是	打卡方式，(1 无线AP 2蓝牙 3GPS）	1
	 device_id	string	是	设备id 仅当打卡为 1或2时为必传	0920514_0001
	 device_name	string	是	设备名称 仅当打卡为 1或2时为必传	ChinaNet-PzSD
	 checkin_datetime	string	是	打卡时间	2016-12-8 08:30:00
	 sch_id	string	是	所属考勤组	1
	 */
	public static final String checkin = baseUrl + "checkin/checkin";

	/***
	 参数名	类型	必需	描述	示例 e.g.
	 member_id	string	是		127.0.0.1_1480920580_0001
	 year	string	是		2016
	 month	string	是		11
	 */
	public static final String personal_report = baseUrl + "checkin/personal_report";

}
