package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.CenterRcvInfo;

public interface CenterRcvDAO extends BaseDAO<CenterRcvInfo>{

	public static RowMapper<CenterRcvInfo> MAPPER = new RowMapper<CenterRcvInfo>() {

		@Override
		public CenterRcvInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			
			CenterRcvInfo info = new CenterRcvInfo();
			
			InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.CENTER_RCV, rs.getLong("rcv_id"));
			info.setInfoId(id);
			info.setEntityCode(rs.getString("entity_code"));
			info.setNodeCode(rs.getString("node_code"));
			info.setReceiveTime(rs.getTimestamp("rcv_time"));
			info.setState(rs.getString("state"));
			info.setReceiveData(rs.getString("rcv_data"));
			info.setStartOwm(rs.getLong("start_owm"));
			info.setEndOwm(rs.getLong("end_owm"));
			
			info.setModifier(rs.getString("modifier"));
			info.setModifyDate(rs.getTimestamp("last_modified"));
			
			return info;
		}};
}
