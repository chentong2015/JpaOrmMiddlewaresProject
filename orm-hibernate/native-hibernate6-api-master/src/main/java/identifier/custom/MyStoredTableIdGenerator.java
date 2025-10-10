package identifier.custom;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

// 自定义ID的生成器，根据不同的策略实现generate()方法
// - UuidGenerator
// - IncrementGenerator
// - IncrementGenerator
// - ...
public class MyStoredTableIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor implementor, Object object) throws HibernateException {
        return null;
    }
}
