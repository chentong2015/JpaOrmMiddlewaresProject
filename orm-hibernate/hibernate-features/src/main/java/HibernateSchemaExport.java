// TODO. 实现数据库的迁移和表的自动创建
// 使用代码自动创建表, 相当于.cfg.xml中的配置属性"hbm2ddl.auto"
// 查找"hibernate.cfg.xml"文件中<mapping>来生成数据库表
public class HibernateSchemaExport {

    // Hibernate 3.x 4.x
    // Configuration configuration = new Configuration().configure();
    // SchemaExport schema = new SchemaExport(configuration);
    // schema.create(true, true); 是否生成ddl脚本，是否更新到数据库

    // Hibernate 5.0.x
    // StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    // MetadataImplementor metadata = (MetadataImplementor) new MetadataSources(registry).buildMetadata();
    // SchemaExport export = new SchemaExport(registry, metadata);
    // export.create(true, true);

    // Hibernate 5.1.x - 5.6.9
    // StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    // Metadata metadata = new MetadataSources(registry).buildMetadata();
    // SchemaExport schemaExport = new SchemaExport();
    // schemaExport.create(EnumSet.of(Target.DATABASE), metadata);

    // Hibernate 6.0.1
}
