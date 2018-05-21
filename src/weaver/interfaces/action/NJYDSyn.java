package weaver.interfaces.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import ln.LN;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.MD5;
import weaver.general.Util;
import weaver.hrm.company.DepartmentComInfo;
import weaver.hrm.resource.ResourceComInfo;
import weaver.interfaces.hrm.DepartmentBean;
import weaver.interfaces.hrm.HrmSynService;
import weaver.interfaces.hrm.JobTitleBean;
import weaver.interfaces.hrm.SubCompanyBean;
import weaver.interfaces.hrm.UserBean;

/**
 * 同步人员/部门
 * 
 * @author jiangyanlong
 *
 */
public class NJYDSyn extends BaseBean implements HrmSynService {

	/*
	 * 定时同步人员 从其他系统同步到OA
	 */
	@Override
	public String SynTimingToOAHrmResource() {
		try {
			String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String table = "OAresource.oa_hrinfo";
			String sql = "select * from " + table;
			RecordSet rsd = new RecordSet();
			RecordSet rs = new RecordSet();
			rsd.executeSql(sql);
			while (rsd.next()) {
				String loginid = Util.null2String(rsd.getString("loginid"));
				String name = Util.null2String(rsd.getString("name"));
				String sex = Util.null2String(rsd.getString("sex"));
				String depcode = Util.null2String(rsd.getString("depcode"));
				String subcompanycode = Util.null2String(rsd.getString("subcompanycode"));
				String certificatenum = Util.null2String(rsd.getString("certificatenum"));
				String healthinfo = Util.null2String(rsd.getString("healthinfo"));
				String status = Util.null2String(rsd.getString("status"));
				rs.executeSql("select * from hrmresource where loginid = '" + loginid + "' "); // 查找工号是否存在
				LN l = new LN();
				int lnum = l.CkHrmnum();
				if (lnum > 0) {
					writeLog("同步失败！License用户数量达到最大值，请向泛微购买License!");
					continue;
				}
				String depID = getdepartmentcode(depcode);
				String subcompanyID = getsubcompanycode(subcompanycode);
				if (rs.next()) { // 如果人员存在，更新 姓名 性别 部门 身份证号 人员类别 状态
					if ("".equals(depID)) {
						writeLog("更新人员loginid:" + loginid + "，部门编码在OA中不存在，不进行更新");
						continue;
					}
					if ("".equals(subcompanyID)) {
						writeLog("更新人员loginid:" + loginid + "，公司编码在OA中不存在，不进行更新");
						continue;
					}
					if ("".equals(name)) {
						writeLog("更新人员loginid:" + loginid + "，姓名为空，不进行更新");
						continue;
					}
					if ("".equals(sex)) {
						writeLog("更新人员loginid:" + loginid + "，性别为空，不进行更新");
						continue;
					}
					if ("".equals(certificatenum)) {
						writeLog("更新人员loginid:" + loginid + "，身份证号为空，不进行更新");
						continue;
					}
					if ("".equals(healthinfo)) {
						writeLog("更新人员loginid:" + loginid + "，人员类别为空，不进行更新");
						continue;
					}
					if ("".equals(status)) {
						writeLog("更新人员loginid:" + loginid + "，人员状态为空，不进行更新");
						continue;
					}
					String updateSQL = "update hrmresource set lastname = '" + name + "', sex = '" + sex
							+ "', departmentid = '" + depID + "', certificatenum = '" + certificatenum + "', "
							+ "healthinfo = '" + healthinfo + "', status = '" + status + "' ";
					writeLog("更新人力资源SQL:" + updateSQL);
					rs.execute(updateSQL);
				} else { // 不存在人员，新增
					rs.executeProc("HrmResourceMaxId_Get", "");
					rs.next();
					int maxid = rs.getInt(1);
					String password = new MD5().getMD5ofStr("111111");
					String totalspace = "500";
					String occupyspace = "0";
					if ("".equals(depID)) {
						writeLog("新增人员loginid:" + loginid + "，部门编码在OA中不存在，不进行插入");
						continue;
					}
					if ("".equals(subcompanyID)) {
						writeLog("新增人员loginid:" + loginid + "，公司编码在OA中不存在，不进行插入");
						continue;
					}
					if ("".equals(loginid)) {
						writeLog("新增人员loginid:" + loginid + "，登录账号为空，不进行插入");
						continue;
					}
					if ("".equals(name)) {
						writeLog("新增人员loginid:" + loginid + "，姓名为空，不进行插入");
						continue;
					}
					if ("".equals(sex)) {
						writeLog("新增人员loginid:" + loginid + "，性别为空，不进行插入");
						continue;
					}
					if ("".equals(certificatenum)) {
						writeLog("新增人员loginid:" + loginid + "，身份证号为空，不进行插入");
						continue;
					}
					if ("".equals(healthinfo)) {
						writeLog("新增人员loginid:" + loginid + "，人员类别为空，不进行插入");
						continue;
					}
					if ("".equals(status)) {
						writeLog("新增人员loginid:" + loginid + "，人员状态为空，不进行插入");
						continue;
					}
					String insert_sql = "insert into hrmresource(id,loginid,password,lastname,sex,systemlanguage,locationid,seclevel,departmentid,subcompanyid1,createrid,createdate,certificatenum,workcode,healthinfo,status,accounttype,totalspace,occupyspace) "
							+ "values('" + maxid + "','" + loginid + "','" + password + "','" + name + "','" + sex
							+ "','7','21','10','" + depID + "','" + subcompanyID + "','1','" + createdate + "','"
							+ certificatenum + "','" + loginid + "','" + healthinfo + "','" + status + "','0','"
							+ totalspace + "','" + occupyspace + "')";
					writeLog("新增人员SQL:" + insert_sql);
					boolean succ = rs.executeSql(insert_sql);
					writeLog("新增" + loginid + "=" + succ + ":" + insert_sql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeLog(e);
		}
		// 同步缓存
		try {
			ResourceComInfo rci = new ResourceComInfo();
			rci.removeResourceCache();
		} catch (Exception e) {
			e.printStackTrace();
			writeLog(e);
		}
		return "";
	}

	@Override
	public String SynTimingToOADepartment() {
		try {
			String table = "OAresource.oa_hrdepartment";
			String sql = "select * from " + table;
			RecordSet rs = new RecordSet();
			RecordSet rsd = new RecordSet();
			rsd.executeSql(sql);
			RecordSet rsUpdate = new RecordSet();
			while (rsd.next()) {
				String depcode = Util.null2String(rsd.getString("depcode")); // 部门编码
				String shortnamemark = Util.null2String(rsd.getString("shortnamemark")); // 部门简称
				String departmentname = Util.null2String(rsd.getString("departmentname")); // 部门全程
				String subcompanycode = Util.null2String(rsd.getString("subcompanycode")); // 分部编码
				String supdepcode = Util.null2String(rsd.getString("supdepcode")); // 上级部门编码
				rs.executeSql("select * from hrmdepartment where departmentcode = '" + depcode + "'"); // 查找部门编号是否存在
				if (rs.next()) { // 如果存在，更新部门名称和部门简称
					if ("".equals(departmentname)) {
						writeLog("更新部门departmentcode:" + depcode + "，部门名称为空，不进行更新");
						continue;
					}
					if ("".equals(shortnamemark)) {
						writeLog("更新部门departmentcode:" + depcode + "，部门简称为空，不进行更新");
						continue;
					}
					String updateSQL = "update hrmdepartment set departmentname = '" + departmentname
							+ "', departmentmark = '" + shortnamemark + "' where departmentcode = '" + depcode + "' ";
					writeLog("更新部门名称和部门简称SQL:" + updateSQL);
					rsUpdate.execute(updateSQL);
				} else { // 不存在，新增部门
					// 根据分部编码查询分部ID
					String subcompanyID = getsubcompanycode(subcompanycode);
					String supdepartmentID = getdepartmentcode(supdepcode);
					if ("".equals(depcode)) {
						writeLog("新增部门departmentcode:" + depcode + "，部门为空，不进行更新");
						continue;
					}
					if ("".equals(supdepartmentID)) {
						writeLog("新增部门departmentcode:" + depcode + "，公司编码在OA中不存在，不进行更新");
						continue;
					}
					if ("".equals(supdepartmentID)) {
						writeLog("新增部门departmentcode:" + depcode + "，上级部门编码为空，不进行更新");
						continue;
					}
					if ("".equals(departmentname)) {
						writeLog("新增部门departmentcode:" + depcode + "，部门名称为空，不进行更新");
						continue;
					}
					if ("".equals(shortnamemark)) {
						writeLog("新增部门departmentcode:" + depcode + "，部门简称为空，不进行更新");
						continue;
					}

					String insertSQL = "insert into hrmdepartment (departmentname,departmentmark,departmentcode,SUBCOMPANYID1,supdepid) "
							+ "values ('" + departmentname + "','" + shortnamemark + "','" + depcode + "','"
							+ subcompanyID + "','" + supdepartmentID + "') ";
					writeLog("新增部门SQL:" + insertSQL);
					rsUpdate.execute(insertSQL);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeLog(e);
		}
		// 同步缓存
		try {
			DepartmentComInfo rci = new DepartmentComInfo();
			rci.removeCompanyCache();
		} catch (Exception e) {
			e.printStackTrace();
			writeLog(e);
		}
		return "";
	}

	/**
	 * 根据分部编码获取分部ID
	 * 
	 * @param departmentcode
	 * @return
	 */
	public String getdepartmentcode(String departmentcode) {
		if ("".equals(Util.null2String(departmentcode))) {
			return "";
		}
		RecordSet rs = new RecordSet();
		rs.execute("select id from hrmdepartment where departmentcode = '" + departmentcode + "'");
		rs.next();
		return Util.null2String(rs.getString("id"));
	}

	/**
	 * 根据分部编码获取分部ID
	 * 
	 * @param subcompanycode
	 * @return
	 */
	public String getsubcompanycode(String subcompanycode) {
		if ("".equals(Util.null2String(subcompanycode))) {
			return "";
		}
		RecordSet rs = new RecordSet();
		rs.execute("select id from hrmsubcompany where subcompanycode = '" + subcompanycode + "'");
		rs.next();
		return Util.null2String(rs.getString("id"));
	}

	@Override
	public void SynInstantDepartment(DepartmentBean arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void SynInstantHrmResource(UserBean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SynInstantJobtitle(JobTitleBean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SynInstantSubCompany(SubCompanyBean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean SynSendMessage(String arg0, String arg1, String arg2, String arg3, String arg4) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void SynTimingFromOADepartment(DepartmentBean[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SynTimingFromOAHrmResource(UserBean[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SynTimingFromOAJobtitle(JobTitleBean[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SynTimingFromOASubCompany(SubCompanyBean[] arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String SynTimingToOAJobtitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String SynTimingToOASubCompany() {
		// TODO Auto-generated method stub
		return null;
	}
}
