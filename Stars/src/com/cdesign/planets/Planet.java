package com.cdesign.planets;

import java.awt.Color;
/*
 * @author Ageev Evgeny
 * 
 * Планета имеет:
 * 1. Имя
 * 2. Внутренний id
 * 3. Радиус
 * 4. Радиус орбиты
 * 5. Скорость движения
 * 6. Цвет
 * 7. Направление (по часовой стрелке, против часовой стрелки)
 * 8. Угол (текущее положение)
 * 9. Родителя (Если не заполнено - значит планета, иначе спутник)
 * 
 */
public class Planet
{
	private String name;
	private int id;
	private double radius = 1.0;
	private double radiusOrbit = 5.0;
	private double velocity = 1;
	private Color color;
	private int angle = 0;
	private String parent;

	public Planet(String name, int id, double rad, double radOrbit, double velocity, Color color)
	{
		this.name = name;
		this.id = id;
		this.radius = rad;
		this.radiusOrbit = radOrbit;
		this.velocity = velocity;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getRadiusOrbit() {
		return radiusOrbit;
	}

	public void setRadiusOrbit(double radiusOrbit) {
		this.radiusOrbit = radiusOrbit;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public int getAngle()
	{
		return angle;
	}
	
	public void setAngle(int newAngle)
	{
		this.angle = newAngle;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getParent() {
		return parent;
	}
}
