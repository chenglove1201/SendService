package com.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.common.JdbcUtils;
import com.common.LogRecord;
import com.common.XMLParser;
import com.entity.Beauty;
import com.entity.BeautyCard;
import com.entity.BeautyCardArray;
import com.entity.BuyCardRecord;
import com.entity.Card;
import com.entity.Machine;
import com.entity.MachineType;
import com.entity.Merchant;
import com.exception.CommonException;
import com.google.gson.Gson;

public class Daocrud {

//	public static void main(String[] args) {
//		new Daocrud();
//	}
//
//	public Daocrud() {
//		// try {
//		// updateConsumeOrder2("8effde89737ca99b");
//		// } catch (CommonException e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//		// System.out.println(getMchOrder("8effde89737ca99b"));
//		// try {
//		// System.out.println(queryRecordCount("8effde89737ca99b"));
//		// } catch (CommonException e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//
//		try {
//			// insertConsumeOrder("4", "8effde89737ca99b");
//			// System.out.println(queryCardRemainTimes(1 + ""));
//			List<BeautyCard> queryBeautyCard = queryBeautyCard("爱看到美");
//			for(int i = 0;i<queryBeautyCard.size();i++){
//				System.out.println(queryBeautyCard.get(i).getCustom_times());
//			}
//		} catch (CommonException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * 美容院注册
	 */
	public void InsertBeauty(String beauty_name, String contacts, String beauty_password, String phone,
			String address) {
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql = "insert into beauty values(null,?,?,?,?,?,?)";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, beauty_name);
			prepareStatement.setString(2, contacts);
			prepareStatement.setString(3, beauty_password);
			prepareStatement.setString(4, phone);
			prepareStatement.setString(5, address);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			prepareStatement.setString(6, dateFormat.format(new Date()));
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			if (e.getErrorCode() == 1062) {
				System.out.println("美容院名已存在，请重新输入");
			}
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 创建卡
	 */
	public void InsertCard(String card_name) {
		PreparedStatement prepareStatement = null;
		Connection conn = JdbcUtils.getConn();
		String sql = "insert into card values(null,?)";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, card_name);
			prepareStatement.executeUpdate();
			JdbcUtils.close(null, prepareStatement, conn);
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			if (e.getErrorCode() == 1062) {
				System.out.println("美容卡已存在，请重新定义");
			}
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 注册会员
	 * 
	 * @throws CommonException
	 */
	public void InsertCustomer(String customer_name, String password, String age, String sex, String bind_beauty)
			throws CommonException {
		PreparedStatement prepareStatement = null;
		Connection conn = JdbcUtils.getConn();
		String sql = "insert into customer values(null,?,?,?,?,?,?)";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, customer_name);
			prepareStatement.setString(2, password);
			prepareStatement.setString(3, age);
			prepareStatement.setString(4, sex);
			prepareStatement.setString(5, bind_beauty);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			prepareStatement.setString(6, dateFormat.format(new Date()));
			prepareStatement.executeUpdate();
			JdbcUtils.close(null, prepareStatement, conn);
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage() + "..." + customer_name + "..." + password + "..." + age + "..." + sex
					+ "..." + bind_beauty + "..." + bind_beauty.getBytes().length);
			if (e.getErrorCode() == 1062) {
				throw new CommonException("此用户已存在，请重新注册");
			}
			throw new CommonException("用户注册失败，请重新注册");
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 创建商户
	 */
	public void InsertMerchant(String appid, String mch_id, String key, String bind_beauty) {
		PreparedStatement prepareStatement = null;
		Connection conn = JdbcUtils.getConn();
		String sql = "insert into merchant values(null,?,?,?,?)";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, appid);
			prepareStatement.setString(2, mch_id);
			prepareStatement.setString(3, key);
			prepareStatement.setString(4, bind_beauty);
			prepareStatement.executeUpdate();
			JdbcUtils.close(null, prepareStatement, conn);
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 添加设备
	 */
	public void InsertMachine(String cpu_id, String bind_mch_type, String bind_beauty) {
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql = "insert into machine values(null,?,?,?,?)";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, cpu_id);
			prepareStatement.setString(2, bind_mch_type);
			prepareStatement.setString(3, bind_beauty);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			prepareStatement.setString(4, dateFormat.format(new Date()));
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			if (e.getErrorCode() == 1062) {
				System.out.println("此设备id已存在，请重新输入");
			}
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 创建设备类型
	 */
	public void InsertMchType(String type) {
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql = "insert into machine_type values(null,?)";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, type);
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 根据数组插入购买的卡
	 * 
	 * @param a
	 */
	public boolean InsertBuyCardRecord(String buy_card) {
		String[] cards = buy_card.split(",");
		String buy_card_customer = cards[0];
		String affiliation = cards[cards.length - 1];
		String[] buy_card_data = new String[cards.length - 2];
		for (int i = 0; i < buy_card_data.length; i++) {
			buy_card_data[i] = cards[i + 1];
		}
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql = "insert into buy_card_record values(null,?,?,?,?,?,?,?,?,?,?)";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sql);
			for (int i = 0; i < buy_card_data.length / 3; i++) {
				List<String> card_info = queryBeautyCardNameById(buy_card_data[i * 3]);
				prepareStatement.setString(1, buy_card_customer);
				prepareStatement.setString(2, card_info.get(0));// 美容院
				prepareStatement.setString(3, card_info.get(1));// 卡的名称
				prepareStatement.setInt(4, Integer.parseInt(card_info.get(2)));// 卡原价
				prepareStatement.setInt(5, Integer.parseInt(buy_card_data[i * 3 + 1]));
				prepareStatement.setInt(6, Integer.parseInt(card_info.get(3)));// 卡的原时长
				prepareStatement.setInt(7, Integer.parseInt(buy_card_data[i * 3 + 2]));
				prepareStatement.setInt(8, Integer.parseInt(buy_card_data[i * 3 + 2]) * 60);
				prepareStatement.setString(9, affiliation);
				prepareStatement.setString(10, dateFormat.format(date));
				prepareStatement.addBatch();
			}
			prepareStatement.executeBatch();
			conn.commit();
			return true;
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
		return false;
	}

	/**
	 * 给美容院增加服务费
	 * 
	 * @throws CommonException
	 */
	public boolean addAdvanceCharge(String attach) throws CommonException {
		System.out.println("gwegweg");
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String beauty = attach.split(",")[1];
		String advanceCharge = attach.split(",")[2];
		String sql = "update beauty set TOTAL_ADVANCE_PAYMENT = TOTAL_ADVANCE_PAYMENT + ?,REMAINING_ADVANCE = REMAINING_ADVANCE + ? where BEAUTY_NAME = ?";
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, advanceCharge);
			prepareStatement.setString(2, advanceCharge);
			prepareStatement.setString(3, beauty);
			prepareStatement.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			throw new CommonException("给美容院增加服务费错误");
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 插入微信返回数据
	 * 
	 * @param a
	 */
	public boolean insertWxNotify(String customer_name, Map<String, Object> analysisResponseMap) {
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql = "insert into transaction_result_notification values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, customer_name);
			prepareStatement.setString(2, (String) analysisResponseMap.get("appid"));
			prepareStatement.setString(3, (String) analysisResponseMap.get("mch_id"));
			prepareStatement.setString(4, (String) analysisResponseMap.get("attach"));
			prepareStatement.setString(5, (String) analysisResponseMap.get("bank_type"));
			prepareStatement.setString(6, (String) analysisResponseMap.get("cash_fee"));
			prepareStatement.setString(7, (String) analysisResponseMap.get("nonce_str"));
			prepareStatement.setString(8, (String) analysisResponseMap.get("openid"));
			prepareStatement.setString(9, (String) analysisResponseMap.get("out_trade_no"));
			prepareStatement.setString(10, (String) analysisResponseMap.get("sign"));
			prepareStatement.setString(11, (String) analysisResponseMap.get("total_fee"));
			prepareStatement.setString(12, (String) analysisResponseMap.get("trade_type"));
			prepareStatement.setString(13, (String) analysisResponseMap.get("transaction_id"));
			prepareStatement.setString(14, (String) analysisResponseMap.get("time_end"));
			prepareStatement.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
		return false;
	}

	/**
	 * 插入购卡记录
	 */
	public void InsertBuyCardRecord(String buy_card_customer, String buy_card_beauty, String buy_card_name,
			String card_price, String card_times, String affiliation_orders) {
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql = "insert into buy_card_record values(null,?,?,?,?,?,?,?,?,?,?)";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, buy_card_customer);
			prepareStatement.setString(2, buy_card_beauty);
			prepareStatement.setString(3, buy_card_name);
			prepareStatement.setString(4, card_price);
			prepareStatement.setString(5, card_times);
			prepareStatement.setString(6, affiliation_orders);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
			prepareStatement.setString(7, dateFormat.format(new Date()));
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 创建美容院美容卡，单个
	 * 
	 * @throws CommonException
	 */
	public void insertBeautyCard(String beauty_card_name, String custom_price, String custom_times, String bind_beauty)
			throws CommonException {
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql = "insert into beauty_card values(null,?,?,?,?,?)";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, beauty_card_name);
			prepareStatement.setString(2, custom_price);
			prepareStatement.setString(3, custom_times);
			prepareStatement.setString(4, bind_beauty);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			prepareStatement.setString(5, dateFormat.format(new Date()));
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("插入美容院美容卡错误");
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 创建美容院美容卡 json方式批量创建
	 * 
	 * @throws CommonException
	 */
	public void insertBeautyCard(String beauty_card_json) throws CommonException {
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql_delete = "delete from beauty_card where BIND_BEAUTY = ?";
		String sql = "insert into beauty_card values(null,?,?,?,?,?)";
		Gson gson = new Gson();
		BeautyCardArray beautyCardArray = gson.fromJson(beauty_card_json, BeautyCardArray.class);
		List<BeautyCard> beautyCard = beautyCardArray.getBeautyCard();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sql_delete);
			prepareStatement.setString(1, beautyCard.get(0).getBind_beauty());
			prepareStatement.executeUpdate();
			prepareStatement = conn.prepareStatement(sql);
			for (int i = 0; i < beautyCard.size(); i++) {
				prepareStatement.setString(1, beautyCard.get(i).getBeauty_card_name());
				prepareStatement.setInt(2, beautyCard.get(i).getCustom_price());
				prepareStatement.setInt(3, beautyCard.get(i).getCustom_times());
				prepareStatement.setString(4, beautyCard.get(i).getBind_beauty());
				prepareStatement.setString(5, dateFormat.format(date));
				prepareStatement.addBatch();
			}
			prepareStatement.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 更新设备使用时间
	 * 
	 * @return 返回的boolean值是服务费模式中是否够下次使用，true表示可以，false表示服务费已不足，不能工作
	 * @throws CommonException
	 */
	public boolean updateConsumeOrder2(String consume_object, String cpu_id, String advance_payment)
			throws CommonException {
		boolean flag = true;// 默认返回值，如果服务费更新失败，置为false
		int id = 0;
		int use_time = 0;
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql1 = "select * from consume_record2 where CONSUME_OBJECT = ? and USE_DESCRIBE = ?";
		String sql2 = "insert into consume_record2 values(null,?,?,?,?,?)";
		String sql3 = "update consume_record2 set WORK_TIME = ?,USE_DESCRIBE = ?,DATE = ? where ID = ?";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		try {
			conn.setAutoCommit(false);
			// 查询
			prepareStatement = conn.prepareStatement(sql1);
			prepareStatement.setString(1, consume_object);
			prepareStatement.setString(2, "using");
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				id = executeQuery.getInt("ID");
				use_time = executeQuery.getInt("WORK_TIME");
			}
			if (id == 0) {
				// 插入
				prepareStatement = conn.prepareStatement(sql2);
				prepareStatement.setString(1, consume_object);
				prepareStatement.setString(2, cpu_id);
				prepareStatement.setInt(3, 1);
				prepareStatement.setString(4, "begin");
				prepareStatement.setString(5, dateTime);
				prepareStatement.addBatch();
				prepareStatement.setString(1, consume_object);
				prepareStatement.setString(2, cpu_id);
				prepareStatement.setInt(3, 1);
				prepareStatement.setString(4, "using");
				prepareStatement.setString(5, dateTime);
				prepareStatement.addBatch();
				prepareStatement.executeBatch();
			} else {
				// 更新
				prepareStatement = conn.prepareStatement(sql3);
				if (use_time == 1) {
					prepareStatement.setInt(1, use_time + 1);
					prepareStatement.setString(2, "end");
					prepareStatement.setString(3, dateTime);
					prepareStatement.setInt(4, id);
					// 如果是服务费模式，更新剩余服务费
					if (advance_payment.split(",")[0].equals("beauty")) {
						flag = updateRemainAdvance(advance_payment.split(",")[1]);
					}
				} else {
					prepareStatement.setInt(1, use_time + 1);
					prepareStatement.setString(2, "using");
					prepareStatement.setString(3, dateTime);
					prepareStatement.setInt(4, id);
				}
				prepareStatement.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
		return flag;
	}

	/**
	 * 更新设备使用时间
	 * 
	 */
	public void insertConsumeOrder(String object, String cpu_id) throws CommonException {
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql1 = "insert into consume_record values(null,?,?,?)";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		try {
			prepareStatement = conn.prepareStatement(sql1);
			prepareStatement.setString(1, object);
			prepareStatement.setString(2, cpu_id);
			prepareStatement.setString(3, dateTime);
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	// /**
	// * 插入购卡记录
	// */
	// public void InsertBuyCardRecord(String buy_card_customer, String
	// buy_card_beauty, String buy_card_name,
	// String card_price2, String card_times, String affiliation_orders) {
	// Connection conn = JdbcUtils.getConn();
	// PreparedStatement prepareStatement = null;
	// String sql = "insert into buy_card_record values(null,?,?,?,?,?,?,?)";
	// try {
	// conn.setAutoCommit(false);
	// prepareStatement = conn.prepareStatement(sql);
	// prepareStatement.setString(1, buy_card_customer);
	// prepareStatement.setString(2, buy_card_beauty);
	// prepareStatement.setString(3, buy_card_name);
	// prepareStatement.setString(5, card_times);
	// prepareStatement.setString(6, affiliation_orders);
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss");
	// prepareStatement.setString(7, dateFormat.format(new Date()));
	// for (int i = 0; i < 2251094; i++) {
	// prepareStatement.setString(4,
	// RandomStringGenerator.getRandomNumByLength(15));
	// prepareStatement.addBatch();
	// }
	// prepareStatement.executeBatch();
	// conn.commit();
	// // prepareStatement.executeUpdate();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// JdbcUtils.close(null, prepareStatement, conn);
	// }
	// }

	/**
	 * 根据card_id判断剩下卡时间够不够下次使用
	 * 
	 * @param cpu_id
	 * @return
	 * @throws CommonException
	 */
	public String queryCardRemainTimes(String card_id) throws CommonException {
		String remainTimes = "";
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql1 = "select REMAIN_TIMES from buy_card_record where ID = ?";
		try {
			prepareStatement = conn.prepareStatement(sql1);
			prepareStatement.setString(1, card_id);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				remainTimes = executeQuery.getString("REMAIN_TIMES");
			}
			return remainTimes;
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("查询卡剩余时长出现异常");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
	}

	/**
	 * 根据绑定美容院查询服务费
	 * 
	 * @param cpu_id
	 * @return
	 * @throws CommonException
	 */
	public String queryAdvancePayment(String bind_beauty) throws CommonException {
		String total_advance_payment = null, remaining_advance = null, unit_price = null;
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql1 = "select TOTAL_ADVANCE_PAYMENT,REMAINING_ADVANCE,UNIT_PRICE from beauty where BEAUTY_NAME = ?";
		try {
			prepareStatement = conn.prepareStatement(sql1);
			prepareStatement.setString(1, bind_beauty);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				total_advance_payment = executeQuery.getString("TOTAL_ADVANCE_PAYMENT");
				remaining_advance = executeQuery.getString("REMAINING_ADVANCE");
				unit_price = executeQuery.getString("UNIT_PRICE");
			}
			return total_advance_payment + "," + remaining_advance + "," + unit_price;
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("查询设备订单数量出现异常");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
	}

	/**
	 * 根据cpuid查询此设备下单数量
	 * 
	 * @param cpu_id
	 * @return
	 * @throws CommonException
	 */
	public String queryRecordCount(String beauty) throws CommonException {
		String total_count = null;
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql1 = "select count(*) from consume_record where CONSUME_OBJECT = ?";
		try {
			prepareStatement = conn.prepareStatement(sql1);
			prepareStatement.setString(1, beauty);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				total_count = executeQuery.getString("count(*)");
			}
			return total_count;
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("查询设备订单数量出现异常");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
	}

	/**
	 * 根据cpuid查询此设备下单数量
	 * 
	 * @param cpu_id
	 * @return
	 * @throws CommonException
	 */
	// public String queryRecordCount(String cpu_id, String months) throws
	// CommonException {
	// String total_count = null, months_count = null;
	// Connection conn = JdbcUtils.getConn();
	// ResultSet executeQuery = null;
	// PreparedStatement prepareStatement = null;
	// String sql1 = "select count(*) from consume_record2 where CPU_ID = ? and
	// USE_DESCRIBE = ?";
	// String sql2 = sql1 + "and DATE_FORMAT(DATE,'%Y-%m') = ?";
	// try {
	// prepareStatement = conn.prepareStatement(sql1);
	// prepareStatement.setString(1, cpu_id);
	// prepareStatement.setString(2, "end");
	// executeQuery = prepareStatement.executeQuery();
	// while (executeQuery.next()) {
	// total_count = executeQuery.getString("count(*)");
	// }
	// prepareStatement = conn.prepareStatement(sql2);
	// prepareStatement.setString(1, cpu_id);
	// prepareStatement.setString(2, "end");
	// prepareStatement.setString(3, months);
	// executeQuery = prepareStatement.executeQuery();
	// while (executeQuery.next()) {
	// months_count = executeQuery.getString("count(*)");
	// }
	// return total_count + "," + months_count;
	// } catch (SQLException e) {
	// LogRecord.writeLog(e.getMessage());
	// throw new CommonException("查询设备订单数量出现异常");
	// } finally {
	// JdbcUtils.close(executeQuery, prepareStatement, conn);
	// }
	// }

	/**
	 * 查询订单中是否存入了该订单号true:表中已插入该订单，false：表中尚未插入该订单
	 * 
	 * @param out_trade_no
	 * @return
	 */
	public boolean queryOutTradeNo(String out_trade_no) {
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from transaction_result_notification where OUT_TRADE_NO = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, out_trade_no);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				return true;
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return false;
	}

	/**
	 * 查询设备类型
	 * 
	 * @return
	 */
	public List<MachineType> queryMchType() {
		List<MachineType> list = new ArrayList<MachineType>();
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from machine_type";
		try {
			prepareStatement = conn.prepareStatement(sql);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				MachineType machineType = new MachineType();
				machineType.setId(executeQuery.getInt("ID"));
				machineType.setType(executeQuery.getString("TYPE"));
				list.add(machineType);
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return list;
	}

	/**
	 * 美容院查询
	 * 
	 * @return
	 */
	public List<Beauty> queryBeauty() {
		List<Beauty> list = new ArrayList<Beauty>();
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from beauty";
		try {
			prepareStatement = conn.prepareStatement(sql);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				Beauty machineType = new Beauty();
				machineType.setId(executeQuery.getInt("ID"));
				machineType.setBeauty_name(executeQuery.getString("BEAUTY_NAME"));
				machineType.setContacts(executeQuery.getString("CONTACTS"));
				machineType.setPhone(executeQuery.getString("PHONE"));
				machineType.setAddress(executeQuery.getString("ADDRESS"));
				machineType.setRegist_date(executeQuery.getString("REGIST_DATE"));
				list.add(machineType);
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return list;
	}

	/**
	 * 查询美容卡
	 * 
	 * @return
	 */
	public List<Card> queryCard() {
		List<Card> list = new ArrayList<Card>();
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from card";
		try {
			prepareStatement = conn.prepareStatement(sql);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				Card machineType = new Card();
				machineType.setId(executeQuery.getInt("ID"));
				machineType.setCard_name(executeQuery.getString("CARD_NAME"));
				list.add(machineType);
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return list;
	}

	/**
	 * 根据美容院查询美容院美容卡
	 * 
	 * @return
	 * @throws CommonException
	 */
	public List<BeautyCard> queryBeautyCard(String beauty_name) throws CommonException {
		List<BeautyCard> list = new ArrayList<BeautyCard>();
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from beauty_card where BIND_BEAUTY = ? order by CUSTOM_TIMES ASC";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, beauty_name);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				BeautyCard machineType = new BeautyCard();
				machineType.setId(executeQuery.getInt("ID"));
				machineType.setBeauty_card_name(executeQuery.getString("BEAUTY_CARD_NAME"));
				machineType.setCustom_price(executeQuery.getInt("CUSTOM_PRICE"));
				machineType.setCustom_times(executeQuery.getInt("CUSTOM_TIMES"));
				list.add(machineType);
			}
		} catch (SQLException e) {
//			LogRecord.writeLog(e.getMessage());
//			throw new CommonException("查询美容院美容卡失败");
			e.printStackTrace();
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return list;
	}

	/**
	 * 根据美容院美容卡id获取美容卡名称、原价、原时长、和绑定美容院
	 * 
	 * @return
	 * @throws CommonException
	 */
	public List<String> queryBeautyCardNameById(String beauty_card_id) {
		List<String> cards = new ArrayList<String>();
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from beauty_card where ID = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, beauty_card_id);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				cards.add(executeQuery.getString("BIND_BEAUTY"));
				cards.add(executeQuery.getString("BEAUTY_CARD_NAME"));
				cards.add(executeQuery.getString("CUSTOM_PRICE"));
				cards.add(executeQuery.getString("CUSTOM_TIMES"));
				return cards;
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return null;
	}

	/**
	 * 根据设备ID号查询(设备id号是唯一的),并提交当前软件版本号和固件版本号
	 * 
	 * @param cup_id
	 * @return
	 * @throws CommonException
	 */
	public List<Machine> queryMachine(String cpu_id, String app_version, String firmware_version)
			throws CommonException {
		List<Machine> list = new ArrayList<Machine>();
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from machine where CPU_ID = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, cpu_id);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				Machine machineType = new Machine();
				machineType.setId(executeQuery.getInt("ID"));
				machineType.setBind_beauty(executeQuery.getString("BIND_BEAUTY"));
				machineType.setBind_mch_type(executeQuery.getString("BIND_MCH_TYPE"));
				machineType.setCpu_id(executeQuery.getString("CPU_ID"));
				machineType.setCooperation_mode(executeQuery.getString("COOPERATION_MODE"));
				machineType.setInto_date(executeQuery.getString("INTO_DATE"));
				list.add(machineType);

				String sql2 = "update machine set APP_VERSION = ? , FIRMWARE_VERSION = ? where CPU_ID = ?";
				prepareStatement = conn.prepareStatement(sql2);
				prepareStatement.setString(1, app_version);
				prepareStatement.setString(2, firmware_version);
				prepareStatement.setString(3, cpu_id);
				prepareStatement.executeUpdate();
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("查询设备ID失败");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return list;
	}

	/**
	 * 获取mch指令
	 * 
	 * @throws CommonException
	 */
	public String getMchOrder(String cpu_id) throws CommonException {
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from mch_order where CPU_ID = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, cpu_id);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				return executeQuery.getString("ORDER");
			}
		} catch (SQLException e) {
			throw new CommonException("获取mch指令出错");
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
		return null;
	}

	/**
	 * 美容院登录验证
	 * 
	 * @param beauty_name
	 * @param password
	 * @return
	 * @throws CommonException
	 */
	public boolean beautyLogin(String beauty_name, String beauty_password) throws CommonException {
		PreparedStatement prepareStatement;
		ResultSet executeQuery;
		Connection conn = JdbcUtils.getConn();
		String sql = "select BEAUTY_PASSWORD from beauty where BEAUTY_NAME = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, beauty_name);
			executeQuery = prepareStatement.executeQuery();
			if (executeQuery.next()) {
				if (beauty_password.equals(executeQuery.getString("BEAUTY_PASSWORD"))) {
					return true;
				} else {
					return false;
				}
			} else {
				throw new CommonException("不存在此美容院");
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("查询美容院登录错误");
		}
	}

	/**
	 * 客户登录验证
	 * 
	 * @param beauty_name
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean customerLogin(String customer_name, String password, String bind_beauty) throws Exception {
		PreparedStatement prepareStatement;
		ResultSet executeQuery;
		Connection conn = JdbcUtils.getConn();
		String sql = "select PASSWORD from customer where CUSTOMER_NAME = ? and BIND_BEAUTY = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, customer_name);
			prepareStatement.setString(2, bind_beauty);
			executeQuery = prepareStatement.executeQuery();
			if (executeQuery.next()) {
				if (password.equals(executeQuery.getString("PASSWORD"))) {
					return true;
				} else {
					return false;
				}
			} else {
				throw new Exception("用户尚未注册或者不是此美容院绑定会员");
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据绑定美容院获取商户appid,mch_id和key
	 * 
	 * @param bind_beauty
	 * @return
	 */
	public List<Merchant> queryMerchant(String bind_beauty) {
		List<Merchant> list = new ArrayList<Merchant>();
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from merchant where BIND_BEAUTY = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, bind_beauty);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				Merchant merchant = new Merchant();
				merchant.setAppid(executeQuery.getString("APPID"));
				merchant.setMch_id(executeQuery.getString("MCH_ID"));
				merchant.setKey(executeQuery.getString("KEY"));
				list.add(merchant);
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			e.printStackTrace();
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return list;
	}

	/**
	 * 根据客户名称查询已购买的美容卡
	 * 
	 * @param customer_name
	 * @return
	 * @throws CommonException
	 */
	public List<BuyCardRecord> queryBuyCardRecord(String customer_name) throws CommonException {
		List<BuyCardRecord> list = new ArrayList<BuyCardRecord>();
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from buy_card_record where BUY_CARD_CUSTOMER = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, customer_name);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				BuyCardRecord merchant = new BuyCardRecord();
				merchant.setId(executeQuery.getInt("ID"));
				merchant.setBuy_card_name(executeQuery.getString("BUY_CARD_NAME"));
				merchant.setCustom_price(executeQuery.getInt("CUSTOM_PRICE"));
				merchant.setSell_price(executeQuery.getInt("SELL_PRICE"));
				merchant.setSell_times(executeQuery.getInt("SELL_TIMES"));
				merchant.setRemain_times(executeQuery.getInt("REMAIN_TIMES"));
				list.add(merchant);
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("查询已购买卡失败");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return list;

	}

	/**
	 * 修改客户会员密码
	 * 
	 * @param customer_name
	 * @param new_password
	 * @throws CommonException
	 */
	public void updateCustomerPsw(String customer_name, String new_password) throws CommonException {
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "update customer set PASSWORD = ? where CUSTOMER_NAME = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, new_password);
			prepareStatement.setString(2, customer_name);
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("修改客户会员密码失败");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
	}

	/**
	 * 更新剩余时间
	 * 
	 * @param card_id
	 * @throws CommonException
	 */
	public boolean updateRemainTimes(String card_id, String remain_times) throws CommonException {
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "update buy_card_record set REMAIN_TIMES = ? where ID = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, remain_times);
			prepareStatement.setString(2, card_id);
			prepareStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("更新剩余时间失败");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
	}

	/**
	 * 更新剩余预存款,并在更新后检查剩余预存款是否够下次使用，如果不够，返回false，设备停机
	 * 
	 * @param card_id
	 * @throws CommonException
	 */
	public boolean updateRemainAdvance(String beauty) throws CommonException {
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "update beauty set REMAINING_ADVANCE = REMAINING_ADVANCE - UNIT_PRICE where BEAUTY_NAME = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, beauty);
			prepareStatement.executeUpdate();
			String advancePayment = queryAdvancePayment(beauty);
			if (Integer.parseInt(advancePayment.split(",")[1]) < Integer.parseInt(advancePayment.split(",")[2])) {
				return false;
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("更新更新剩余预存款失败");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return true;
	}

	/**
	 * 插入在线设备
	 * 
	 * @param cpu_id
	 * @throws CommonException
	 */
	public void insertOnline(String customer_name) throws CommonException {
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "insert into online values(null,?,?)";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, customer_name);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			prepareStatement.setString(2, dateFormat.format(new Date()));
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("插入在线用户时错误");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
	}

	/**
	 * 判断是否在线
	 * 
	 * @param cpu_id
	 * @throws CommonException
	 */
	public boolean checkOnline(String customer_name) throws CommonException {
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "select * from online where CUSTOMER_NAME = ?";
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, customer_name);
			executeQuery = prepareStatement.executeQuery();
			while (executeQuery.next()) {
				return true;
			}
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("查询错误");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return false;
	}

	/**
	 * 移除在线设备
	 * 
	 * @param cpu_id
	 * @throws CommonException
	 */
	public void removeOnline(String customer_name) throws CommonException {
		Connection conn = JdbcUtils.getConn();
		PreparedStatement prepareStatement = null;
		String sql_delete = "delete from online where CUSTOMER_NAME = ?";
		try {
			prepareStatement = conn.prepareStatement(sql_delete);
			prepareStatement.setString(1, customer_name);
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			LogRecord.writeLog(e.getMessage());
			throw new CommonException("移除在线设备时错误");
		} finally {
			JdbcUtils.close(null, prepareStatement, conn);
		}
	}

	/**
	 * 更新消费信息，判断beauty和customer的值，如果不为0，就更新对应的值，为0不管
	 * 
	 * @throws CommonException
	 * 
	 */
	public String updateConsumeRecords(String beauty_times, String customer_times, String card_id, String beauty,
			String cpu_id) throws CommonException {
		String[] return_result = new String[] { "next_default", "next_default" };
		Connection conn = JdbcUtils.getConn();
		ResultSet executeQuery = null;
		PreparedStatement prepareStatement = null;
		String sql = "update beauty set REMAINING_ADVANCE = REMAINING_ADVANCE - UNIT_PRICE where BEAUTY_NAME = ?";
		String sql1 = "update buy_card_record set REMAIN_TIMES = REMAIN_TIMES - 60 where ID = ?";
		try {
			if (beauty_times.equals("0")) {
				// 判断下次是否还有剩余
				String advancePayment = queryAdvancePayment(beauty);
				if (Integer.parseInt(advancePayment.split(",")[1]) >= Integer.parseInt(advancePayment.split(",")[2])) {
					prepareStatement = conn.prepareStatement(sql);
					prepareStatement.setString(1, beauty);
					prepareStatement.executeUpdate();
					// 添加消费记录信息
					insertConsumeOrder(beauty, cpu_id);
					return_result[0] = "next_yes";
				} else {
					return_result[0] = "next_no";
				}
			}
			if (customer_times.equals("0")) {
				// 判断下次是否还有剩余
				String cardRemainTime = queryCardRemainTimes(card_id);
				if (Integer.parseInt(cardRemainTime) > 0) {
					prepareStatement = conn.prepareStatement(sql1);
					prepareStatement.setString(1, card_id);
					prepareStatement.executeUpdate();
					// 添加消费记录信息
					insertConsumeOrder(card_id, cpu_id);
					return_result[1] = "next_yes";
				} else {
					return_result[1] = "next_no";
				}
			}
		} catch (SQLException e) {
			throw new CommonException("更新更新消费信息失败");
		} finally {
			JdbcUtils.close(executeQuery, prepareStatement, conn);
		}
		return return_result[0] + "," + return_result[1];
	}
}
