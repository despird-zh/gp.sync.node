package com.gp.sync.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gp.sync.message.SyncPushMessage;
import com.gp.web.BaseController;
import com.gp.web.servlet.ServiceTokenFilter;

/**
 * After the sync node receive the sync notice message, it request the data in this controller 
 **/
@Controller
@RequestMapping(ServiceTokenFilter.FILTER_PREFIX)
public class SyncPushAPIController extends BaseController{

	static Logger LOGGER = LoggerFactory.getLogger(SyncPushAPIController.class);
	
	@RequestMapping(
		    value = "sync-push", 
		    method = RequestMethod.POST,
		    consumes = {"text/plain", "application/*"})
	public ModelAndView doSyncPush(@RequestBody(required = false) String payload) throws Exception {
		
		ModelAndView rtv= super.getJsonModelView();
		
		SyncPushMessage pushMessage = super.readRequestBody(payload, SyncPushMessage.class);
		
		LOGGER.debug("request push message: {}" , pushMessage);
		
		return rtv;
	}

}
