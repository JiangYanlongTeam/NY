package weaver.interfaces.jiangyl.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPHelper {

	private FTPClient ftp;

	/**
	 * 
	 * @param path
	 *            上传到ftp服务器哪个路径下
	 * @param addr
	 *            地址
	 * @param port
	 *            端口号
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	public boolean connect(String path, String addr, int port, String username, String password) throws Exception {
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, port);
		ftp.login(username, password);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}
		ftp.changeWorkingDirectory(path);
		result = true;
		return result;
	}

	/**
	 * 
	 * @param file
	 *            上传的文件或文件夹
	 * @throws Exception
	 */
	public boolean upload(File file, String filename) {
		FileInputStream input = null;
		boolean issuccess = false;
		try {
			input = new FileInputStream(file);
			issuccess = ftp.storeFile(filename, input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return issuccess;
	}

	/**
	 * 断开
	 */
	public void disconnect() {
		try {
			ftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}