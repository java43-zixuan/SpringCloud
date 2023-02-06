#Redis常用的数据类型：String、Hash、List、Set、zSet
##RedisTemplate 常用方法
1. String类型
2. Hash类型
3. List类型
4. Set类型
4. zSet类型


###RedisTemplate常用方法
```
redisTemplate.hasKey(key);				//判断是否有key所对应的值，有则返回true，没有则返回false
redisTemplate.opsForValue().get(key);	//有则取出key值所对应的值
redisTemplate.delete(key);				//删除单个key值
redisTemplate.delete(keys); 			//其中keys:Collection<K> keys
redisTemplate.dump(key);				//将当前传入的key值序列化为byte[]类型
redisTemplate.expire(key, timeout, unit);	//设置过期时间
redisTemplate.expireAt(key, date);		//设置过期时间
redisTemplate.keys(pattern);			//查找匹配的key值，返回一个Set集合类型
redisTemplate.rename(oldKey, newKey);	//返回传入key所存储的值的类型
redisTemplate.renameIfAbsent(oldKey, newKey);	//如果旧值存在时，将旧值改为新值
redisTemplate.randomKey();				//从redis中随机取出一个key
redisTemplate.getExpire(key);			//返回当前key所对应的剩余过期时间
redisTemplate.getExpire(key, unit);		//返回剩余过期时间并且指定时间单位
redisTemplate.persist(key);				//将key持久化保存
redisTemplate.move(key, dbIndex);		//将当前数据库的key移动到指定redis中数据库当中
```

###String类型
```
ValueOperations opsForValue = redisTemplate.opsForValue();

opsForValue.set(key, value);	//设置当前的key以及value值
opsForValue.set(key, value, offset);//用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
opsForValue.set(key, value, timeout, unit);	 //设置当前的key以及value值并且设置过期时间
opsForValue.setBit(key, offset, value);	//将二进制第offset位值变为value
opsForValue.setIfAbsent(key, value);//重新设置key对应的值，如果存在返回false，否则返回true
opsForValue.get(key, start, end);	//返回key中字符串的子字符
opsForValue.getAndSet(key, value);	//将旧的key设置为value，并且返回旧的key
opsForValue.multiGet(keys);			//批量获取值
opsForValue.size(key);				//获取字符串的长度
opsForValue.append(key, value);	//在原有的值基础上新增字符串到末尾
opsForValue.increment(key,double increment);//以增量的方式将double值存储在变量中
opsForValue.increment(key,long  increment);	//通过increment(K key, long delta)方法以增量方式存储long值（正值则自增，负值则自减）
 
Map valueMap = new HashMap();  
valueMap.put("valueMap1","map1");  
valueMap.put("valueMap2","map2");  
valueMap.put("valueMap3","map3");  
opsForValue.multiSetIfAbsent(valueMap); 	//如果对应的map集合名称不存在，则添加否则不做修改
opsForValue.multiSet(valueMap);				//设置map集合到redis
```

###Hash类型
```
HashOperations opsForHash = redisTemplate.opsForHash();

opsForHash.get(key, field);	//获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null
opsForHash.entries(key);	//获取变量中的键值对
opsForHash.put(key, hashKey, value);	//新增hashMap值
opsForHash.putAll(key, maps);	//以map集合的形式添加键值对
opsForHash.putIfAbsent(key, hashKey, value);	//仅当hashKey不存在时才设置
opsForHash.delete(key, fields);	//删除一个或者多个hash表字段
opsForHash.hasKey(key, field);	//查看hash表中指定字段是否存在
opsForHash.increment(key, field, long increment);	//给哈希表key中的指定字段的整数值加上增量increment
opsForHash.increment(key, field, double increment);	//给哈希表key中的指定字段的整数值加上增量increment
opsForHash.keys(key);				//获取所有hash表中字段
opsForHash.values(key);				//获取hash表中存在的所有的值
opsForHash.scan(key, options);		//匹配获取键值对，ScanOptions.NONE为获取全部键对
```

###List类型
```
ListOperations opsForList = redisTemplate.opsForList();

opsForList.index(key, index);	//通过索引获取列表中的元素
opsForList.range(key, start, end);	//获取列表指定范围内的元素(start开始位置, 0是开始位置，end 结束位置, -1返回所有)
opsForList.leftPush(key, value);	//存储在list的头部，即添加一个就把它放在最前面的索引处
opsForList.leftPush(key, pivot, value);		//如果pivot处值存在则在pivot前面添加
opsForList.leftPushAll(key, value);		//把多个值存入List中(value可以是多个值，也可以是一个Collection value)
opsForList.leftPushIfPresent(key, value);	//List存在的时候再加入
opsForList.rightPush(key, value);	//按照先进先出的顺序来添加(value可以是多个值，或者是Collection var2)
opsForList.rightPushAll(key, value);	//在pivot元素的右边添加值
opsForList.set(key, index, value);		//设置指定索引处元素的值
opsForList.trim(key, start, end);		//将List列表进行剪裁
opsForList.size(key);	//获取当前key的List列表长度
 
//移除并获取列表中第一个元素(如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止)
opsForList.leftPop(key);				
opsForList.leftPop(key, timeout, unit);	
 
//移除并获取列表最后一个元素
opsForList.rightPop(key);
opsForList.rightPop(key, timeout, unit);	
 
//从一个队列的右边弹出一个元素并将这个元素放入另一个指定队列的最左边
opsForList.rightPopAndLeftPush(sourceKey, destinationKey);	
opsForList.rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
 
//删除集合中值等于value的元素(index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素; index<0, 从尾部开始删除第一个值等于value的元素)
opsForList.remove(key, index, value);
```

###Set类型
```
SetOperations opsForSet = redisTemplate.opsForSet();

opsForSet.add(key, values);			//添加元素
opsForSet.remove(key, values);		//移除元素(单个值、多个值)
opsForSet.pop(key);					//删除并且返回一个随机的元素
opsForSet.size(key);				//获取集合的大小
opsForSet.isMember(key, value);		//判断集合是否包含value
opsForSet.intersect(key, otherKey);	//获取两个集合的交集(key对应的无序集合与otherKey对应的无序集合求交集)
opsForSet.intersect(key, otherKeys);//获取多个集合的交集(Collection var2)
opsForSet.intersectAndStore(key, otherKey, destKey);	//key集合与otherKey集合的交集存储到destKey集合中(其中otherKey可以为单个值或者集合)
opsForSet.intersectAndStore(key, otherKeys, destKey);	//key集合与多个集合的交集存储到destKey无序集合中
opsForSet.union(key, otherKeys);	//获取两个或者多个集合的并集(otherKeys可以为单个值或者是集合)
opsForSet.unionAndStore(key, otherKey, destKey);	//key集合与otherKey集合的并集存储到destKey中(otherKeys可以为单个值或者是集合)
opsForSet.difference(key, otherKeys);	//获取两个或者多个集合的差集(otherKeys可以为单个值或者是集合)
opsForSet.differenceAndStore(key, otherKey, destKey);	//差集存储到destKey中(otherKeys可以为单个值或者集合)
opsForSet.randomMember(key);	//随机获取集合中的一个元素
opsForSet.members(key);			//获取集合中的所有元素
opsForSet.randomMembers(key, count);	//随机获取集合中count个元素
opsForSet.distinctRandomMembers(key, count);	//获取多个key无序集合中的元素（去重），count表示个数
opsForSet.scan(key, options);	//遍历set类似于Interator(ScanOptions.NONE为显示所有的)
```

###zSet类型
```
ZSetOperations提供了一系列方法对有序集合进行操作
ZSetOperations opsForZSet = redisTemplate.opsForZSet();

opsForZSet.add(key, value, score);				//添加元素(有序集合是按照元素的score值由小到大进行排列)
opsForZSet.remove(key, values);					//删除对应的value,value可以为多个值
opsForZSet.incrementScore(key, value, delta);	//增加元素的score值，并返回增加后的值
opsForZSet.rank(key, value);					//返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
opsForZSet.reverseRank(key, value);				//返回元素在集合的排名,按元素的score值由大到小排列
opsForZSet.reverseRangeWithScores(key, start,end);	//获取集合中给定区间的元素(start 开始位置，end 结束位置, -1查询所有)
opsForZSet.reverseRangeByScore(key, min, max);	//按照Score值查询集合中的元素，结果从小到大排序
opsForZSet.reverseRangeByScoreWithScores(key, min, max);	//返回值为:Set<ZSetOperations.TypedTuple<V>>
opsForZSet.count(key, min, max);				//根据score值获取集合元素数量
opsForZSet.size(key);							//获取集合的大小
opsForZSet.zCard(key);							//获取集合的大小
opsForZSet.score(key, value);					//获取集合中key、value元素对应的score值
opsForZSet.removeRange(key, start, end);		//移除指定索引位置处的成员
opsForZSet.removeRangeByScore(key, min, max);	//移除指定score范围的集合成员
opsForZSet.unionAndStore(key, otherKey, destKey);//获取key和otherKey的并集并存储在destKey中（其中otherKeys可以为单个字符串或者字符串集合）
opsForZSet.intersectAndStore(key, otherKey, destKey);	//获取key和otherKey的交集并存储在destKey中（其中otherKeys可以为单个字符串或者字符串集合）
```