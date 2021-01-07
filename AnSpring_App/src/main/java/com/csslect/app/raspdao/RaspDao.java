package com.csslect.app.raspdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.csslect.app.ardudto.ArduDto;
import com.csslect.app.raspdto.RaspDto;

public class RaspDao {

	DataSource dataSource;

	public RaspDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/team01");
			/*dataSource = (DataSource) context.lookup("java:/comp/env/CSS");*/
		} catch (NamingException e) {
			e.getMessage();
		}

	}

	public ArrayList<RaspDto> raspGetData() {		
		
		ArrayList<RaspDto> adtos = new ArrayList<RaspDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * from rasp"
							+ " where (store_id, table_num, start_time)" 
							+ " in (select store_id, table_num, max(start_time) from rasp group by store_id, table_num )";			
			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				String store_id = resultSet.getString("store_id");
				String store_name = resultSet.getString("store_name");
				String table_num = resultSet.getString("table_num");
				String start_time = resultSet.getString("start_time"); 
				String end_time = resultSet.getString("end_time"); 
				String accupation_time = resultSet.getString("accupation_time"); 
				
				RaspDto adto = new RaspDto(store_id, store_name, table_num, start_time, end_time, accupation_time);
				adtos.add(adto);			
			}	
			
			System.out.println("adtos ũ�� : " + adtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;

	}
	
public ArrayList<RaspDto> RaspSetData(String store_id1, String store_name1, String table_num1, String table_value1) {
	
		String store_id = store_id1, store_name = store_name1, table_num = table_num1, table_value = table_value1;		
		
		int state = -1;		
				
		ArrayList<RaspDto> adtos = new ArrayList<RaspDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "";
			
			// ���Կ� �մ��� �ͼ� ���� �Ѹ� ���� ���̺��� �����̵ǰ�
			if(table_value.equals("ON")) {
				query = "insert into rasp(store_id, store_name, table_num, start_time) "
						+ " values(?, ?, ?, to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss')) ";
				
				prepareStatement = connection.prepareStatement(query);
				prepareStatement.setString(1, store_id);
				prepareStatement.setString(2, store_name);
				prepareStatement.setString(3, table_num);
				state = prepareStatement.executeUpdate();
			
			// �մ��� ������ ���̺� ����� ���Ҿ� �ð��� ����Ͽ� ���̺��� ������Ʈ�ϸ� �����Ѵ�
			}else { 
				query = "update rasp set "
						+ " end_time = to_char(sysdate, 'yyyy-mm-dd hh:mi:ss') ,"
						+ " accupation_time = round(( sysdate  - to_date(start_time, 'yyyy-mm-dd hh24:mi:ss'))*24*60)"
						+ " where store_id = '" + store_id + "' "
						+ "   and table_num = '" + table_num + "' "
						+ "   and end_time is null ";
				
				prepareStatement = connection.prepareStatement(query);				
				state = prepareStatement.executeUpdate();
			}				
			
			if (state > 0) {
				System.out.println(state + "?��?��?���?");				
			} else {
				System.out.println(state + "?��?��?��?��");
			}		


			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;

	}
	
	/*public int anInsertMulti(int id, String name, String date, String dbImgPath, String uploadType, String videoImagePath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
				
		int state = -1;

		try {			
			// 
			connection = dataSource.getConnection();
			String query = "insert into test2(id, name, hire_date, image1, uploadtype, videoImagePath) " + "values(" + id + ",'" + name + "',"
					+ "to_date('" + date + "','rr/mm/dd') , '" + dbImgPath + "', '" + uploadType + "', '" + videoImagePath + "' )";

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "?��?��?���?");				
			} else {
				System.out.println(state + "?��?��?��?��");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 

		}

		return state;

	}
	

	public int anUpdateMulti(int id, String name, String date, String dbImgPath, String uploadtype, String videoImagePath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// ?��?��?��?�� ?��?��?��?�� ?��?��			
			connection = dataSource.getConnection();
			String query = "update test2 set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "
		             + ", image1 = '" + dbImgPath + "' "
		             + ", uploadtype = '" + uploadtype + "' "
		             + ", videoImagePath = '" + videoImagePath + "' "
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("?��?��1?���?");
				
			} else {
				System.out.println("?��?��1?��?��");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	
	}
	
	public int anUpdateMultiNo(int id, String name, String date) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// ?��?��?��?�� ?��?��?��?�� ?��?��			
			connection = dataSource.getConnection();
			String query = "update test2 set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "		             
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("?��?��2?���?");
				
			} else {
				System.out.println("?��?��2?��?��");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	}
	
	public int anDeleteMulti(int id) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "delete from test2 where id=" + id;
			
			System.out.println(id);

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("?��?��?���?");				
			} else {
				System.out.println("?��?��?��?��");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return state;

	}
*/
		
	
	
}
