package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RecordModel {
    private LocalDate birthDate, targetDate;
    private double physical, emotional, intellectual;

    public RecordModel(LocalDate birthDate, LocalDate targetDate) {
	this.birthDate = birthDate;
	this.targetDate = targetDate;

	long dayDifference = ChronoUnit.DAYS.between(this.birthDate, this.targetDate);

	this.physical = (double) Math.round(Math.sin((2 * Math.PI * dayDifference) / 23) * 10000) / 10000;
	this.emotional = (double) Math.round(Math.sin((2 * Math.PI * dayDifference) / (23 + 5)) * 10000) / 10000;
	this.intellectual = (double) Math.round(Math.sin((2 * Math.PI * dayDifference) / (23 + 10)) * 10000) / 10000;
    }

    public LocalDate getBirthDate() {
	return birthDate;
    }

    public LocalDate getTargetDate() {
	return targetDate;
    }

    public double getPhysical() {
	return physical;
    }

    public double getEmotional() {
	return emotional;
    }

    public double getIntellectual() {
	return intellectual;
    }

}
