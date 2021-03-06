/**
 * Copyright (C) 2016 - 2030 youtongluan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yx.db.listener;

import java.util.List;
import java.util.Map;

import org.yx.bean.Bean;
import org.yx.db.annotation.CacheType;
import org.yx.db.event.InsertEvent;
import org.yx.db.sql.PojoMeta;
import org.yx.db.sql.PojoMetaHolder;
import org.yx.listener.SumkEvent;
import org.yx.log.Log;
import org.yx.redis.RecordReq;
import org.yx.util.GsonUtil;

@Bean
public class InsertListener implements DBListener<InsertEvent> {

	@Override
	public boolean accept(SumkEvent event) {
		return InsertEvent.class.isInstance(event);
	}

	@Override
	public void listen(InsertEvent event) {
		try {
			PojoMeta pm = PojoMetaHolder.getTableMeta(event.getTable());
			List<Map<String, Object>> list = event.getPojos();
			if (pm == null || pm.isNoCache() || list == null) {
				return;
			}
			for (Map<String, Object> map : list) {
				String id = pm.getRedisID(map, false);
				if (id == null) {
					continue;
				}
				if (pm.cacheType() == CacheType.LIST) {
					RecordReq.del(pm, id);
					return;
				}
				RecordReq.set(pm, id, GsonUtil.toJson(map));
			}
		} catch (Exception e) {
			Log.printStack("db-listener", e);
		}
	}

	@Override
	public String[] getTags() {
		return null;
	}

}
