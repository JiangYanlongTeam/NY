package weaver.interfaces.jiangyl.service;

import weaver.general.BaseBean;
import weaver.general.MD5;
import weaver.general.Util;

import java.util.Date;

public class NYLogonVerity extends BaseBean {
    /**
     * 验证传入编码是否是当前用户所在的编码
     *
     * @param loginid
     * @param code
     * @return
     */
    public boolean verityCode(String loginid,String code) {
        if(Util.null2String(code).equals("")) {
            writeLog("验证登录code传入参数code为空");
            return false;
        }
        if (NYLogonCodeServiceImp.hrmresourceMap.containsKey(loginid)) {
            if(NYLogonCodeServiceImp.hrmresourceMap.get(loginid).equals(code)) {
                String md5Code = encode(loginid);
                NYLogonCodeServiceImp.hrmresourceMap.put(loginid,md5Code);
                writeLog("原有账号："+loginid+" code为"+code+",此次新生成code为："+md5Code);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 根据传入参数code和当前日期加密获取字符串
     *
     * @param code
     * @return
     */
    public String encode(String code) {
        MD5 md5 = new MD5();
        return Util.null2String(md5.getMD5ofStr(code+new Date().getTime()));
    }
}
