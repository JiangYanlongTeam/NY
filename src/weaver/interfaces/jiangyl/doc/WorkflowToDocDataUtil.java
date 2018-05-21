package weaver.interfaces.jiangyl.doc;

import weaver.conn.RecordSet;
import weaver.hrm.resource.ResourceComInfo;

/**
 * 拼接归档html
 */
public class WorkflowToDocDataUtil {

	/**
	 * 拼接头部
	 * 
	 * @param headerstr
	 * @return
	 */
	public String getHtmlHeader(String headerstr) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
		sb.append("<body>");
		sb.append("<div style=\"text-align:center;\"><h3>" + headerstr + "</h3></div>");
		return sb.toString();
	}

	/**
	 * 拼接尾部
	 * 
	 * @return
	 */
	public String getHtmlFooter() {
		StringBuffer sb = new StringBuffer();
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	/**
	 * 拼接签字意见
	 * 
	 * @return
	 */
	public String getSignTitle() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div style=\"margin-top:10px;\">");
		sb.append("<label style=\"color:#008df6;cursor:pointer;font-size:9pt;\">流转意见</label>");
		sb.append("</div>");
		return sb.toString();
	}

	/**
	 * 拼接签字意见内容
	 * 
	 * @return
	 */
	public String getWorkflowSignData(String requestid) {
		StringBuffer sb = new StringBuffer();
		RecordSet rsset = new RecordSet();
		RecordSet rs = new RecordSet();
		String sql = "select operator,receivedpersonids,nodeid,"
				+ "(select nodename from workflow_nodebase where id = nodeid) nodename,"
				+ "logtype,operatedate,operatetime from workflow_requestLog a " + "where a.requestid = '" + requestid
				+ "' order by a.operatedate desc,a.operatetime desc";
		rs.execute(sql);
		sb.append("<div>");
		sb.append(
				"<table width=\"100%\" border=0 style=\"font-size:9pt;font-family:Microsoft YaHei !important;border-collapse:collapse;border-color:#005782 !important;border-width:0px !important;margin-top:4px !important;text-align:left\">");
		while (rs.next()) {
			String operator = rs.getString("operator");
			String receivedpersonids = rs.getString("receivedpersonids");
			String nodename = rs.getString("nodename");
			String logtype = rs.getString("logtype");
			String operatedate = rs.getString("operatedate");
			String operatetime = rs.getString("operatetime");
			String hrmname = "";
			if (operator.equals("1")) {
				hrmname = "系统管理员";
			} else {
				try {
					hrmname = new ResourceComInfo().getLastname(operator);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String departmentname = "";
			if (!operator.equals("系统管理员")) {
				departmentname = getDepartmentNameByHrmid(operator);
			}
			sb.append("<tr>");
			sb.append("<td style=\"padding:0px;padding:0px;height:30px;border:0px solid !important\">");
			sb.append("" + hrmname + " " + departmentname + "");
			sb.append("</td>");
			String[] reces = receivedpersonids.split(",");
			StringBuffer rece = new StringBuffer("");
			for (int i = 0; i < reces.length; i++) {
				if (!reces[i].equals("1")) {
					rsset.execute("select lastname from hrmresource where id = '" + reces[i] + "'");
					rsset.next();
					String lastname = rsset.getString("lastname");
					rece.append(lastname + " ");
				} else {
					rece.append("系统管理员" + " ");
				}
			}
			sb.append(
					"<td style=\"padding:0px;padding:0px;border:0px solid !important;word-break:break-all;word-wrap:break-word\">");
			sb.append("接收人： " + rece.toString() + " ");
			sb.append("</td>");

			sb.append("<td style=\"padding:0px;padding:0px;height:35px;border:0px solid !important\">");
			sb.append(operatedate + " " + operatetime);
			if (logtype.equals("i")) {
				sb.append("[" + nodename + "  / 流程干预]");
			} else {
				sb.append("[" + nodename + "  / 提交]");
			}
			sb.append("</td>");

			sb.append("</tr>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	private String getDepartmentNameByHrmid(String hrmid) {
		RecordSet rs = new RecordSet();
		rs.execute(
				"select departmentname from hrmdepartment where id = (select departmentid from hrmresource where id = '"
						+ hrmid + "')");
		rs.next();
		String departmentname = rs.getString("departmentname");
		return departmentname;
	}
}