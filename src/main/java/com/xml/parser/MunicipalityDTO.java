package com.xml.parser;

import java.util.List;

import com.xml.parser.entity.District;
import com.xml.parser.entity.Municipality;

import lombok.Data;

@Data
public class MunicipalityDTO {
    List<Municipality> municipalities;
    List<District> districts;
}