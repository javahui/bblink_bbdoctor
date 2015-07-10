package cn.bblink.bbdoctor.api.action.core;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bblink.bbdoctor.domain.common.AjaxResult;
import cn.bblink.bbdoctor.domain.core.AbstractPo;

public class AbstractRestAction<T extends AbstractPo> extends AbstractAction {
	
	protected final String namespace = this.getClass().getSimpleName().toLowerCase().replaceAll("action", "");
	
	
	
}