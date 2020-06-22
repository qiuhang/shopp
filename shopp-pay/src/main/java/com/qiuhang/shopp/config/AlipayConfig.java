package com.qiuhang.shopp.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092800618398";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCvOJN3/A3gyrLMc3NwaMQOxJgAoc94E0m45uu5E8kcvKjRZKvUQNieLoiAllc0DWSoxoZ4e8LuCPJAgeiVm72amj0VYpydXHrsoFsBV/o/lQVrWWWJptJ+Vwhd2hnzatysdL7KvA5wF6CkSIP3lGjOWHyyzAglx6q85t7XF6oPqIGLY46wx8PnfIIpV1KXUJll/EgjfBcJhUSw/8QnjtR3XAIwtmeC6FILN+QECowenrxagQPmC0DBOuSGPaHin+Jz59q0yPdM14NKRpvhIrAcpJyKiAeTUBaM0pDzQ0ZZwMVCXitZAMrTbo0P/DlG2Ht+U3q7+WpmJQqVcbNNPLe9AgMBAAECggEAd1sQiPC2+2LXvJEAF5glxPGBoRyX2HE7DKqv81hY7CxOyqPKsECjzcxgy0adjCV7ZcQloTicpcyOSAa1ygNWelOKPGD50e9HBJyOoTGuk1UNZfx1P/jU9Z6PYm7IspkeTJMRy8OiChTuJjoHOsRrR/ParWhyPFnwIdEaBmZmP1cmn4k53uxagWBErHDp4SuVw8j0Dvqw8sseP0Td8DbzWlBwN8AIp+6yGzoid+1FjfQKfcw5pSDXBAC3jNffmKOH7YnyFVetBdQ0DokgXNoKfOCQ7NXHIL63GqnBdXCi60KBQ+J0B1i8Pze99Z5eMN6n2zLWmjH0e6LdFpK7vTPheQKBgQDX2RKhMKtSF08PXG6bcuE7c9C+WCm/gVGZ3/ETbdSvDRc4U7QsVoDnAP+i3nWoe58yfQxE2SQuG0YrVd9HDqXdleQ9kqzj24TEQ/c7lp9T2xr6tRFrqNdrdRoMrYwoYIlc4EpNrAEScqUmo++zNy5M7U4c6ukMB9JtmpRMeapyfwKBgQDP0M04jYdQ8czeFst6oZxpc/jqNvRAIHDzpIc7+eQl73iaPiw9Zg0NsaqCtv+xEwHIrosr4jZPvSjIqcV+QL4LVGL4KrxaeSBoyOvMBQr/Lf0F6ntkT6P2+arAz3garreMUDXiLyr70+De09d4FbcvAM42jWHOZnJPNjTitCX/wwKBgQDLNsaQVWbmOaWoW6iS15xvwcEEg7KCZvjxeuHYHe5ABH4Y/M1DdQmU8t66cuCpCAJAoXRcmWaztd/hSs6Ucpj8PnOM+LunJnQf57ErO8qzQzRqVvCYwHRX6f7lcrrjBMguVcf+ynKT8LXF5E/3FXuU11JUbWGNfwvBPuhh+6XadwKBgQCWiQjva03W66DcS6TPyxuTI8fuWSpq/VT0mHzBjE2g0SwKhvji7PaNseHy1/611S/Yn97+RdnTvxE48gm2X82KRXF/lO1lmBJK9SghXJHPsQ905prFWg9KyyXOjzrQETmyMTQnCMXZL2zX3JpbSVete6bky1tK+Nja2khK4/YWowKBgBjJ4mo5vSyKaUrsLKxGznnvjDpvArjl9kVBFNDPWh6dM0PHZRFbZLx71u7AqtjP4iUvY1ZvT6ytfuwVdOHL63wcPPvm+08tZ0GbBPqxhHT8tYOPoDF9SR65iyGxipjuA+Wjr7MA1v7bchEiwJwZOI8DrpD4ahKf3Oxr8xDlRKYi";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
	// 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA18To6JyYyaNbDMPu49xmXgmfYHVlLhcMU0cEvWWGk7yMy056HTTor+to0ESt7oUnN+ZxnulZtavCbEWkXw9ylYwTJFxne2UqqS/7slrK6s2Z6y9vhUsWf9HsI3LWFg1sN52PjNxbzZiuoec3ZyXD9Ih+V5G0XN5244E7bc/zw3fsC1MSUm8Lc4uEO4tO+f47m8YRQ//nXSkH12HD66OHHYHMdsKH1lKmYRp2C4QtU4liYNy/MCy+qj7erUiKHU1qRSuXbsATYyu+861nnrV8tKiGfqAt79K2vGlv0y0z5F6j3PME0EyR1eAQ+YxypWw4v4Lal4sC/+rKUJGlTVb3HQIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://mh2t95.natappfree.cc/alibaba/callBack/notifyUrl";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://mh2t95.natappfree.cc/alibaba/callBack/returnUrl";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 日志地址
	public static String log_path = "D:\\";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 *
	 * @param sWord
	 *            要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
