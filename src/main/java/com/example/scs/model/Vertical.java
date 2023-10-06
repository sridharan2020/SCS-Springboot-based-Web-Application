package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class Vertical {
    private Integer vertical_id;

    private String name_of_vertical;

    public Integer getVertical_id() {
		return vertical_id;
	}


	public void setVertical_id(Integer vertical_id) {
		this.vertical_id = vertical_id;
	}


	public String getName_of_vertical() {
		return name_of_vertical;
	}


	public void setName_of_vertical(String name_of_vertical) {
		this.name_of_vertical = name_of_vertical;
	}

	@Override
    public String toString() {
        return "Vertical{" +
                "vertical_id=" + vertical_id +
                ", name_of_vertical='" + name_of_vertical + '\'' +
                '}';
    }
}
