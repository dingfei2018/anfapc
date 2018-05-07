/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.supyuan.component.base;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.supyuan.jfinal.base.BaseModel;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.log.AsaDmlUser;
import com.supyuan.util.DateUtils;
import com.supyuan.util.NumberUtils;


/**
 * 公共操作日志
 * @author liangxp
 *
 * Date:2017年11月13日上午10:21:44 
 * 
 * @email liangxp@anfawuliu.com
 */
public class KdBaseModel <M extends Model<M>>  extends BaseModel<M> {

	private static final long serialVersionUID = 1L;

	
	public boolean save(SessionUser user) {
		boolean hasCompanyId = false;
		if(getTable().getColumnTypeMap().containsKey("company_id")){
			hasCompanyId = true;
			set("company_id", user.getCompanyId());
		}
		boolean flag = super.save();
		if(hasCompanyId){
			String tableName = getTable().getName();
			String[] keys = getTable().getPrimaryKey();
			Object id = null;
			if (keys != null && keys.length >= 1) {
				id = get(keys[0]);
			}
			Integer primaryId = (id != null) ? NumberUtils.parseInt(id) : null;
			saveLog(tableName, primaryId, user, AsaDmlUser.MODEL_SAVE_NUM);
		}
		return flag;
	}
	
	

	public boolean delete(SessionUser user, String desc) {
		boolean flag = super.delete();
		if(getTable().getColumnTypeMap().containsKey("company_id")){
			String tableName = getTable().getName();
			String[] keys = getTable().getPrimaryKey();
			Object id = null;
			if (keys != null && keys.length >= 1) {
				id = get(keys[0]);
			}
			Integer primaryId = (id != null) ? NumberUtils.parseInt(id) : null;
			saveLog(tableName, primaryId, user, AsaDmlUser.MODEL_DELETE_NUM);
		}
		return flag;
	}
	
	

	public boolean deleteById(SessionUser user, Object id, String desc) {
		boolean flag = super.deleteById(id);
		if(getTable().getColumnTypeMap().containsKey("company_id")){
			String tableName = getTable().getName();
			Integer primaryId = (id != null) ? NumberUtils.parseInt(id) : null;
			saveLog(tableName, primaryId, user, AsaDmlUser.MODEL_DELETE_NUM);
		}
		return flag;
	}

	public boolean update(SessionUser user) {
		boolean flag = super.update();
		if(getTable().getColumnTypeMap().containsKey("company_id")){
			String tableName = getTable().getName();
			String[] keys = getTable().getPrimaryKey();
			Object id = null;
			if (keys != null && keys.length >= 1) {
				id = get(keys[0]);
			}
			Integer primaryId = (id != null) ? NumberUtils.parseInt(id) : null;
			saveLog(tableName, primaryId, user, AsaDmlUser.MODEL_UPDATE_NUM);
		}
		return flag;
	}

	
	
	protected void saveLog(String tableName, Integer primaryId, SessionUser user, int operType) {
		try {
			String sql = "INSERT INTO sas_dml_user(dml_id, dml_user_id, dml_company_id, dml_table_name, dml_table_id, dml_level, dml_time)VALUES (NULL, ?, ?, ?, ?, ?, ?)";
			Db.update(sql, user.getUserId(), user.getCompanyId(), tableName, primaryId, operType,DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS));
		} catch (Exception e) {
			log.error("添加sas_dml_user日志失败", e);
		}
	}

}
