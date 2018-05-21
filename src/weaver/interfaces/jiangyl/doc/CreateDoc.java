package weaver.interfaces.jiangyl.doc;

import java.rmi.RemoteException;

import org.apache.axis.encoding.Base64;

import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocService;
import weaver.docs.webservices.DocServiceImpl;

public class CreateDoc {

	public int createDocFile(String Subject, String fileName, int dirid, String html) {
		int docid = 0;
		DocService service = new DocServiceImpl();
		String session;
		try {
			session = service.login("sysadmin", "njupt@oaapp", 0, "127.0.0.1");
			DocInfo doc = service.getDoc(dirid, session);// 44711
			DocAttachment da = doc.getAttachments()[0];

			da.setDocid(0);
			da.setImagefileid(0);
			da.setFilename(fileName);
			da.setFilecontent(Base64.encode(html.getBytes()));
			da.setIszip(1);

			doc.setId(0);
			doc.setDocSubject(Subject);
			doc.setAttachments(new DocAttachment[] { da });
			docid = service.createDoc(doc, session);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docid;
	}
}
