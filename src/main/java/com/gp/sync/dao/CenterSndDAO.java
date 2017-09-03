package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.CenterSndInfo;

public interface CenterSndDAO extends BaseDAO<CenterSndInfo>{

	public static RowMapper<CenterSndInfo> MAPPER = new RowMapper<CenterSndInfo>() {

		@Override
		public CenterSndInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			CenterSndInfo info = new CenterSndInfo();
			
			InfoId<Long> id = IdKeys.getInfoId( SyncIdKey.CENTER_SND, rs.getLong("rcv_id"));
			info.setInfoId(id);
			info.setEntityCode(rs.getString("entity_code"));
			info.setNodeCode(rs.getString("node_code"));
			info.setSendTime(rs.getTimestamp("snd_time"));
			info.setBloomData(rs.getString("bloom_data"));
			info.setState(rs.getString("state"));
			
			info.setModifier(rs.getString("modifier"));
			info.setModifyDate(rs.getTimestamp("last_modified"));
			return info;
		}};
}
