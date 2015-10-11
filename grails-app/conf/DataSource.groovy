dataSource {
    pooled = true
//    driverClassName = "org.hsqldb.jdbcDriver"
    driverClassName = "com.mysql.jdbc.Driver"
    username = "root"
    password = "yoyoma"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//            url = "jdbc:hsqldb:mem:devDB"
            dbCreate = "validate"
            url = "jdbc:mysql://localhost/invest?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=UTF-8"
//			url = "jdbc:mysql://localhost:3306/invest"
        }
    }
    test {
        dataSource {
            dbCreate = "validate"
            url = "jdbc:mysql://localhost/invest_test?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=UTF-8"
//            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            dbCreate = "validate"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}
