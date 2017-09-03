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
import com.gp.sync.dao.CenterDistDAO;
import com.gp.sync.dao.info.CenterDistInfo;

@Component
public class CenterDistDAOImpl extends DAOSupport implements CenterDistDAO{

	Logger LOGGER = LoggerFactory.getLogger(CenterDistDAOImpl.class);
	
	@Autowired
	public CenterDistDAOImpl(@Qualifier(ServiceConfigurer.DATA_SRC)DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int create(CenterDistInfo info) {
		StringBuffer SQL = new StringBuffer();
		SQL.append("insert into gp_center_dist (")
			.append("dist_id, msg_id, snd_id,")
			.append("tgt_entity_code, tgt_node_code, dist_time, sync_done,")
			.append("modifier, last_modified")
			.append(")values(")
			.append("?,?,?,")
			.append("?,?,?,?,")
			.append("?,?)");
		
		InfoId<Long> key = info.getInfoId();
		
		Object[] params = new Object[]{
				key.getId(),info.getMsgId(), info.getSendId(),
				info.getTargetEntityCode(), info.getTargetNodeCode(), info.getDistributeTime(), info.getSyncDone(),
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
		SQL.append("delete from gp_center_dist ")
			.append("where dist_id = ? ");
		
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
	public int update(CenterDistInfo info, FilterMode mode, FlatColLocator... filterCols) {
		Set<String> colset = FlatColumns.toColumnSet(filterCols);
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer SQL = new StringBuffer();
		SQL.append("update gp_center_dist set ");
		
		if(columnCheck(mode, colset, "msg_id")){
			SQL.append("msg_id = ?,");
			params.add(info.getMsgId());
		}
		if(columnCheck(mode, colset, "snd_id")){
			SQL.append("snd_id = ? ,");
			params.add(info.getSendId());
		}
		if(columnCheck(mode, colset, "tgt_entity_code")){
			SQL.append("tgt_entity_code = ? , ");
			params.add(info.getTargetEntityCode());
		}
		if(columnCheck(mode, colset, "tgt_node_code")){
			SQL.append("tgt_node_code = ?, ");
			params.add(info.getTargetNodeCode());
		}
		if(columnCheck(mode, colset, "dist_time")){
		SQL.append("dist_time = ?,");
		params.add(info.getDistributeTime());
		}
		if(columnCheck(mode, colset, "sync_done")){
			SQL.append("sync_done = ?, ");
			params.add(info.getSyncDone());
		}
		
		SQL.append("modifier = ?, last_modified = ? ")
			.append("where dist_id = ? ");
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
	public CenterDistInfo query(InfoId<?> id) {
		String SQL = "select * from gp_center_dist "
				+ "where dist_id = ? ";
		
		Object[] params = new Object[]{				
				id.getId()
			};
		
		JdbcTemplate jtemplate = this.getJdbcTemplate(JdbcTemplate.class);
		if(LOGGER.isDebugEnabled()){			
			LOGGER.debug("SQL : " + SQL.toString() + " / params : " + ArrayUtils.toString(params));
		}
		List<CenterDistInfo> ainfo = jtemplate.query(SQL, params, DistMapper);
		
		return ainfo.size() > 0 ? ainfo.get(0) : null;
	}

	@Override
	protected void initialJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
