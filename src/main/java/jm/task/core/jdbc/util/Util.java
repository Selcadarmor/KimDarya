package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.util.Properties;
import org.hibernate.Session;

public class Util {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Создаем объект Configuration для конфигурации Hibernate
            Configuration configuration = new Configuration();

            // Создание объекта Properties для программной настройки
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/projectdaryakim"); // Замените на ваш URL
            properties.setProperty("hibernate.connection.username", "root"); // Замените на ваше имя пользователя
            properties.setProperty("hibernate.connection.password", "070196^Slava*"); // Замените на ваш пароль
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.hbm2ddl.auto", "update"); // Для автоматического обновления схемы

            // Применяем настройки из Properties
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

            // Создаем SessionFactory на основе конфигурации
            sessionFactory = configuration.buildSessionFactory();

        } catch (Throwable ex) {
            // В случае ошибки инициализации выбрасываем исключение
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Метод для получения SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Метод для закрытия SessionFactory (необязательный, но полезный)
    public static void shutdown() {
        getSessionFactory().close();
    }

    // Метод для получения JDBC-соединения через Hibernate
    public static Connection getConnection() {
        // Открытие сессии
        Session session = sessionFactory.openSession();

        // Получаем соединение с помощью doReturningWork
        return session.doReturningWork(connection -> connection);
    }
}
