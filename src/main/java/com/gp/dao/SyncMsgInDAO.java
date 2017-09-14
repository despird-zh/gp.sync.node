package com.gp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.dao.info.SyncMsgInInfo;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;

public interface SyncMsgInDAO extends BaseDAO<SyncMsgInInfo>{

	public static RowMapper<SyncMsgInInfo> MAPPER = new RowMapper<SyncMsgInInfo>() {

		@Override
		public SyncMsgInInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			SyncMsgInInfo info = new SyncMsgInInfo();
			
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
