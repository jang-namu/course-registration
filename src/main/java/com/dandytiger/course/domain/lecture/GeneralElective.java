package com.dandytiger.course.domain.lecture;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GE")
public class GeneralElective extends Lecture {

}
