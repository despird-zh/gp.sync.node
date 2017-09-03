package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.NodeMsgOutInfo;

public interface NodeMsgOutDAO extends BaseDAO<NodeMsgOutInfo>{

	public static RowMapper<NodeMsgOutInfo> MAPPER = new RowMapper<NodeMsgOutInfo>() {

		@Override
		public NodeMsgOutInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			NodeMsgOutInfo info = new NodeMsgOutInfo();
			
			InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.NODE_MSG_IN,rs.getLong("msg_id"));
			info.setInfoId(id);
			info.setPushId(rs.getLong("push_id"));
			info.setEntityCode(rs.getString("entity_code"));
			info.setNodeCode(rs.getString("node_code"));
			info.setTraceCode(rs.getString("trace_code"));
			info.setOwm(rs.getLong("owm"));
			info.setSyncCommand(rs.getString("sync_cmd"));
			info.setMsgData(rs.getString("msg_data"));
			
			info.setModifier(rs.getString("modifier"));
			info.setModifyDate(rs.getTimestamp("last_modified"));
			
			return info;
		}};
}
