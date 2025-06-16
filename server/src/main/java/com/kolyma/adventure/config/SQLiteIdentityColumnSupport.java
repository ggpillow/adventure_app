package com.kolyma.adventure.config;

import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

// Кастомная поддержка автоинкрементных колонок для SQLite.
// Hibernate использует этот класс для генерации SQL, связанного с автоинкрементом.
public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {

    // Указывает, что SQLite поддерживает автоинкрементные колонки.
    // @return true — поддержка есть.
    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    // Возвращает SQL-запрос для получения последнего сгенерированного автоинкрементного значения.
    // SQLite использует функцию last_insert_rowid().
    // @param table имя таблицы (не используется)
    // @param column имя колонки (не используется)
    // @param type тип колонки (не используется)
    // @return SQL строка для получения последнего ID
    @Override
    public String getIdentitySelectString(String table, String column, int type) {
        return "select last_insert_rowid()";
    }

    // Возвращает SQL-фрагмент для определения колонки с автоинкрементом.
    // В SQLite это просто "integer" (тип INTEGER PRIMARY KEY будет автоинкрементом).
    // @param type тип колонки (не используется)
    // @return SQL тип колонки с автоинкрементом
    @Override
    public String getIdentityColumnString(int type) {
        return "integer";
        // Можно заменить на "integer primary key autoincrement" для явного указания автоинкремента
    }
}