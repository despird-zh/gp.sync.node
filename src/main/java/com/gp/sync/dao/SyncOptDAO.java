package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.gp.common.IdKeys;
import com.gp.dao.BaseDAO;
import com.gp.info.InfoId;
import com.gp.sync.SyncIdKey;
import com.gp.sync.dao.info.SyncOptInfo;

public interface SyncOptDAO extends BaseDAO<SyncOptInfo>{

	public List<SyncOptInfo> queryAll() ;
	
	public List<SyncOptInfo> queryByGroup(String groupKey) ;
	
	public SyncOptInfo queryByKey(String optKey) ;
	
	public int updateByKey(String optionKey, String optionValue);
	
	public static RowMapper<SyncOptInfo> MAPPER = new RowMapper<SyncOptInfo>() {

		@Override
		public SyncOptInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			SyncOptInfo info = new SyncOptInfo();
			InfoId<Integer> id = IdKeys.getInfoId(SyncIdKey.SYNC_OPT,rs.getInt("sync_opt_id"));
			info.setInfoId(id);

			info.setOptionGroup(rs.getString("opt_group"));
			info.setOptionKey(rs.getString("opt_key"));
			info.setOptionValue(rs.getString("opt_value"));
			info.setDescription(rs.getString("descr"));
			
			info.setModifier(rs.getString("modifier"));
			info.setModifyDate(rs.getTimestamp("last_modified"));
			return info;
		}};
}
