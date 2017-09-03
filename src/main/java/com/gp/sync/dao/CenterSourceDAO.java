package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.CenterSourceInfo;

public interface CenterSourceDAO extends BaseDAO<CenterSourceInfo>{

	public static RowMapper<CenterSourceInfo> Mapper = new RowMapper<CenterSourceInfo>(){

		@Override
		public CenterSourceInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			CenterSourceInfo info = new CenterSourceInfo();
			
			InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.CENTER_SOURCE, rs.getLong("node_id"));
			info.setInfoId(id);
			info.setNodeCode(rs.getString("node_code"));
			info.setEntityCode(rs.getString("entity_code"));
			info.setLastReceiveOwm(rs.getLong("last_rcv_owm"));
			info.setLastReceiveTime(rs.getTimestamp("last_rcv_time"));
			info.setLastSendTime(rs.getTimestamp("last_snd_time"));
			info.setLastSendOwm(rs.getLong("last_snd_owm"));
			
			info.setModifier(rs.getString("modifier"));
			info.setModifyDate(rs.getTimestamp("last_modified"));
			return info;
		}};
}
