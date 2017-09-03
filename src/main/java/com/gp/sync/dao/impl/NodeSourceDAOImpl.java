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

import com.gp.common.FlatColumns;
import com.gp.common.FlatColumns.FilterMode;
import com.gp.config.ServiceConfigurer;
import com.gp.dao.impl.DAOSupport;
import com.gp.info.FlatColLocator;
import com.gp.info.InfoId;
import com.gp.sync.dao.NodeSourceDAO;
import com.gp.sync.dao.info.NodeSourceInfo;

public class NodeSourceDAOImpl extends DAOSupport implements NodeSourceDAO{

	Logger LOGGER = LoggerFactory.getLogger(NodeSourceDAOImpl.class);
	
	@Autowired
	public NodeSourceDAOImpl(@Qualifier(ServiceConfigurer.DATA_SRC) DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	
	@Override
	protected void initialJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int create(NodeSourceInfo info) {
		StringBuffer SQL = new StringBuffer();
		SQL.append("insert into gp_node_sources (")
			.append("node_id, entity_code, node_code, last_push_time,")
			.append("last_push_owm, last_pull_time, last_pull_owm, ")
			.append("modifier, last_modified")
			.append(")values(")
			.append("?,?,?,?,")
			.append("?,?,?,")
			.append("?,?)");
		
		InfoId<Long> key = info.getInfoId();
		
		Object[] params = new Object[]{
				key.getId(), info.getEntityCode(), info.getNodeCode(), info.getLastPushTime(),
				info.getLastPushOwm(), info.getLastPullTime(), info.getLastPullOwm(),
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
		SQL.append("delete from gp_node_sources ")
			.append("where node_id = ? ");
		
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
	public int update(NodeSourceInfo info, FilterMode mode, FlatColLocator... filterCols) {
		Set<String> colset = FlatColumns.toColumnSet(filterCols);
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer SQL = new StringBuffer();
		SQL.append("update gp_node_sources set ");
		
		if(columnCheck(mode, colset, "node_code")){
			SQL.append("node_code = ?,");
			params.add(info.getNodeCode());
		}
		if(columnCheck(mode, colset, "entity_code")){
			SQL.append("entity_code = ? ,");
			params.add(info.getEntityCode());
		}
		if(columnCheck(mode, colset, "last_push_time")){
			SQL.append("last_push_time = ? , ");
			params.add(info.getLastPushTime());
		}
		if(columnCheck(mode, colset, "last_push_owm")){
			SQL.append("last_push_owm = ? , ");
			params.add(info.getLastPushOwm());
		}
		if(columnCheck(mode, colset, "last_pull_time")){
			SQL.append("last_pull_time = ? , ");
			params.add(info.getLastPullTime());
		}
		if(columnCheck(mode, colset, "last_pull_owm")){
			SQL.append("last_pull_owm = ? , ");
			params.add(info.getLastPullOwm());
		}

		SQL.append("modifier = ?, last_modified = ? ")
			.append("where node_id = ? ");
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
	public NodeSourceInfo query(InfoId<?> id) {
		String SQL = "select * from gp_node_sources "
				+ "where node_id = ? ";
		
		Object[] params = new Object[]{				
				id.getId()
			};
		
		JdbcTemplate jtemplate = this.getJdbcTemplate(JdbcTemplate.class);
		if(LOGGER.isDebugEnabled()){			
			LOGGER.debug("SQL : " + SQL.toString() + " / params : " + ArrayUtils.toString(params));
		}
		List<NodeSourceInfo> ainfo = jtemplate.query(SQL, params, MAPPER);
		
		return ainfo.size() > 0 ? ainfo.get(0) : null;
	}

}
