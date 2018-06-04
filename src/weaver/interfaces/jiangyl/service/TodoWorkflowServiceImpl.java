package weaver.interfaces.jiangyl.service;


import com.alibaba.fastjson.JSON;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.MD5;
import weaver.general.Util;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

public class TodoWorkflowServiceImpl extends BaseBean implements TodoWorkflowService {

	@Override
	public String todoWorkflowCount(String loginname, String code) {
		Response response = new Response();
		String loginid = Util.null2String(loginname);
		if ("".equals(loginid)) {
			response.setMessage("登录账号不能为空");
			response.setSuccess("false");
			response.setNum("0");
			response.setUrl("");
			return JSON.toJSON(response).toString();
		}
		String key = getPropValue("zf_key","key");
		String url = getPropValue("zf_key","url");
		String md5Code = new MD5().getMD5ofStr(loginname+key);
		writeLog("通过"+loginname+key+"生成MD5："+md5Code);
		if(!md5Code.equals(code)) {

		}
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = '" + loginname + "'";
		rs.execute(sql);
		rs.next();
		String id = Util.null2String(rs.getString("id"));
		
		if ("".equals(id)) {
			response.setMessage("登录账号"+loginname+"在泛微oa中不存在");
			response.setSuccess("false");
			response.setNum("0");
			response.setUrl("");
			return JSON.toJSON(response).toString();
			
		}
		WorkflowService WorkflowServicePortTypeProxy = new WorkflowServiceImpl();
		int count = WorkflowServicePortTypeProxy.getToDoWorkflowRequestCount(Integer.parseInt(id), null);
		String todoCount = Util.null2String(count);
		response.setMessage("获取成功");
		response.setSuccess("true");
		response.setNum(todoCount);
		response.setUrl(url+"/interface/portal/portal.jsp?loginid="+loginname+"");
		return Util.null2String(count);
	}
}