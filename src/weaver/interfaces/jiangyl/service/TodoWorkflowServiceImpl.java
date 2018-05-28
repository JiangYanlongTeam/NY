package weaver.interfaces.jiangyl.service;


import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

public class TodoWorkflowServiceImpl extends BaseBean implements TodoWorkflowService {

	@Override
	public String todoWorkflowCount(String loginname) {
		String loginid = Util.null2String(loginname);
		String info="";
		if ("".equals(loginid)) {
			info="登录账号不能为空";
			return info;
		}
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		
		if ("".equals(id)) {
			info="登录账号" + loginname + "在泛微OA中不存在";
			return info;
			
		}
		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		int count = WorkflowServicePortTypeProxy.getToDoWorkflowRequestCount(Integer.parseInt(id), null);
		return Util.null2String(count);
	}
}