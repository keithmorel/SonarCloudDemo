package com.revature.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.model.Ship;
import com.revature.template.ShipTemplate;

@Repository
public class ShipDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Ship getShipById(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Ship ship = session.get(Ship.class, id);
		return ship;
	}

	@Transactional
	public Ship addShip(ShipTemplate shipTemplate) {

		Session session = sessionFactory.getCurrentSession();
		
		Ship newShip = new Ship(0, shipTemplate.getShipName());
		session.persist(newShip);
		return newShip;
		
	}

}
