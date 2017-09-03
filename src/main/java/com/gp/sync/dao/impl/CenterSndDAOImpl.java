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
import com.gp.sync.dao.CenterSndDAO;
import com.gp.sync.dao.info.CenterSndInfo;

@Component
public class CenterSndDAOImpl extends DAOSupport implements CenterSndDAO{

	Logger LOGGER = LoggerFactory.getLogger(CenterRcvDAOImpl.class);
	
	@Autowired
	public CenterSndDAOImpl(@Qualifier(ServiceConfigurer.DATA_SRC) DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int create(CenterSndInfo info) {
		
		StringBuffer SQL = new StringBuffer();
		SQL.append("insert into gp_center_snd (")
			.append("snd_id, entity_code, node_code, snd_time, ")
			.append("bloom_data, snd_data, state, ")
			.append("modifier, last_modified")
			.append(")values(")
			.append("?,?,?,?,")
			.append("?,?,?,")
			.append("?,?)");
		
		InfoId<Long> key = info.getInfoId();
		
		Object[] params = new Object[]{
				key.getId(),info.getEntityCode(), info.getNodeCode(), info.getSendTime(),
				info.getBloomData(), info.getSendData(), info.getState(),
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
		SQL.append("delete from gp_center_snd ")
			.append("where snd_id = ? ");
		
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
	public int update(CenterSndInfo info, FilterMode mode, FlatColLocator... filterCols) {
		Set<String> colset = FlatColumns.toColumnSet(filterCols);
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer SQL = new StringBuffer();
		SQL.append("update gp_center_snd set ");
		
		if(columnCheck(mode, colset, "entity_code")){
			SQL.append("entity_code = ? , ");
			params.add(info.getEntityCode());
		}
		if(columnCheck(mode, colset, "node_code")){
			SQL.append("node_code = ?, ");
			params.add(info.getNodeCode());
		}
		if(columnCheck(mode, colset, "snd_time")){
			SQL.append("snd_time = ?,");
			params.add(info.getSendTime());
		}
		if(columnCheck(mode, colset, "state")){
			SQL.append("state = ?, ");
			params.add(info.getState());
		}
		if(columnCheck(mode, colset, "snd_data")){
			SQL.append("snd_data = ?, ");
			params.add(info.getSendData());
		}
		if(columnCheck(mode, colset, "bloom_data")){
			SQL.append("bloom_data = ?, ");
			params.add(info.getBloomData());
		}
		
		SQL.append("modifier = ?, last_modified = ? ")
			.append("where rcv_id = ? ");
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
	public CenterSndInfo query(InfoId<?> id) {
		String SQL = "select * from gp_center_snd "
				+ "where snd_id = ? ";
		
		Object[] params = new Object[]{				
				id.getId()
			};
		
		JdbcTemplate jtemplate = this.getJdbcTemplate(JdbcTemplate.class);
		if(LOGGER.isDebugEnabled()){			
			LOGGER.debug("SQL : " + SQL.toString() + " / params : " + ArrayUtils.toString(params));
		}
		List<CenterSndInfo> ainfo = jtemplate.query(SQL, params, MAPPER);
		
		return ainfo.size() > 0 ? ainfo.get(0) : null;
	}

	@Override
	protected void initialJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
