package com.gp.sync;

import java.util.Locale;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.core.CoreEngine;
import com.gp.core.CoreFacade;
import com.gp.core.DictionaryFacade;
import com.gp.core.MasterFacade;
import com.gp.core.SecurityFacade;
import com.gp.dao.info.AuditInfo;
import com.gp.dao.info.SysOptionInfo;
import com.gp.dao.info.TokenInfo;
import com.gp.common.AccessPoint;
import com.gp.common.GPrincipal;
import com.gp.common.JwtPayload;
import com.gp.core.CoreAuditFacade;
import com.gp.core.CoreAuditHandler;
import com.gp.disruptor.EventDispatcher;
import com.gp.exception.BaseException;
import com.gp.exception.CoreException;
import com.gp.info.InfoId;
import com.gp.launcher.CoreLauncher;

/**
 * the core starter of application event engine.
 *
 * @author gary diao 
 * @version 0.1 2015-12-12
 * 
 **/
public class SyncNodeLauncher extends CoreLauncher{
	
	static Logger LOGGER = LoggerFactory.getLogger(SyncNodeLauncher.class);

	@Override
	public void engineOff() {
		LOGGER.debug("ServletContextListener:CoreStarter destroying");
		try {

			CoreEngine.shutdown();
			LOGGER.debug("CoreEngine shutdown");
			
		} catch (BaseException e) {
			LOGGER.debug("fail to shutdown CoreFacade.",e);
		}
	}
	
	@Override
	public void engineOn() {
		LOGGER.debug("ContextInitFinishListener:CoreStarter starting");
		try {
			/** 
			 * register the core event hooker, it handle the audit operation to persist it into database
			 * if you want to change the default process, you can extends it to override.
			 **/
			CoreAuditHandler coreHandler = new CoreAuditHandler();
			EventDispatcher.getInstance().regEventHandler(coreHandler);
			// initialize the engine
			CoreFacadeDelegate coreFacade = new CoreFacadeDelegate();
			CoreEngine.initial(coreFacade);
			LOGGER.debug("CoreEngine initialized");
			CoreEngine.startup();
			LOGGER.debug("CoreEngine startup");
		} catch (BaseException e) {
			LOGGER.debug("fail to startup CoreFacade.",e);
		}
	}
	
	class CoreFacadeDelegate implements CoreFacade{

		@Override
		public InfoId<Long> persistAudit(AuditInfo operaudit) throws CoreException {
			
			return CoreAuditFacade.persistAudit(operaudit);
		}

		@Override
		public String findMessagePattern(Locale locale, String dictKey) {
			
			return DictionaryFacade.findMessagePattern(locale, dictKey);
		}

		@Override
		public String findPropertyName(Locale locale, String dictKey) {
			
			return DictionaryFacade.findPropertyName(locale, dictKey);
		}

		@Override
		public SysOptionInfo findSystemOption(GPrincipal principal, String optionKey) throws CoreException{
			
			return MasterFacade.findSystemOption( principal, optionKey);
		}

		@Override
		public TokenInfo findToken(AccessPoint accesspoint, InfoId<Long> tokenId) throws CoreException {
			
			return SecurityFacade.findToken(accesspoint, tokenId);
		}

		@Override
		public GPrincipal findPrincipal(AccessPoint accesspoint, InfoId<Long> userId, String account, String type)
				throws CoreException {
			
			return SecurityFacade.findPrincipal(accesspoint, userId, account, type);
		}

		@Override
		public String reissueToken(AccessPoint accesspoint, GPrincipal principal, JwtPayload payload)
				throws CoreException {
			
			return SecurityFacade.reissueToken(accesspoint, principal, payload);
		}

		@Override
		public boolean removeToken(AccessPoint accesspoint, GPrincipal principal, InfoId<Long> tokenId)
				throws CoreException {
			
			return SecurityFacade.removeToken(accesspoint, principal, tokenId);
		}

		@Override
		public Boolean authenticate(AccessPoint accesspoint, GPrincipal principal, String password)
				throws CoreException {
			
			return SecurityFacade.authenticate(accesspoint, principal, password);
		}

		@Override
		public String newToken(AccessPoint accesspoint, JwtPayload payload) throws CoreException {
			
			return SecurityFacade.newToken(accesspoint, payload);
		}
		
	}
}
