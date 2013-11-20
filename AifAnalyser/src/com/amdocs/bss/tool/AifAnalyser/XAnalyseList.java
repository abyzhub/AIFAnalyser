package com.amdocs.bss.tool.AifAnalyser;

import java.util.ArrayList;

import amdocs.core.CoreSession;

import com.amdocs.aif.consumer.CustomAdapter;
import com.amdocs.aif.consumer.OperationInputs;
import com.amdocs.aif.consumer.OperationResults;
import com.clarify.webframework.DisplayableException;

public class XAnalyseList extends CustomAdapter {

	private AifAnalyserUtil aifAnalyser = new AifAnalyserUtil(); 
	ArrayList<OperationDetails> operationList = new ArrayList<OperationDetails>();
	private OperationDetails[] operationArray;

	@Override
	public void execute(CoreSession paramCoreSession,
			OperationInputs paramOperationInputs,
			OperationResults paramOperationResults) throws Exception {

		 aifAnalyser.setUser(((AifAnalyserAnalyseListInputs)paramOperationInputs).getUser());
		 aifAnalyser.setPasswd(((AifAnalyserAnalyseListInputs)paramOperationInputs).getPassword());
		 aifAnalyser.setUrl(((AifAnalyserAnalyseListInputs)paramOperationInputs).getUrl());
		 
		 
		 
		try {
			String[] operations = aifAnalyser.readAllOperations();

			OperationDetails od;
			for (String operation : operations) {
				od = new OperationDetails();
				od.setTitle(operation);
				od.setValue(operation);
				operationList.add(od);
			}
			operationArray = (OperationDetails[]) operationList
					.toArray(new OperationDetails[operationList.size()]);

		} catch (Exception e) {
			throw new DisplayableException(e.getLocalizedMessage());
		}
		((AifAnalyserAnalyseListResults)paramOperationResults).setOperationArray(operationArray);
	}

}
