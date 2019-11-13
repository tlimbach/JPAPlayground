/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testabcd.parking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author thorsten
 */
@Entity
public class Parkplatzgruppe {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
