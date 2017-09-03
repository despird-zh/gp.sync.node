package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.CenterMsgInfo;

public interface CenterMsgDAO extends BaseDAO<CenterMsgInfo>{

	public static RowMapper<CenterMsgInfo> CMSG_Mapper = new RowMapper<CenterMsgInfo>() {

		@Override
		public CenterMsgInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			CenterMsgInfo info  = new CenterMsgInfo();
			
			InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.CENTER_MSG, rs.getLong("msg_id"));
			info.setInfoId(id);
			info.setReceiveId(rs.getLong("rcv_id"));
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
