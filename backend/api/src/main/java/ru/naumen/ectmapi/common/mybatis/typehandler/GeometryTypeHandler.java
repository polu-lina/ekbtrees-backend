package ru.naumen.ectmapi.common.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgis.Geometry;
import org.postgis.PGgeometry;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GeometryTypeHandler<T extends Geometry> extends BaseTypeHandler<T> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, T geom, JdbcType jdbcType) throws SQLException {
        PGgeometry geometry = new PGgeometry();
        geometry.setGeometry(geom);
        preparedStatement.setObject(i, geometry);
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
        PGgeometry pGgeometry = (PGgeometry) resultSet.getObject(s);
        if (pGgeometry == null) {
            return null;
        }
        return (T) pGgeometry.getGeometry();
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
        PGgeometry pGgeometry = (PGgeometry) resultSet.getObject(i);
        if (pGgeometry == null) {
            return null;
        }
        return (T) pGgeometry.getGeometry();
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        PGgeometry pGgeometry = (PGgeometry) callableStatement.getObject(i);
        if (pGgeometry == null) {
            return null;
        }
        return (T) pGgeometry.getGeometry();
    }
}
