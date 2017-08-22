package com.zed.util;


import com.zed.common.exception.EnumOutOfBoundsException;
import com.zed.common.util.StringUtil;
import com.google.gson.Gson;

public class ConstantType {
	
	public static final Gson gson = new Gson();
	
	
	public static enum OriginType{
		SED_LIVE(1),
		SED_PLAYER(2);
		private int type;
		OriginType(int type){
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	
	/**
	 * 登陆类型
	 * @author liupeng
	 *
	 */
	public static enum LoginType{
		SELF(0),//自有账户登陆
		QQ(1),
		WEIXIN(2),
		WEIBO(3),
		FACEBOOK(4),
		TWITTER(5),
		GOOGOL(6),
		OTHER(-1)
		;
		
		private int type;
		LoginType(int type){
			this.type = type;
		}
		public int getType() {
			return type;
		}
		
		/**
		 *  类型检查，返回枚举类
		 * @param type
		 * @return
		 */
		public static LoginType getLoginType(int type){
			LoginType[] types = LoginType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getType()==type){
					return types[i];
				}
			}
			
			return LoginType.OTHER;
		}
		
		
	}

	/**
	 * 来源
	 * @author lixuan
	 *
	 */
	public static enum Origin{
		LIVE(0),//自有账户登陆
		PLAYER(1);
		private int type;
		Origin(int type){
			this.type = type;
		}
		public int getType() {
			return type;
		}
		
		/**
		 *  类型检查，返回枚举类
		 * @param type
		 * @return
		 */
		public static LoginType getLoginType(int type){
			LoginType[] types = LoginType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getType()==type){
					return types[i];
				}
			}
			
			return LoginType.OTHER;
		}
		
		
	}
	
	/**
	 * 性别
	 * @author 天俊
	 *
	 */
	public static enum Gender{
		FEMALE("1"),//女
		MALE("0"),//男
		NULL(""),//空
		;
		
		private String gender;
		Gender(String gender){
			this.gender = gender;
		}
		public String getGender() {
			return gender;
		}
		
		
		public static Gender getGender(String gender){
			Gender[] types = Gender.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getGender().equals(gender)){
					return types[i];
				}
			}
			return Gender.NULL;
		}
		
	}
	
	
	//运营商
	public static enum Operator{
		CMCC("CMCC"),
		UNICOM("UNICOM"),
		TELECOM("TELECOM"),
		OTHER("OTHER"),
		
		;
		
		private String operator;
		private Operator(String operator){
			this.operator = operator;
		}
		public String getOperator() {
			return operator;
		}
		
		
		/**
		 *  类型检查，返回枚举类
		 * @param type
		 * @return
		 * @throws EnumOutOfBoundsException
		 */
		public static Operator getOperator(String operator){
			Operator[] ops = Operator.values();
			for(int i=0;i<ops.length;i++){
				if(ops[i].getOperator().equals(operator)){
					return ops[i];
				}
			}
			
			return Operator.OTHER;
		}
		
	}
	
	
	
	
	//网络类型
	public static enum Net{
		G2("2G"),
		G3("3G"),
		G4("4G"),
		WIFI("WIFI"),
		OTHER("OTHER")
		;
		
		private String net;
		private Net(String net){
			this.net = net;
		}
		
		
		public String getNet() {
			return net;
		}

		/**
		 *  类型检查，返回枚举类
		 * @param net
		 * @return
		 * @throws EnumOutOfBoundsException
		 */
		public static Net getNet(String net){
			Net[] ns = Net.values();
			for(int i=0;i<ns.length;i++){
				if(ns[i].getNet().equals(net)){
					return ns[i];
				}
			}
			
			return Net.OTHER;
		}
		
	}
	
	
	
	/**
	 * 客户端系统类型
	 * @author 天俊
	 *
	 */
	public static enum SysType{
		ANDROID("a"),//安卓
		IOS("i"),//苹果
		;
		
		private String type;
		SysType(String type){
			this.type = type;
		}
		public String getType() {
			return type;
		}
		
		
		public static SysType getSysType(String type){
			if(!StringUtil.isBlank(type) && type.toLowerCase().startsWith("iphone")){
				 return SysType.IOS;
			}
			SysType[] types = SysType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getType().equals(type)){
					return types[i];
				}
			}
			return SysType.ANDROID;
		}
		
	}
	
	public static enum FriendType{
		FRIEND(0),//
		WAIT_FOR_CONFIRM(1),
		REFUSED(2),
		STRANGER(-1),
		BLACK(-2),
		
		OTHER(-10),//
		;
		
		private int type;
		FriendType(int type){
			this.type = type;
		}
		public int getType() {
			return type;
		}
		
		/**
		 *  类型检查，返回枚举类
		 * @param type
		 * @return
		 */
		public static FriendType getType(int type){
			FriendType[] types = FriendType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getType()==type){
					return types[i];
				}
			}
			
			return FriendType.OTHER;
		}
		
		
	}
	
	
	
	/**
	 * 用户消息通知类型
	 * @author 天俊
	 *
	 */
	public static enum MsgNotifyType{
		MSG_BEE(1,"msg_bee"),//
		VIBRATE_BEE(2,"vibrate_bee"),
		VOICE_BEE(3,"voice_bee"),
		STRANGE_BEE(4,"strange_bee"),
		GROUP_BEE(5,"group_bee"),
		TIME_BEE(6,"time_bee"),
		CHATROOM_BEE(7,"room_bee"),
		
		OTHER(-10,"null"),//
		;
		
		private int type;
		private String fieldName;
		MsgNotifyType(int type,String fieldName){
			this.type = type;
			this.fieldName = fieldName;
		}
		public int getType() {
			return type;
		}
		
		public String getFieldName() {
			return fieldName;
		}
		/**
		 *  类型检查，返回枚举类
		 * @param type
		 * @return
		 */
		public static MsgNotifyType getType(int type){
			MsgNotifyType[] types = MsgNotifyType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getType()==type){
					return types[i];
				}
			}
			
			return MsgNotifyType.OTHER;
		}
		
		
	}
	
	/**
	 * 统计操作
	 * @author 天俊
	 *
	 */
	public static enum StatOperate{
		DEFAULT("d"),
		INROOM("inroom"),//进入聊天室
		OUTROOM("outroom"),//退出聊天室
		;
		
		private String operate;
		StatOperate(String operate){
			this.operate = operate;
		}
		public String getOperate() {
			return operate;
		}
		
		
		public static StatOperate getOperate(String operate){
			StatOperate[] types = StatOperate.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getOperate().equals(operate)){
					return types[i];
				}
			}
			return StatOperate.DEFAULT;
		}
		
	}
	
	
	

	
	
	/**
	 * 登陆来源
	 * @author lixuan
	 *
	 */
	public static enum SettingSwitch{
		OPEN(1),//开启
		CLOSE(2);//关闭
		private int type;
		SettingSwitch(int type){
			this.type = type;
		}
		public int getSwitch() {
			return type;
		}
		
		public static SettingSwitch getSwitch(int operate){
			SettingSwitch[] types = SettingSwitch.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getSwitch()==operate){
					return types[i];
				}
			}
			return SettingSwitch.OPEN;
		}
		
		
	}
	
	
	
	
	/**
	 * 支付渠道
	 * @author lixuan
	 *
	 */
	public static enum PayChannel{
		GOOGLE_PAY(1);//Google支付
		private int type;
		PayChannel(int type){
			this.type = type;
		}
		public int getPayChannel() {
			return type;
		}
		
		public static PayChannel getPayChannel(int operate){
			PayChannel[] types = PayChannel.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getPayChannel()==operate){
					return types[i];
				}
			}
			return PayChannel.GOOGLE_PAY;
		}
		
		
	}
	
	
	/**
	 * 支付动作
	 * @author lixuan
	 *
	 */
	public static enum PayMotion{
		RECHARGE(1),//充值
		DRAWINGS(2),//提现
		BUY_PRESENT(3);//购买礼物
		private int type;
		PayMotion(int type){
			this.type = type;
		}
		public int getPayMotion() {
			return type;
		}
		
		public static PayMotion getPayMotion(int operate){
			PayMotion[] types = PayMotion.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getPayMotion()==operate){
					return types[i];
				}
			}
			return PayMotion.RECHARGE;
		}
		
		
	}
	
	/**
	 * 支付类型
	 * @author lixuan
	 *
	 */
	public static enum PayType{
		GOLD_COIN_PAY(1),//金币支付
		SCORE_PAY(2);//积分支付
		private int type;
		PayType(int type){
			this.type = type;
		}
		public int getPayType() {
			return type;
		}
		
		public static PayType getPayType(int operate){
			PayType[] types = PayType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getPayType()==operate){
					return types[i];
				}
			}
			return PayType.GOLD_COIN_PAY;
		}
		
		
	}
	
	
	
	/**
	 * 数据库数据status类型
	 * @author lixuan
	 *
	 */
	public static enum DataBaseStatusType{
		ERROR(-2),//异常/错误/失败
		DELETED(-1),//已经删除
		NO_USING(0),//未使用/非最新
		USING(1),//使用中/最新
		HANDING(2),//处理中
		HANDING_STOP(3),//处理暂停
		HANDING_FORGO(4);//处理放弃
		private int type;
		DataBaseStatusType(int type){
			this.type = type;
		}
		public int getStatus() {
			return type;
		}
		
		public static DataBaseStatusType getStatus(int operate){
			DataBaseStatusType[] types = DataBaseStatusType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getStatus()==operate){
					return types[i];
				}
			}
			return DataBaseStatusType.USING;
		}
		
		
	}
	
	
	
	/**
	 * 货币币种
	 * @author lixuan
	 *
	 */
	public static enum CurrencyType{
		RS(1),//卢比
		￥(2),//美元
		RMB(3);//人民币
		private int type;
		CurrencyType(int type){
			this.type = type;
		}
		public int getStatus() {
			return type;
		}
		
		public static CurrencyType getType(int operate){
			CurrencyType[] types = CurrencyType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getStatus()==operate){
					return types[i];
				}
			}
			return CurrencyType.RS;
		}
		
		
	}
	
	
	/**
	 * 货币币种
	 * @author lixuan
	 *
	 */
	public static enum CountryType{
		India(91),//印度
		China(86),//天朝
		Indonesia(62),//印尼
		Thailand(66),//泰国
		Vietnam(84);//越南
		private int type;
		CountryType(int type){
			this.type = type;
		}
		public int getStatus() {
			return type;
		}
		
		public static CountryType getType(int operate){
			CountryType[] types = CountryType.values();
			for(int i=0;i<types.length;i++){
				if(types[i].getStatus()==operate){
					return types[i];
				}
			}
			return CountryType.China;
		}
		
		
	}
	
	
	
	public static enum UserType{
		SYSTEM_USER(1),
		SYSTEM_TOURIST(2);
		private int type;
		UserType(int type){
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	/**
	 * 热词状态值
	 */
	public static enum KeyWordType{
		STOP(0), 		//禁用
		START(1); 		//启用
		private int status;
		KeyWordType(int status){
			this.status = status;
		}
		public int getStatus(){
			return status;
		}
	}
	
	/**
	 * 通用状态值
	 */
	public static enum CommonType{
		ZERO(0), 		//禁用
		ONE(1); 		//启用
		private int status;
		CommonType(int status){
			this.status = status;
		}
		public int getStatus(){
			return status;
		}
	}
	
	/**
	 * 播放源文件状态值
	 */
	public static enum PlayerType{
		WAITINGFORTRANSCODING(0), 		//待转码
		SUCCESS(1), 					//转码成功 
		TRANSCODING(2), 				//转码中
		FAILED(3); 						//转码失败
		private int status;
		PlayerType(int status){
			this.status = status;
		}
		public int getStatus(){
			return status;
		}
	}
	
	/**
	 * @date : 2017年2月13日 上午11:48:54
	 * @author : Iris.Xiao
	 * @version : 1.0
	 * @description : 热门用户状态
	*/
	public static enum HotUserStatus{
		DISABLE_ALL(-1),//全部禁用包括资源
		DISABLE(0), 		//禁用
		ENABLE(1); 		//启用
		private int status;
		HotUserStatus(int status){
			this.status = status;
		}
		public int getStatus(){
			return status;
		}
	}
}
