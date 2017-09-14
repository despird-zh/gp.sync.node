package com.gp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.dao.info.SyncPullInfo;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;

public interface SyncPullDAO extends BaseDAO<SyncPullInfo>{

	public static RowMapper<SyncPullInfo> MAPPER = new RowMapper<SyncPullInfo>() {

		@Override
		public SyncPullInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			
			SyncPullInfo info = new SyncPullInfo();
			
			InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.NODE_PULL, rs.getLong("pull_id"));
			info.setInfoId(id);
			info.setEntityCode(rs.getString("entity_code"));
			info.setNodeCode(rs.getString("node_code"));
			info.setBloomData(rs.getString("bloom_data"));
			info.setPullData(rs.getString("pull_data"));
			info.setPullTime(rs.getTimestamp("pull_time"));
			
			info.setModifier(rs.getString("modifier"));
			info.setModifyDate(rs.getTimestamp("last_modified"));
			
			return info;
		}};
}
