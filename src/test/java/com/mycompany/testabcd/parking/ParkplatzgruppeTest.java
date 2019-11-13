/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testabcd.parking;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author thorsten
 */
public class ParkplatzgruppeTest {

    public ParkplatzgruppeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Parkplatzgruppe.
     */
    @Test
    public void testCreateParkplatzgruppe() {
        createParkplatzGruppe();
    }

    @Test
    public void testMergeParkplatzGruppe() {
        Parkplatzgruppe grp = createParkplatzGruppe();
        grp.setName("grp2");
        EntityHelper.merge(grp);
    }

    @Test
    public void testAddStellplatz() {
        Parkplatzgruppe grp = createParkplatzGruppe();
        Stellplatz sp = new Stellplatz("sp1");

        grp.getStellplaetze().add(sp);

        EntityHelper.merge(grp);
    }

    private Parkplatzgruppe createParkplatzGruppe() {

        EntityHelper.clearTable("Parkplatzgruppe");

        Parkplatzgruppe grp = new Parkplatzgruppe();
        grp.setName("greenfield");

        return (Parkplatzgruppe) EntityHelper.persist(grp);

    }

}
