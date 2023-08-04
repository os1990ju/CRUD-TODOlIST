package com.prueba.crud.models.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Clase que representa una tarea para la lista de todos
 */
@Entity
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="No puede estar vacio")
    private String name;
    @NotEmpty(message="No puede estar vacio")
    private String state;
    @NotNull(message = "La fecha de expiración no puede estar vacía")
    @Column(name = "expires_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireAt;

    public Long getId( ) {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getName( ) {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Date getExpireAt( ) {
        return expireAt;
    }

    public void setExpireAt( Date expireAt ) {
        this.expireAt = expireAt;
    }

    public String getState( ) {
        return state;
    }

    public void setState( String state ) {
        this.state = state;
    }

    private static final long serialVersionUID = 1L;
}
