package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.NodePushInfo;

public interface NodePushDAO extends BaseDAO<NodePushInfo>{

	public static RowMapper<NodePushInfo> MAPPER = new RowMapper<NodePushInfo>() {

		@Override
		public NodePushInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			
			NodePushInfo info = new NodePushInfo();
			
			InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.NODE_PUSH,rs.getLong("push_id"));
			info.setInfoId(id);
			info.setEntityCode(rs.getString("entity_code"));
			info.setNodeCode(rs.getString("node_code"));
			info.setStartOwm(rs.getLong("start_owm"));
			info.setEndOwm(rs.getLong("end_owm"));
			info.setPushData(rs.getString("push_data"));
			info.setPushTime(rs.getTimestamp("push_time"));
			
			info.setModifier(rs.getString("modifier"));
			info.setModifyDate(rs.getTimestamp("last_modified"));
			
			return info;
		}};
}
