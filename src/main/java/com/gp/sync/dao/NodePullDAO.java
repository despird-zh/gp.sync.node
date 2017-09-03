package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.NodePullInfo;

public interface NodePullDAO extends BaseDAO<NodePullInfo>{

	public static RowMapper<NodePullInfo> MAPPER = new RowMapper<NodePullInfo>() {

		@Override
		public NodePullInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			
			NodePullInfo info = new NodePullInfo();
			
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
