/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testabcd.parking;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author thorsten
 */
public class StellplatzTest {

    public StellplatzTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateStellplatz() {
        createTestStellplatz();
    }

    @Test
    public void testChangeStellplatzName() {
        Stellplatz sp = createTestStellplatz();
        assertTrue(sp.getId() > 0);
        assertThat(sp.getName(), is("ElCheffe"));

        sp.setName("Donald");
        EntityHelper.merge(sp);

    }

    @Test
    public void testChangeDetatchedStellplatzName() {
        Stellplatz sp = createTestStellplatz();
        assertTrue(sp.getId() > 0);
        assertThat(sp.getName(), is("ElCheffe"));

        Long id = sp.getId();

        Stellplatz dSp = new Stellplatz();
        dSp.setId(id);

        dSp.setName("Hannibal");

        EntityHelper.merge(dSp);
    }

    private static Stellplatz createTestStellplatz() {

        EntityHelper.clearTable("Stellplatz");

        Stellplatz stellplatz = new Stellplatz();
        stellplatz.setName("ElCheffe");

        return (Stellplatz) EntityHelper.persist(stellplatz);
    }

}
