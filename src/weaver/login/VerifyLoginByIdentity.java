package weaver.login;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class VerifyLoginByIdentity {

	public int verifyLoginByIdentity(String paramString1, String paramString2){
		try{
	      boolean bool = validate(paramString1, paramString2);
	      if (bool) {
	        return 1;
	      }
	      return 2;
	    }
	    catch (Exception localException1) {
	      localException1.printStackTrace();
	    }
	    return 5;
	}
	
	/**
	 * 验证ladp是否存在
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean validate(String username,String password){
	    String PASSWORD = "njupt@2016";
    	String PRINCIPAL = "uid=conn_oa,ou=Manager,dc=njupt,dc=edu,dc=cn";
    	String URL = "ldap://202.119.226.200:389/";
    	String BASEDN = "dc=njupt,dc=edu,dc=cn";
    	Control[] connCtls = null;
    	LdapContext ctx = null;
        boolean valide = false;
        if(password == null || password == "")
            return false;
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, URL + BASEDN);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, PRINCIPAL);
        env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
        try {
            // 链接ldap
            ctx = new InitialLdapContext(env, connCtls);
        } catch(javax.naming.AuthenticationException e){
            new weaver.general.BaseBean().writeLog("Authentication faild: "+e.toString());
        } catch(Exception e){
            new weaver.general.BaseBean().writeLog("Something wrong while authenticating: "+e.toString());
        }
        
        String userDN = "";
        try{
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration en = ctx.search("","uid="+username, constraints);
            if(en == null){
                new weaver.general.BaseBean().writeLog("Have no NamingEnumeration.");
            }
            if(!en.hasMoreElements()){
                new weaver.general.BaseBean().writeLog("Have no element.");
            }
            while (en != null && en.hasMoreElements()){
                Object obj = en.nextElement();
                if(obj instanceof SearchResult){
                    SearchResult si = (SearchResult) obj;
                    userDN += si.getName();
                    userDN += "," + BASEDN;
                }
                else{
                    new weaver.general.BaseBean().writeLog(obj);
                }
                new weaver.general.BaseBean().writeLog("");
            }
        }catch(Exception e){
            new weaver.general.BaseBean().writeLog("Exception in search():"+e);
        }
        try {
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            ctx.reconnect(connCtls);
            valide = true;
        } catch (AuthenticationException e) {
            new weaver.general.BaseBean().writeLog(userDN + " is not authenticated");
            new weaver.general.BaseBean().writeLog(e.toString());
            valide = false;
        }catch (NamingException e) {
            new weaver.general.BaseBean().writeLog(userDN + " is not authenticated");
            valide = false;
        }
        return valide;
    }
}
