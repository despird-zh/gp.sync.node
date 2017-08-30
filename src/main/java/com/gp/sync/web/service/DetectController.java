package com.gp.sync.web.service;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gp.web.ActionResult;
import com.gp.web.BaseController;
import com.gp.web.servlet.ServiceTokenFilter;

@RestController
@RequestMapping(ServiceTokenFilter.FILTER_PREFIX)
public class DetectController extends BaseController{
 
    @RequestMapping(value="detect.do", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView detect() {
    		ModelAndView mav = super.getJsonModelView();
    		
    		ActionResult result = ActionResult.success("this is internal network");

        return mav.addAllObjects(result.asMap());
    }
}