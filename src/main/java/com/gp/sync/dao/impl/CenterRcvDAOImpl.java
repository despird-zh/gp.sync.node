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
import com.gp.sync.dao.CenterRcvDAO;
import com.gp.sync.dao.info.CenterRcvInfo;

@Component
public class CenterRcvDAOImpl extends DAOSupport implements CenterRcvDAO{

	Logger LOGGER = LoggerFactory.getLogger(CenterRcvDAOImpl.class);
	
	@Autowired
	public CenterRcvDAOImpl(@Qualifier(ServiceConfigurer.DATA_SRC) DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int create(CenterRcvInfo info) {
		StringBuffer SQL = new StringBuffer();
		SQL.append("insert into gp_center_rcv (")
			.append("rcv_id, entity_code, node_code, rcv_time, ")
			.append("state, rcv_data, start_owm, end_owm, ")
			.append("modifier, last_modified")
			.append(")values(")
			.append("?,?,?,?,")
			.append("?,?,?,?,")
			.append("?,?)");
		
		InfoId<Long> key = info.getInfoId();
		
		Object[] params = new Object[]{
				key.getId(),info.getEntityCode(), info.getNodeCode(), info.getReceiveTime(),
				info.getState(), info.getReceiveData(), info.getStartOwm(), info.getEndOwm(),
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
		SQL.append("delete from gp_center_rcv ")
			.append("where rcv_id = ? ");
		
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
	public int update(CenterRcvInfo info, FilterMode mode, FlatColLocator... filterCols) {
		Set<String> colset = FlatColumns.toColumnSet(filterCols);
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer SQL = new StringBuffer();
		SQL.append("update gp_center_rcv set ");
		
		if(columnCheck(mode, colset, "entity_code")){
			SQL.append("entity_code = ? , ");
			params.add(info.getEntityCode());
		}
		if(columnCheck(mode, colset, "node_code")){
			SQL.append("node_code = ?, ");
			params.add(info.getNodeCode());
		}
		if(columnCheck(mode, colset, "rcv_time")){
			SQL.append("rcv_time = ?,");
			params.add(info.getReceiveTime());
		}
		if(columnCheck(mode, colset, "state")){
			SQL.append("state = ?, ");
			params.add(info.getState());
		}
		if(columnCheck(mode, colset, "rcv_data")){
			SQL.append("rcv_data = ?, ");
			params.add(info.getReceiveData());
		}
		if(columnCheck(mode, colset, "start_owm")){
			SQL.append("start_owm = ?, ");
			params.add(info.getStartOwm());
		}
		if(columnCheck(mode, colset, "end_owm")){
			SQL.append("end_owm = ?, ");
			params.add(info.getEndOwm());
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
	public CenterRcvInfo query(InfoId<?> id) {
		String SQL = "select * from gp_center_rcv "
				+ "where rcv_id = ? ";
		
		Object[] params = new Object[]{				
				id.getId()
			};
		
		JdbcTemplate jtemplate = this.getJdbcTemplate(JdbcTemplate.class);
		if(LOGGER.isDebugEnabled()){			
			LOGGER.debug("SQL : " + SQL.toString() + " / params : " + ArrayUtils.toString(params));
		}
		List<CenterRcvInfo> ainfo = jtemplate.query(SQL, params, MAPPER);
		
		return ainfo.size() > 0 ? ainfo.get(0) : null;
	}

	@Override
	protected void initialJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
