package com.amdocs.bss.tool.aifAnalyser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;

import com.amdocs.aif.datamodel.AdapterBinding;
import com.amdocs.aif.datamodel.Binding;
import com.amdocs.aif.datamodel.ObjectState;
import com.amdocs.aif.datamodel.Operation;
import com.amdocs.aif.repository.OperationStore;
import com.clarify.cbo.BoContext;
import com.clarify.cbo.XBeanImpl;
import com.clarify.webframework.DisplayableException;


public class XAIfAnalyzeOperationXB extends XBeanImpl {

	
	
	private String dbDriver = "oracle.jdbc.driver.OracleDriver";
	private String user = "sa";
	private String passwd = "sa";
	private String operationName;
	private Operation operation;
	public Operation getOperation() {
		return operation;
	}

	private String mUrl;


	
	
	@Override
	protected void executeImpl(BoContext bocontext) {
		// TODO Auto-generated method stub

	}
	private Object createObjectByName(Class<?> classDefinition,
			Class<?>[] parameterTypes, Object[] constructorArguments)
			throws Exception {
		// Class classDefinition = Class.forName(className);

		Constructor<?> constructor = classDefinition
				.getDeclaredConstructor(parameterTypes);

		constructor.setAccessible(true);

		return constructor.newInstance(constructorArguments);
	}
	
	@Override
	protected void preExecute(BoContext bocontext) {
		OperationStore opStore = OperationStore.get();

		Class<?>[] innerClasses = OperationStore.class.getDeclaredClasses();
		Class<?> connFactoryCls = null;
		for (Class<?> innerClass : innerClasses) {
			if (innerClass.getName().equals(
					"com.amdocs.aif.repository.OperationStore$JdbcConFactory"))
				connFactoryCls = innerClass;
		}
		try{

			Class.forName(dbDriver);
			Connection jdbcCon = DriverManager.getConnection(mUrl, user, passwd);
			Object connFactory = createObjectByName(connFactoryCls,
					new Class[] { Connection.class, String.class, String.class,
							String.class, String.class }, new Object[] {
							jdbcCon, dbDriver, mUrl, user, passwd });

			Field m_conFactory = OperationStore.class
					.getDeclaredField("m_conFactory");
			m_conFactory.setAccessible(true);
			m_conFactory.set(opStore, connFactory);

			operation =  opStore.queryOperationById(operationName);
			
			Binding[] bindings= operation.getBindings();
			for(Binding binding:bindings){
				if (binding instanceof AdapterBinding){
					AdapterBinding adpBinding = (AdapterBinding)binding;
					
					Field faultMapping = AdapterBinding.class.getDeclaredField("m_FaultMapping");
					faultMapping.setAccessible(true);
					faultMapping.set(adpBinding, null);
				}
			}
			
		}
		catch(Exception e)
		{
			throw new DisplayableException (e.getLocalizedMessage());
		}

	}
	
	
	
	
	

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	public String getmUrl() {
		return mUrl;
	}

	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	
	
}
