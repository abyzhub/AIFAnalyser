package com.amdocs.bss.tool.aifAnalyser;


import java.util.ArrayList;

import com.clarify.cbo.BoContext;
import com.clarify.cbo.XBeanImpl;
import com.clarify.webframework.DisplayableException;

public class XAIfAnalyzeListXB extends XBeanImpl {

	
	private AifAnalyserUtil aifAnalyser = new AifAnalyserUtil();
	private String txtUserName;
	private String txtPassword;
	ArrayList <OperationDetails> operationList = new ArrayList<OperationDetails>();
	private String mUrl;
	private OperationDetails[] operationArray;
	
	

	@Override
	protected void executeImpl(BoContext arg0) {

		try {
			String[] operations = aifAnalyser.readAllOperations();
			
			OperationDetails od ;
			for (String operation : operations) {
				od = new OperationDetails();
				od.setTitle(operation);
				od.setValue(operation);
				operationList.add(od);
			}
			operationArray =(OperationDetails[])operationList.toArray( new OperationDetails[operationList.size()]);
			
			}
			catch(Exception e)
			{
				throw new DisplayableException(e.getLocalizedMessage());
			}


	}

	@Override
	protected void preExecute(BoContext arg0) {
		aifAnalyser.setUser(txtUserName);
		aifAnalyser.setPasswd(txtPassword);
		aifAnalyser.setUrl(mUrl );
	}
	
	public OperationDetails[] getOperationArray() {
		return operationArray;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	
	
	public void setTxtPassword(String txtPassword) {
		this.txtPassword = txtPassword;
	}
	
	public void setTxtUserName(String txtUserName) {
		this.txtUserName = txtUserName;
	}
}
