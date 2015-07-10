package cn.bblink.bbdoctor.domain.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class AbstractBean{
	protected transient final Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String toJson(){
		return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 转换为一个Map
	 */
	public Map toMap(){
		try {
			return BeanUtils.describe(this);
		}  catch (Exception e) {
			return new HashMap();
		}
	}
	
}