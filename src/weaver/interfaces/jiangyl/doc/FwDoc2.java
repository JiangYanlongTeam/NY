package weaver.interfaces.jiangyl.doc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.company.DepartmentComInfo;
import weaver.hrm.resource.ResourceComInfo;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 党委发文
 */
public class FwDoc2 extends BaseBean implements Action {

	String ip = getPropValue("Doc_column", "IP");
	String port = getPropValue("Doc_column", "PORT");
	String database = getPropValue("Doc_column", "DATABASE");
	String username = getPropValue("Doc_column", "USER");
	String PASSWD = getPropValue("Doc_column", "PASSWD");
	String ftp_ip = getPropValue("Doc_column", "FTP_IP");
	String ftp_port = getPropValue("Doc_column", "FTP_PORT");
	String ftp_username = getPropValue("Doc_column", "FTP_USERNAME");
	String ftp_password = getPropValue("Doc_column", "FTP_PASSWORD");
	String swfj_value = "";
	String zw_value = "";

	public String execute(RequestInfo request) {
		String requestid = request.getRequestid();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();

		String ngr_value = "";
		String ngbm_value = "";
		String lxdh_value = "";
		String ngrq_value = "";
		String hj_value = "";
		String yffs_value = "";
		String fwbmfzr_value = "";
		String fwbmoams_value = "";
		String fwzh_value = "";
		String fwlx_value = "";
		String zs_value = "";
		String cwrq_value = "";
		String cs_value = "";
		String fwrq_value = "";
		String fwsx_value = "";
		String dyffr_value = "";
		String fwbmfgxld_value = "";
		String btc_value = "";
		String sffb_value = "";

		// 判断提交还是退回
		String src = request.getRequestManager().getSrc();
		// 如果是退回，则不执行接口操作
		if (!"submit".equals(src)) {
			return SUCCESS;
		}
		FWModel model = new FWModel();
		Property[] properties = request.getMainTableInfo().getProperty();// 获取表单主字段信息
		for (int i = 0; i < properties.length; i++) {
			String name = properties[i].getName();// 主字段名称
			String value = Util.null2String(properties[i].getValue());// 主字段对应的值
			if (name.equalsIgnoreCase(model.getDwfwlx_column())) {
				model.setDwfwlx(getFwlxChinese(value));
			}
			if (name.equalsIgnoreCase(model.getFwzh_column())) {
				model.setFwzh(value);
			}
			if (name.equalsIgnoreCase(model.getXldqf_column())) {
				try {
					model.setXldqf(new ResourceComInfo().getLastname(value));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (name.equalsIgnoreCase(model.getNgbm_column())) {
				try {
					model.setNgbm(new ResourceComInfo().getLastname(value));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (name.equalsIgnoreCase(model.getFwrq_column())) {
				model.setFwrq(value);
			}
			if (name.equalsIgnoreCase(model.getNgr_column())) {
				model.setNgr(value);
			}
			if (name.equalsIgnoreCase("fj")) {
				swfj_value = value;
			}
			if (name.equalsIgnoreCase("zw")) {
				zw_value = value;
			}
			if (name.equalsIgnoreCase("ngr")) {
				if (!"".equals(value)) {
					try {
						ngr_value = new ResourceComInfo().getLastname(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (name.equalsIgnoreCase("ngbm")) {
				if (!"".equals(value)) {
					try {
						ngbm_value = new DepartmentComInfo().getDepartmentname(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (name.equalsIgnoreCase("lxdh")) {
				lxdh_value = value;
			}
			if (name.equalsIgnoreCase("ngrq")) {
				ngrq_value = value;
			}
			if (name.equalsIgnoreCase("hj")) {
				if (!"".equals(value)) {
					hj_value = getNameByFieldId("5847", value);
				}
			}
			if (name.equalsIgnoreCase("yffs")) {
				yffs_value = value;
			}
			if (name.equalsIgnoreCase("fwbmfzr")) {
				if (!"".equals(value)) {
					try {
						fwbmfzr_value = new ResourceComInfo().getLastname(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (name.equalsIgnoreCase("fwbmoams")) {
				if (!"".equals(value)) {
					try {
						fwbmoams_value = new ResourceComInfo().getLastname(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (name.equalsIgnoreCase("fwzh")) {
				fwzh_value = value;
			}
			if (name.equalsIgnoreCase("fwlx")) {
				if (!"".equals(value)) {
					fwlx_value = getNameByFieldId("5868", value);
				}
			}
			if (name.equalsIgnoreCase("zs")) {
				zs_value = value;
			}
			if (name.equalsIgnoreCase("cwrq")) {
				cwrq_value = value;
			}
			if (name.equalsIgnoreCase("cs")) {
				cs_value = value;
			}
			if (name.equalsIgnoreCase("fwrq")) {
				fwrq_value = value;
			}
			if (name.equalsIgnoreCase("fwsx")) {
				if (!"".equals(value)) {
					fwsx_value = getNameByFieldId("5869", value);
				}
			}
			if (name.equalsIgnoreCase("dyffr")) {
				if (!"".equals(value)) {
					try {
						dyffr_value = new ResourceComInfo().getLastname(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (name.equalsIgnoreCase("fwbmfgxld")) {
				if (!"".equals(value)) {
					try {
						fwbmfgxld_value = new ResourceComInfo().getLastname(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (name.equalsIgnoreCase("btc")) {
				btc_value = value;
			}
			if (name.equalsIgnoreCase("sffb")) {
				if ("".equals(value)) {
					sffb_value = getNameByFieldId("5862", value);
				}
			}
		}
		model.setRequestname(request.getRequestManager().getRequestname());
		String year = new SimpleDateFormat("yyyy").format(new Date());
		String month = new SimpleDateFormat("MM").format(new Date());
		if (month.length() == 1) {
			month = "0" + month;
		}
		String dirid = getDirIdFromu_dag_fl("发文", year, month);
		if ("".equals(dirid)) {
			int maxid = getMaxDirId();
			insertDirId(maxid, "发文", year, month);
			dirid = getDirIdFromu_dag_fl("发文", year, month);
		}
		model.setDirid(dirid);
		int fwid = getMaxId();
		model.setId(String.valueOf(fwid));
		insertFw(model);
		FTPHelper helper = new FTPHelper();
		boolean isconnected = false;
		try {
			isconnected = helper.connect("/userfile/dag/dba/fw/attach/", ftp_ip, Integer.parseInt(ftp_port),
					ftp_username, ftp_password);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeLog("连接FTP：" + isconnected);
		if (isconnected) {
			if (!"".equals(swfj_value) && "-2".equals(zw_value)) {
				String[] strs = swfj_value.split(",");
				for (int i = 0; i < strs.length; i++) {
					String docid = strs[i];
					String sql = "select b.imagefilename,b.filerealpath,b.filesize,b.aescode from DocImageFile a,imagefile b where a.imagefileid = b.imagefileid and a.docid = '"
							+ docid + "'";
					writeLog("查询文档sql：" + sql);
					rs.execute(sql);
					rs.next();
					String imagefilename = Util.null2o(rs.getString("imagefilename"));
					String filerealpath = Util.null2o(rs.getString("filerealpath"));
					File file = new File(filerealpath);
					String fileName = file.getName();
					String ftype = getPrefix(file);
					String ftypename = getPrefix(imagefilename);
					String rPath = "";
					if ("zip".equals(ftype)) {
						Unzip unZip = new Unzip();
						unZip.unZip(filerealpath, filerealpath.split(fileName)[0]);
						rPath = filerealpath.split(fileName)[0] + getFileNameWithoutPrefix(fileName);
					}
					boolean issuccess = false;
					try {
						issuccess = helper.upload(new File(rPath), "fw_" + fwid + "_4_" + i + "." + ftypename);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (issuccess) {
						insertTx("系统管理员", String.valueOf(fwid), "4", String.valueOf(i), imagefilename, "",
								"fw_" + fwid + "_4_" + i, "0");
					}
				}
			}
			if (!"".equals(zw_value)) {
				String[] strs = zw_value.split(",");
				for (int i = 0; i < strs.length; i++) {
					String docid = strs[i];
					String sql = "select b.imagefilename,b.filerealpath,b.filesize,b.aescode from DocImageFile a,imagefile b where a.imagefileid = b.imagefileid and a.docid = '"
							+ docid + "'";
					writeLog("查询文档sql：" + sql);
					rs1.execute(sql);
					rs1.next();
					String imagefilename = Util.null2o(rs1.getString("imagefilename"));
					String filerealpath = Util.null2o(rs1.getString("filerealpath"));
					File file = new File(filerealpath);
					String fileName = file.getName();
					String ftype = getPrefix(file);
					String ftypename = getPrefix(imagefilename);
					String rPath = "";
					if ("zip".equals(ftype)) {
						Unzip unZip = new Unzip();
						unZip.unZWZip(filerealpath, filerealpath.split(fileName)[0]);
						// rPath = filerealpath.split(fileName)[0] + getFileNameWithoutPrefix(fileName);
						rPath = filerealpath.split(fileName)[0] + "fw.doc";
					}
					boolean issuccess = false;
					try {
						issuccess = helper.upload(new File(rPath),
								"fw_" + fwid + "_4_" + (swfj_value.split(",").length + i) + "." + ftypename);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (issuccess) {
						insertTx("系统管理员", String.valueOf(fwid), "4", String.valueOf((swfj_value.split(",").length + i)),
								imagefilename, "", "fw_" + fwid + "_4_" + (swfj_value.split(",").length + i), "0");
					}
				}
			}
			StringBuffer html = new StringBuffer();
			WorkflowToDocDataUtil util = new WorkflowToDocDataUtil();
			html.append(util.getHtmlHeader("党委发文"));
			StringBuffer body = new StringBuffer();

			body.append(addHeader());
			body.append(addBody("拟稿人", ngr_value, "拟稿部门", ngbm_value));
			body.append(addBody("联系电话", lxdh_value, "拟稿日期", ngrq_value));
			body.append(addBody("缓急", hj_value, "印发份数", yffs_value));
			body.append(addBody("发文部门负责人", fwbmfzr_value, "成文日期", cwrq_value));
			body.append(addBody("发文类型", fwlx_value, "发文部门OA秘书", fwbmoams_value));
			body.append(addBody("发文部门分管校领导", fwbmfgxld_value));
			body.append(addBody("发文字号", fwzh_value, "发文日期", fwrq_value));
			body.append(addBody("标题", request.getRequestManager().getRequestname()));
			body.append(addBody("主题词", btc_value));
			body.append(addBody("主送", zs_value));
			body.append(addBody("抄送", cs_value));
			body.append(addBody("是否发布", sffb_value));
			body.append(addBody("发文属性", fwsx_value, "打印分发人", dyffr_value));
			body.append(addBody("归档标记", "是"));
			body.append(addFooter());

			html.append(body.toString());
			html.append(util.getSignTitle());
			html.append(util.getWorkflowSignData(requestid));
			html.append(util.getHtmlFooter());
			CreateDoc createdoc = new CreateDoc();
			int _docid = createdoc.createDocFile(request.getRequestManager().getRequestname(),
					request.getRequestManager().getRequestname() + ".html", 1172, html.toString());

			String gdsql = "select b.imagefilename,b.filerealpath,b.filesize,b.aescode from DocImageFile a,imagefile b where a.imagefileid = b.imagefileid and a.docid = '"
					+ _docid + "' ";
			writeLog("查询归档文档sql：" + gdsql);
			rs2.execute(gdsql);
			rs2.next();
			String imagefilename = Util.null2o(rs2.getString("imagefilename"));
			String filerealpath = Util.null2o(rs2.getString("filerealpath"));
			writeLog("归档文档附件 " + _docid + " 名字：" + imagefilename);
			writeLog("归档文档附件 " + _docid + " 路径：" + filerealpath);
			File file = new File(filerealpath);
			String fileName = file.getName();
			String ftype = getPrefix(file);
			String ftypename = getPrefix(imagefilename);
			writeLog("归档文档附件 " + _docid + " 类型：" + ftypename);
			String rPath = "";
			if ("zip".equals(ftype)) {
				Unzip unZip = new Unzip();
				unZip.unZip(filerealpath, filerealpath.split(fileName)[0]);
				rPath = filerealpath.split(fileName)[0] + getFileNameWithoutPrefix(fileName);
				writeLog("归档附件 " + _docid + " 解压路径：" + rPath);
			}
			String htmlname = "fw_" + fwid + "_4_" + (swfj_value.split(",").length + zw_value.split(",").length) + "."
					+ ftypename;
			writeLog("归档html上传到FTP名称：" + htmlname);
			boolean issuccess = false;
			try {
				issuccess = helper.upload(new File(rPath), htmlname);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (issuccess) {
				insertTx("系统管理员", String.valueOf(fwid), "4",
						String.valueOf((swfj_value.split(",").length + zw_value.split(",").length)), imagefilename, "",
						"fw_" + fwid + "_4_" + (swfj_value.split(",").length + zw_value.split(",").length), "0");
			}
			helper.disconnect();
			String sql = "update formtable_main_4 set gdbj = '1' where requestid = '" + requestid + "'";
			rs.execute(sql);
		}
		return SUCCESS;
	}

	/**
	 * 设置失败消息提醒
	 * 
	 * @param request
	 * @param failid
	 * @param message
	 */
	public void setFailedMessage(RequestInfo request, String failid, String message) {
		request.getRequestManager().setMessageid(failid);
		request.getRequestManager().setMessagecontent(message);
	}

	public int getMaxDirId() {
		int dirid = 0;
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ip + ":" + port + "; DatabaseName=" + database + "", "" + username + "",
					"" + PASSWD + "");
			statement = conn.createStatement();
			rs = statement.executeQuery("select max(id) id from u_dag_fl");
			while (rs.next()) {
				int id = rs.getInt("id");
				if (id != 0) {
					dirid = id + 1;
				} else {
					dirid = id;
				}
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			rs = null;
			statement = null;
			conn = null;
		}
		return dirid;
	}

	/**
	 * 获取u_dag_fl 对应id
	 * 
	 * @param column1
	 *            "收文" 或 "发文"
	 * @param column2
	 *            年
	 * @param column3
	 *            月
	 * @return
	 */
	public String getDirIdFromu_dag_fl(String column1, String column2, String column3) {
		String dirid = "";
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ip + ":" + port + "; DatabaseName=" + database + "", "" + username + "",
					"" + PASSWD + "");
			statement = conn.createStatement();
			rs = statement.executeQuery("select * from u_dag_fl where c1 = '" + column1 + "' and c2 = '" + column2
					+ "' and c3 = '" + column3 + "'");
			while (rs.next()) {
				String id = rs.getString("id");
				if (null != id) {
					dirid = id;
				}
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			rs = null;
			statement = null;
			conn = null;
		}
		return dirid;
	}

	/**
	 * 获取u_dag_fl 对应id
	 * 
	 * @param column1
	 *            "收文" 或 "发文"
	 * @param column2
	 *            年
	 * @param column3
	 *            月
	 * @return
	 */
	public int getMaxId() {
		int dirid = 0;
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ip + ":" + port + "; DatabaseName=" + database + "", "" + username + "",
					"" + PASSWD + "");
			statement = conn.createStatement();
			rs = statement.executeQuery("select max(id) id from u_fw");
			while (rs.next()) {
				String id = rs.getString("id");
				if (null != id) {
					dirid = Integer.parseInt(id) + 1;
				}
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			rs = null;
			statement = null;
			conn = null;
		}
		return dirid;
	}

	/**
	 * 插入目录信息
	 * 
	 * @param column1
	 *            "收文" 或 "发文"
	 * @param column2
	 *            年
	 * @param column3
	 *            月
	 * @return
	 */
	public void insertDirId(int maxid, String column1, String column2, String column3) {
		PreparedStatement statement = null;
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ip + ":" + port + "; DatabaseName=" + database + "", "" + username + "",
					"" + PASSWD + "");
			statement = conn.prepareStatement("insert into u_dag_fl (c1,c2,c3,id) values (?,?,?,?)");
			statement.setString(1, column1);
			statement.setString(2, column2);
			statement.setString(3, column3);
			statement.setInt(4, maxid);
			int res = statement.executeUpdate();
			writeLog("执行插入u_dag_fl表返回值：" + res);
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			statement = null;
			conn = null;
		}
	}

	/**
	 * 插入目录信息
	 * 
	 * @param column1
	 *            "收文" 或 "发文"
	 * @param column2
	 *            年
	 * @param column3
	 *            月
	 * @return
	 */
	public void insertTx(String column1, String column2, String column3, String column4, String column5, String column6,
			String column7, String column8) {
		PreparedStatement statement = null;
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ip + ":" + port + "; DatabaseName=" + database + "", "" + username + "",
					"" + PASSWD + "");
			statement = conn.prepareStatement(
					"insert into u_fw_tx (mlh,ajh,mtlx,yh,ztm,fz,cph,filesize) values (?,?,?,?,?,?,?,?)");
			statement.setString(1, column1);
			statement.setFloat(2, Float.parseFloat(column2));
			statement.setInt(3, Integer.parseInt(column3));
			statement.setString(4, column4);
			statement.setString(5, column5);
			statement.setString(6, column6);
			statement.setString(7, column7);
			statement.setFloat(8, Float.parseFloat(column8));
			int res = statement.executeUpdate();
			writeLog("执行插入u_fw_tx表返回值：" + res);
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			statement = null;
			conn = null;
		}
	}

	/**
	 * 插入fw信息
	 * 
	 * @param column1
	 *            "收文" 或 "发文"
	 * @param column2
	 *            年
	 * @param column3
	 *            月
	 * @return
	 */
	public void insertFw(FWModel model) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://" + ip + ":" + port + "; DatabaseName=" + database + "", "" + username + "",
					"" + PASSWD + "");
			statement = conn.prepareStatement(
					"insert into u_fw (id,dir,tag,ajh,ztm,wh,zrz,dw,gdr,sry) values (?,?,?,?,?,?,?,?,?,?)");
			statement.setString(1, model.getId());
			statement.setString(2, model.getDirid());
			statement.setString(3, model.getFwlx());
			statement.setString(4, model.getAjh());
			statement.setString(5, model.getRequestname());
			statement.setString(6, model.getFwzh());
			statement.setString(7, model.getXldqf());
			statement.setString(8, model.getNgbm());
			statement.setString(9, model.getFwrq());
			statement.setString(10, model.getNgr_column());
			int res = statement.executeUpdate();
			writeLog("执行插入u_fw表返回值：" + res);
			statement.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement = null;
			conn = null;
		}
	}

	/**
	 * 获取文件类型
	 * 
	 * @param s
	 * @return
	 */
	public String getType(String s) {
		return s.substring(s.lastIndexOf(".") + 1, s.length());
	}

	/**
	 * 获取文件后缀名 如：.zip返回zip，如果没有后缀名，则返回文件全名
	 * 
	 * @param file
	 * @return
	 */
	public String getPrefix(File file) {
		String fileName = file.getName();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return prefix;
	}

	/**
	 * 获取文件后缀名 如：.zip返回zip，如果没有后缀名，则返回文件全名
	 * 
	 * @param file
	 * @return
	 */
	public String getPrefix(String file) {
		String prefix = file.substring(file.lastIndexOf(".") + 1);
		return prefix;
	}

	/**
	 * 获取文件名字，不要后缀
	 * 
	 * @param s
	 * @return
	 */
	public String getFileNameWithoutPrefix(String s) {
		return s.substring(0, s.lastIndexOf("."));
	}

	/**
	 * 获取发文类型
	 * 
	 * @param fwlx
	 * @return
	 */
	public String getFwlxChinese(String fwlx) {
		RecordSet rs = new RecordSet();
		rs.execute("select selectname,selectvalue from workflow_selectitem where fieldid = '6098' and selectvalue = '"
				+ fwlx + "'");
		rs.next();
		return rs.getString("selectname");
	}

	public String getNameByFieldId(String fieldid, String selectvalue) {
		String sql = "select selectvalue,selectname from workflow_selectitem where fieldid = '" + fieldid
				+ "' and selectvalue = '" + selectvalue + "'";
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		rs.next();
		String selectname = rs.getString("selectname");
		return selectname;
	}

	public String addHeader() {
		StringBuffer sb = new StringBuffer("<div>");
		sb.append(
				"<table width=\"100%\" border=0 style=\"font-size:9pt;font-family:Microsoft YaHei !important;border-collapse:collapse;border-color:#005782 !important;border-width:0px !important;margin-top:4px !important;text-align:left\">");
		return sb.toString();
	}

	public String addBody(String name, String value, String name1, String value1) {
		StringBuffer sb = new StringBuffer("<tr>");
		sb.append(
				"<td style=\"padding:0px;padding:0px;height:30px;background-color:#ebf5ff !important;border:1px solid #b0d6fe !important\">");
		sb.append(name);
		sb.append("</td>");
		sb.append(
				"<td style=\"padding:0px;padding:0px;border:1px solid #b0d6fe !important;word-break:break-all;word-wrap:break-word\">");
		sb.append(value);
		sb.append("</td>");
		sb.append(
				"<td style=\"padding:0px;padding:0px;height:30px;background-color:#ebf5ff !important;border:1px solid #b0d6fe !important\">");
		sb.append(name1);
		sb.append("</td>");
		sb.append(
				"<td style=\"padding:0px;padding:0px;border:1px solid #b0d6fe !important;word-break:break-all;word-wrap:break-word\">");
		sb.append(value1);
		sb.append("</td>");
		sb.append("</tr>");
		return sb.toString();
	}

	public String addBody(String name, String value) {
		StringBuffer sb = new StringBuffer("<tr>");
		sb.append(
				"<td style=\"padding:0px;padding:0px;height:30px;background-color:#ebf5ff !important;border:1px solid #b0d6fe !important\">");
		sb.append(name);
		sb.append("</td>");
		sb.append(
				"<td style=\"padding:0px;padding:0px;border:1px solid #b0d6fe !important;word-break:break-all;word-wrap:break-word\" colspan=3>");
		sb.append(value);
		sb.append("</td>");
		sb.append("</tr>");
		return sb.toString();
	}

	public String addFooter() {
		StringBuffer sb = new StringBuffer("");
		sb.append("</table></div>");
		return sb.toString();
	}
}
