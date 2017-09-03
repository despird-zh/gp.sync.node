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
import com.gp.sync.dao.CenterSourceDAO;
import com.gp.sync.dao.info.CenterSourceInfo;

@Component
public class CenterSourceDAOImpl extends DAOSupport implements CenterSourceDAO{

	Logger LOGGER = LoggerFactory.getLogger(CenterSourceDAOImpl.class);
	
	@Autowired
	public CenterSourceDAOImpl(@Qualifier(ServiceConfigurer.DATA_SRC) DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int create(CenterSourceInfo info) {
		StringBuffer SQL = new StringBuffer();
		SQL.append("insert into gp_center_sources (")
			.append("node_id, entity_code, node_code, ")
			.append("last_rcv_time, last_rcv_owm, last_snd_time, last_snd_owm, ")
			.append("modifier, last_modified")
			.append(")values(")
			.append("?,?,?,")
			.append("?,?,?,?,")
			.append("?,?)");
		
		InfoId<Long> key = info.getInfoId();
		
		Object[] params = new Object[]{
				key.getId(),info.getEntityCode(), info.getNodeCode(),
				info.getLastReceiveTime(), info.getLastReceiveOwm(), info.getLastSendTime(), info.getLastSendOwm(),
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
		SQL.append("delete from gp_center_sources ")
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
	public int update(CenterSourceInfo info, FilterMode mode, FlatColLocator... filterCols) {
		Set<String> colset = FlatColumns.toColumnSet(filterCols);
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer SQL = new StringBuffer();
		SQL.append("update gp_center_sources set ");
		
		if(columnCheck(mode, colset, "node_code")){
			SQL.append("node_code = ?,");
			params.add(info.getNodeCode());
		}
		if(columnCheck(mode, colset, "entity_code")){
			SQL.append("entity_code = ? ,");
			params.add(info.getEntityCode());
		}
		if(columnCheck(mode, colset, "last_rcv_time")){
			SQL.append("last_rcv_time = ? , ");
			params.add(info.getLastReceiveTime());
		}
		if(columnCheck(mode, colset, "last_rcv_owm")){
			SQL.append("last_rcv_owm = ?, ");
			params.add(info.getLastReceiveOwm());
		}
		if(columnCheck(mode, colset, "last_snd_time")){
		SQL.append("last_snd_time = ?,");
		params.add(info.getLastSendTime());
		}
		if(columnCheck(mode, colset, "last_snd_owm")){
			SQL.append("last_snd_owm = ?, ");
			params.add(info.getLastSendOwm());
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
	public CenterSourceInfo query(InfoId<?> id) {
		String SQL = "select * from gp_center_sources "
				+ "where node_id = ? ";
		
		Object[] params = new Object[]{				
				id.getId()
			};
		
		JdbcTemplate jtemplate = this.getJdbcTemplate(JdbcTemplate.class);
		if(LOGGER.isDebugEnabled()){			
			LOGGER.debug("SQL : " + SQL.toString() + " / params : " + ArrayUtils.toString(params));
		}
		List<CenterSourceInfo> ainfo = jtemplate.query(SQL, params, Mapper);
		
		return ainfo.size() > 0 ? ainfo.get(0) : null;
	}

	@Override
	protected void initialJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
