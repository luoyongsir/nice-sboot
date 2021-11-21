redis cluster 的封装 基于lettuce

```java

// redisson 分布式锁示例; 更多使用方法,请参考官方文档
@Service
public class TestRLock {

    @Autowired
    private RedissonClient redissonClient;

    private void testLock(){
        String lockKey = "a:b:key";
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        try {
            // do something
        } finally {
            lock.unlock();
        }
    }
}

```
