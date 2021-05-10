package ru.naumen.ectmapi.converter;

import org.mapstruct.Mapper;
import org.postgis.Point;
import ru.naumen.ectmapi.dto.GeographicalPointDto;

@Mapper(componentModel = "spring")
public abstract class PointConverter {

    public Point fromDto(GeographicalPointDto geographicalPoint) {
        return new Point(geographicalPoint.getLatitude(), geographicalPoint.getLongitude());
    }

    public GeographicalPointDto toDto(Point geographicalPoint) {
        return new GeographicalPointDto(geographicalPoint.getX(), geographicalPoint.getY());
    }
}
