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
package org.yx.db;

import java.util.List;
import java.util.Map;

import org.yx.db.kit.LocalSqlHolder;
import org.yx.exception.SumkException;

public class RawUtil {

	public static int execute(String name, Object... params) {
		String sql = LocalSqlHolder.findSql(name);
		return RawDB.execute(sql, params);
	}

	public static List<Map<String, Object>> list(String name, Object... params) {
		String sql = LocalSqlHolder.findSql(name);
		return RawDB.list(sql, params);
	}

	public static List<?> singleColumnList(String name, Object... params) {
		String sql = LocalSqlHolder.findSql(name);
		return RawDB.singleColumnList(sql, params);
	}

	public static int count(String name, Object... params) {
		String sql = LocalSqlHolder.findSql(name);
		return RawDB.count(sql, params);
	}

	public static Map<String, Object> selectOne(String name, Object... params) {
		List<Map<String, Object>> list = list(name, params);
		if (list == null || list.size() != 1) {
			SumkException.throwException(521343, name + " -- result is not only one");
		}
		return list.get(0);
	}

}
