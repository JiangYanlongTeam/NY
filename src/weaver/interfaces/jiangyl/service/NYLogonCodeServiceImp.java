package weaver.interfaces.jiangyl.service;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.HashMap;
import java.util.Map;

public class NYLogonCodeServiceImp extends BaseBean implements NYLogonCodeService {

    public static Map<String,String> hrmresourceMap = new HashMap<String,String>();

    @Override
    public String logonCode(String code) {
        if (Util.null2String(code).equals("")) {
            return "{\"success\":false,\"code\":\"\",\"message\":\"工号不能为空\"}";
        }
        RecordSet recordSet = new RecordSet();
        recordSet.execute("select * from hrmresource where loginid = '" + code + "'");
        String hrmresourceID = "";
        if (recordSet.next()) {
            if (!hrmresourceMap.containsKey(code)) {
                String md5Code = new NYLogonVerity().encode(code);
                hrmresourceMap.put(code, md5Code);
                return "{\"success\":true,\"code\":\"" + md5Code + "\",\"message\":\"获取成功\"}";
            } else {
                String md5Code = hrmresourceMap.get(code);
                return "{\"success\":true,\"code\":\"" + md5Code + "\",\"message\":\"获取成功\"}";
            }
        }
        return "{\"success\":false,\"code\":\"\",\"message\":\"工号在泛微OA中不存在\"}";
    }
}
