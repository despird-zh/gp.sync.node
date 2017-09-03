package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.CenterDistInfo;

public interface CenterDistDAO extends BaseDAO<CenterDistInfo>{

	public static RowMapper<CenterDistInfo> DistMapper = new RowMapper<CenterDistInfo>() {

		@Override
		public CenterDistInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			CenterDistInfo info = new CenterDistInfo();
			
			InfoId<Long> id = IdKeys.getInfoId(SyncIdKey.CENTER_DIST, rs.getLong("dist_id"));
			info.setInfoId(id);
			info.setMsgId(rs.getLong("msg_id"));
			info.setSendId(rs.getLong("snd_id"));
			info.setTargetEntityCode(rs.getString("tgt_entity_code"));
			info.setTargetNodeCode(rs.getString("tgt_node_code"));
			info.setDistributeTime(rs.getTimestamp("dist_time"));
			info.setSyncDone(rs.getBoolean("sync_done"));
			
			info.setModifier(rs.getString("modifier"));
			info.setModifyDate(rs.getTimestamp("last_modified"));
			
			return info;
		}};
}
