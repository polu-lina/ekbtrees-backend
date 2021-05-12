package ru.naumen.ectmapi.common.mybatis.typehandler;

import org.apache.ibatis.type.MappedTypes;
import org.postgis.Point;

@MappedTypes(Point.class)
public class PointTypeHandler extends GeometryTypeHandler<Point> {

}
