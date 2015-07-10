package cn.bblink.bbdoctor.dao.interceptor;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import cn.bblink.bbdoctor.util.ReflectUtil;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagingSQLInterceptor implements Interceptor {
	public Object intercept(Invocation invocation) throws Throwable {
		 if (invocation.getTarget() instanceof RoutingStatementHandler){
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtil.getValueByFieldName(statementHandler, "delegate");  
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getValueByFieldName(delegate, "mappedStatement");  
            String statmentName = mappedStatement.getId();
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
			BoundSql boundSql = statementHandler.getBoundSql();
			Object parameterObject = boundSql.getParameterObject();
			String sql = boundSql.getSql();
			if(SqlCommandType.SELECT.equals(sqlCommandType)){
				if (parameterObject instanceof Map) {
					Map parameterMap = (Map)parameterObject;
					Integer index = MapUtils.getInteger(parameterMap, "index");
					Integer page = MapUtils.getInteger(parameterMap, "page");
					Integer pageSize = MapUtils.getInteger(parameterMap, "pageSize");
					//rest api分页
					if (index != null) {
		    			 int start = (page -1) * pageSize; 
		    			 sql = sql +" LIMIT "+ start + "," + pageSize;
		    			 ReflectUtil.setValueByFieldName(boundSql, "sql", sql);
					}
					
					//后页管理freemark列表页面分页
					if (page != null && !StringUtils.endsWith(statmentName, "Count")) {
						if (page > 0) {
							page--;
						}
						int start = page * pageSize;
						sql = sql + " LIMIT " + start + "," + parameterMap.get("pageSize");
						ReflectUtil.setValueByFieldName(boundSql, "sql", sql);
					}
				}
			}
			
		 }
		return invocation.proceed();
	}
	
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
	
	public void setProperties(Properties properties) {
		// Auto-generated method stub
	}
}