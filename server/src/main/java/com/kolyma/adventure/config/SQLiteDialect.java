package com.kolyma.adventure.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;

public class SQLiteDialect extends Dialect implements FunctionContributor {

    public SQLiteDialect() {
        super();
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new SQLiteIdentityColumnSupport();
    }

    @Override
    public boolean hasAlterTable() {
        return false;
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    @Override
    public String getDropForeignKeyString() {
        return "";
    }

    @Override
    public String getAddForeignKeyConstraintString(String cn, String[] fk, String t, String[] pk, boolean r) {
        return "";
    }

    @Override
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        return "";
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        var registry = functionContributions.getFunctionRegistry();
        var typeConfig = functionContributions.getTypeConfiguration();

        var stringType = typeConfig.getBasicTypeForJavaType(String.class);
        var integerType = typeConfig.getBasicTypeForJavaType(Integer.class);

        // Регистрация функции остатка от деления (mod)
        registry.registerPattern("mod", "?1 % ?2", integerType);

        // Регистрация функции подстроки (substr)
        registry.registerPattern("substr", "substr(?1, ?2, ?3)", stringType);

        // Регистрация функции конкатенации строк (concat)
        registry.registerPattern("concat", "?1 || ?2", stringType);
    }
}