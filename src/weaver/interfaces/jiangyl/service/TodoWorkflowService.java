package weaver.interfaces.jiangyl.service;

public interface TodoWorkflowService {

	/**
	 * 根据登录账号获取待办数量
	 * 
	 * @param loginname
	 * @return
	 */
	public String todoWorkflowCount(String loginname);
}
