package com.gp.sync.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.gp.common.FlatColumns;
import com.gp.common.FlatColumns.FilterMode;
import com.gp.config.ServiceConfigurer;
import com.gp.dao.impl.DAOSupport;
import com.gp.info.FlatColLocator;
import com.gp.info.InfoId;
import com.gp.sync.dao.NodeMsgOutDAO;
import com.gp.sync.dao.info.NodeMsgOutInfo;

@Component
public class NodeMsgOutDAOImpl extends DAOSupport implements NodeMsgOutDAO{

	Logger LOGGER = LoggerFactory.getLogger(NodeMsgOutDAOImpl.class);
	
	@Autowired
	public NodeMsgOutDAOImpl(@Qualifier(ServiceConfigurer.DATA_SRC) DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int create(NodeMsgOutInfo info) {
		StringBuffer SQL = new StringBuffer();
		SQL.append("insert into gp_node_msg_out (")
			.append("msg_id, push_id, entity_code, node_code, ")
			.append("trace_code, owm, sync_cmd, msg_data, ")
			.append("modifier, last_modified")
			.append(")values(")
			.append("?,?,?,")
			.append("?,?,?,?,")
			.append("?,?)");
		
		InfoId<Long> key = info.getInfoId();
		
		Object[] params = new Object[]{
				key.getId(),info.getPushId(), info.getEntityCode(), info.getNodeCode(),
				info.getTraceCode(), info.getOwm(), info.getSyncCommand(), info.getMsgData(),
				info.getModifier(),info.getModifyDate(),
		};
		if(LOGGER.isDebugEnabled()){
			
			LOGGER.debug("SQL : " + SQL.toString() + " / params : " + ArrayUtils.toString(params));
		}
		JdbcTemplate jtemplate = this.getJdbcTemplate(JdbcTemplate.class);
		int cnt = jtemplate.update(SQL.toString(),params);
		return cnt;
	}

	@Override
	public int delete(InfoId<?> id) {
		StringBuffer SQL = new StringBuffer();
		SQL.append("delete from gp_node_msg_out ")
			.append("where msg_id = ? ");
		
		JdbcTemplate jtemplate = this.getJdbcTemplate(JdbcTemplate.class);
		Object[] params = new Object[]{
			id.getId()
		};
		if(LOGGER.isDebugEnabled()){			
			LOGGER.debug("SQL : " + SQL + " / params : " + ArrayUtils.toString(params));
		}
		int rtv = jtemplate.update(SQL.toString(), params);
		return rtv;
	}

	@Override
	public int update(NodeMsgOutInfo info, FilterMode mode, FlatColLocator... filterCols) {
		Set<String> colset = FlatColumns.toColumnSet(filterCols);
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer SQL = new StringBuffer();
		SQL.append("update gp_node_msg_out set ");
		
		if(columnCheck(mode, colset, "node_code")){
			SQL.append("node_code = ?,");
			params.add(info.getNodeCode());
		}
		if(columnCheck(mode, colset, "entity_code")){
			SQL.append("entity_code = ? ,");
			params.add(info.getEntityCode());
		}
		if(columnCheck(mode, colset, "push_id")){
			SQL.append("push_id = ? , ");
			params.add(info.getPushId());
		}
		if(columnCheck(mode, colset, "trace_code")){
			SQL.append("trace_code = ?, ");
			params.add(info.getTraceCode());
		}
		if(columnCheck(mode, colset, "owm")){
		SQL.append("owm = ?,");
		params.add(info.getOwm());
		}
		if(columnCheck(mode, colset, "sync_cmd")){
			SQL.append("sync_cmd = ?, ");
			params.add(info.getSyncCommand());
		}
		if(columnCheck(mode, colset, "msg_data")){
			SQL.append("msg_data = ?, ");
			params.add(info.getMsgData());
		}
		
		SQL.append("modifier = ?, last_modified = ? ")
			.append("where msg_id = ? ");
		params.add(info.getModifier());
		params.add(info.getModifyDate());
		params.add(info.getInfoId().getId());
		
		JdbcTemplate jtemplate = this.getJdbcTemplate(JdbcTemplate.class);
		if(LOGGER.isDebugEnabled()){			
			LOGGER.debug("SQL : " + SQL + " / params : " + ArrayUtils.toString(params));
		}
		int rtv = jtemplate.update(SQL.toString(),params.toArray());
		return rtv;
	}

	@Override
	public NodeMsgOutInfo query(InfoId<?> id) {
		String SQL = "select * from gp_node_msg_out "
				+ "where msg_id = ? ";
		
		Object[] params = new Object[]{				
				id.getId()
			};
		
		JdbcTemplate jtemplate = this.getJdbcTemplate(JdbcTemplate.class);
		if(LOGGER.isDebugEnabled()){			
			LOGGER.debug("SQL : " + SQL.toString() + " / params : " + ArrayUtils.toString(params));
		}
		List<NodeMsgOutInfo> ainfo = jtemplate.query(SQL, params, MAPPER);
		
		return ainfo.size() > 0 ? ainfo.get(0) : null;
	}

	@Override
	protected void initialJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
