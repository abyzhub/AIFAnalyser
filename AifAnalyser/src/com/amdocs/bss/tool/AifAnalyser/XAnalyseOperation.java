package com.amdocs.bss.tool.AifAnalyser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;

import amdocs.core.CoreSession;

import com.amdocs.aif.consumer.CustomAdapter;
import com.amdocs.aif.consumer.OperationInputs;
import com.amdocs.aif.consumer.OperationResults;
import com.amdocs.aif.datamodel.AdapterBinding;
import com.amdocs.aif.datamodel.Binding;
import com.amdocs.aif.datamodel.Interface;
import com.amdocs.aif.datamodel.Operation;
import com.amdocs.aif.repository.OperationStore;
import com.clarify.webframework.DisplayableException;

public class XAnalyseOperation extends CustomAdapter {

	private String dbDriver = "oracle.jdbc.driver.OracleDriver";
	private String user = "sa";
	private String passwd = "sa";
	private String operationName;
	private Operation operation;

	private String mUrl;

	@Override
	public void execute(CoreSession paramCoreSession,
			OperationInputs paramOperationInputs,
			OperationResults paramOperationResults) throws Exception {
		user = ((AifAnalyserAnalyseOperationInputs) paramOperationInputs)
				.getUser();
		passwd = ((AifAnalyserAnalyseOperationInputs) paramOperationInputs)
				.getPassword();
		operationName = ((AifAnalyserAnalyseOperationInputs) paramOperationInputs)
				.getOperationName();
		mUrl = ((AifAnalyserAnalyseOperationInputs) paramOperationInputs)
		.getUrl();
		
		OperationStore opStore = OperationStore.get();

		Class<?>[] innerClasses = OperationStore.class.getDeclaredClasses();
		Class<?> connFactoryCls = null;
		for (Class<?> innerClass : innerClasses) {
			if (innerClass.getName().equals(
					"com.amdocs.aif.repository.OperationStore$JdbcConFactory"))
				connFactoryCls = innerClass;
		}
		try {

			Class.forName(dbDriver);
			Connection jdbcCon = DriverManager
					.getConnection(mUrl, user, passwd);
			Object connFactory = createObjectByName(connFactoryCls,
					new Class[] { Connection.class, String.class, String.class,
							String.class, String.class }, new Object[] {
							jdbcCon, dbDriver, mUrl, user, passwd });

			Field m_conFactory = OperationStore.class
					.getDeclaredField("m_conFactory");
			m_conFactory.setAccessible(true);
			
			Object connFactoryBackup = m_conFactory.get(opStore);
			m_conFactory.set(opStore, connFactory);

			operation = opStore.queryOperationById(operationName);
			
			m_conFactory.set(opStore, connFactoryBackup);

			Field primaryProvider = Interface.class.getDeclaredField("m_PrimaryProvider");
			primaryProvider.setAccessible(true);
			primaryProvider.set(operation.getInterface(), null);

			Binding[] bindings = operation.getBindings();
			for (Binding binding : bindings) {
				if (binding instanceof AdapterBinding) {
					AdapterBinding adpBinding = (AdapterBinding) binding;

					Field faultMapping = AdapterBinding.class
							.getDeclaredField("m_FaultMapping");
					faultMapping.setAccessible(true);
					faultMapping.set(adpBinding, null);
				}
//					primaryProvider = Interface.class.getDeclaredField("m_PrimaryProvider");
//					primaryProvider.setAccessible(true);
					primaryProvider.set(binding.getService().getInterface(), null);
			}

		} catch (Exception e) {
			throw new DisplayableException(e.getLocalizedMessage());
		}

		((AifAnalyserAnalyseOperationResults)paramOperationResults).setOperation(operation);
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

}
