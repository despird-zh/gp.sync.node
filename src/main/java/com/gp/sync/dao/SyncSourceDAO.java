package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.SyncSourceInfo;

public interface SyncSourceDAO extends BaseDAO<SyncSourceInfo>{

	public static RowMapper<SyncSourceInfo> MAPPER = new RowMapper<SyncSourceInfo>() {

		@Override
		public SyncSourceInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			SyncSourceInfo info = new SyncSourceInfo();
			InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.NODE_SOURCE,rs.getLong("node_id"));
			
			info.setInfoId(id);
			info.setEntityCode(rs.getString("entity_code"));
			info.setNodeCode(rs.getString("node_code"));
			info.setLastPullOwm(rs.getLong("last_pull_owm"));
			info.setLastPullTime(rs.getTimestamp("last_pull_time"));
			info.setLastPushOwm(rs.getLong("last_push_owm"));
			info.setLastPushTime(rs.getTimestamp("last_push_time"));
			
			info.setModifier(rs.getString("modifier"));
			info.setModifyDate(rs.getTimestamp("last_modified"));
			
			return info;
		}};
}
