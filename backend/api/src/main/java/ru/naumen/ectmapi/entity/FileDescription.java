package ru.naumen.ectmapi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "file_description")
@Data
public class FileDescription {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "size")
    private Long size;

    @Column(name = "location")
    private String location;

    @Column(name = "author_code")
    private String authorCode;

}
