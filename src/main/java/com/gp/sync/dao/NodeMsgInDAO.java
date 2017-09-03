package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.NodeMsgInInfo;

public interface NodeMsgInDAO extends BaseDAO<NodeMsgInInfo>{

	public static RowMapper<NodeMsgInInfo> MAPPER = new RowMapper<NodeMsgInInfo>() {

		@Override
		public NodeMsgInInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			NodeMsgInInfo info = new NodeMsgInInfo();
			
			InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.NODE_MSG_IN,rs.getLong("msg_id"));
			info.setInfoId(id);
			info.setPullId(rs.getLong("pull_id"));
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
