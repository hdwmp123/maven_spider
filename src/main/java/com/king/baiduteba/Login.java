package com.king.baiduteba;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

/**
 * 使用Jsoup模拟登陆百度
 * 
 * 
 * 大体思路如下:
 * 
 * 第一次请求登陆页面，获取页面信息，包含表单信息，和cookie（这个很重要），拿不到，会模拟登陆不上
 * 
 * 
 * 第二次登陆，设置用户名，密码，把第一次的cooking，放进去，即可
 * 
 * 怎么确定是否登陆成功？
 * 
 * 登陆后，打印页面，会看见欢迎xxx，即可证明
 * 
 * 
 * @date 2014年6月27日
 * @author qindongliang
 * 
 * 
 * **/
public class Login {

	public static void main(String[] args) throws Exception {
        String pass = "加密的哦";
		Login jli = new Login();
        jli.login("123456789", pass);// 输入百度的用户名，和密码

	}

	/**
	 * 模拟登陆百度
	 * 
	 * @param userName
	 *            用户名
	 * @param pwd
	 *            密码
	 * 
	 * **/
	public Map<String, String> login(String userName, String pwd)
			throws Exception {

		// 第一次请求
		Connection con = Jsoup.connect("https://www.baidu.com/");// 获取连接
		con.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
		Response rs = con.execute();// 获取响应
		// 获取，cooking和表单属性，下面map存放post时的数据
		Map<String, String> datas = new HashMap<String, String>();
		datas.put("username", userName);
		datas.put("password", pwd);
		/**
		 * 第二次请求，post表单数据，以及cookie信息
		 * 
		 * **/
		Connection con2 = Jsoup
				.connect("https://passport.baidu.com/v2/api/?login");
		con2.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		// 设置cookie和post上面的map数据
		Response login = con2.ignoreContentType(true).method(Method.POST)
				.data(datas).cookies(rs.cookies()).execute();
		// 打印，登陆成功后的信息
		// System.out.println(login.body());

		// 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
		Map<String, String> map = login.cookies();
		for (String s : map.keySet()) {
			System.out.println(s + " " + map.get(s));
		}
		return map;

	}
}
