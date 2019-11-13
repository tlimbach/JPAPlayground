/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testabcd.parking;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
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
        Stellplatz sp = new Stellplatz(grp, "sp1");

        grp.getStellplaetze().add(sp);

        EntityHelper.merge(grp);
    }

    @Test
    public void testRenameDetatched() {

        Parkplatzgruppe grp = createParkplatzGruppe("jörgensen");

        grp.getStellplaetze().add(new Stellplatz(grp, "jp1"));
        grp.getStellplaetze().add(new Stellplatz(grp, "jp2"));

        EntityHelper.merge(grp);

        EntityManager entitymanager = EntityHelper.getEntitymanager();

        Query query = entitymanager.createQuery("SELECT e FROM Parkplatzgruppe e");
        List<Parkplatzgruppe> grps = (List<Parkplatzgruppe>) query.getResultList();

        assertThat(grps.size(), is(1));

        grp = grps.get(0);
        assertThat(grp.getName(), is("jörgensen"));

        grp.setName("johann");
        EntityHelper.merge(grp);

        grp.setName("Willi");

        grp = (Parkplatzgruppe) EntityHelper.update(grp.getClass(), grp.getId());
        assertThat(grp.getName(), is("johann"));

    }

    @Test
    public void testAddStellplatzDetatched() {

        Parkplatzgruppe grp = createParkplatzGruppe("jörgensen");

        grp.getStellplaetze().add(new Stellplatz(grp, "jp1"));
        grp.getStellplaetze().add(new Stellplatz(grp, "jp2"));

        EntityHelper.merge(grp);

        EntityManager entitymanager = EntityHelper.getEntitymanager();

        Query query = entitymanager.createQuery("SELECT e FROM Parkplatzgruppe e");
        List<Parkplatzgruppe> grps = (List<Parkplatzgruppe>) query.getResultList();

        assertThat(grps.size(), is(1));

        grp = grps.get(0);
        assertThat(grp.getName(), is("jörgensen"));

        grp.setName("johann");
        EntityHelper.merge(grp);

        grp.setName("Willi");

        grp = (Parkplatzgruppe) EntityHelper.update(grp.getClass(), grp.getId());
        assertThat(grp.getName(), is("johann"));

        assertThat(grp.getStellplaetze().size(), is(2));

        // ab hier wirds spannend!
        grp.getStellplaetze().add(new Stellplatz(grp, "Johanns Stellplatz"));

        EntityHelper.merge(grp);
        grp = (Parkplatzgruppe) EntityHelper.update(grp.getClass(), grp.getId());
        assertThat(grp.getName(), is("johann"));

        assertThat(grp.getStellplaetze().size(), is(3));

        grp.getStellplaetze().add(new Stellplatz(grp, "Johanns Stellplatz2"));

        EntityHelper.merge(grp);
        grp = (Parkplatzgruppe) EntityHelper.update(grp.getClass(), grp.getId());
        assertThat(grp.getName(), is("johann"));

        assertThat(grp.getStellplaetze().size(), is(4));

        // jetzt mit "DTO" Gruppe und "update"
        Parkplatzgruppe dto = new Parkplatzgruppe();
        dto.setId(grp.getId());

        Parkplatzgruppe grpFromDto = (Parkplatzgruppe) EntityHelper.update(dto.getClass(), dto.getId());
        grpFromDto.getStellplaetze().add(new Stellplatz(grpFromDto, "Johanns Stellplatz3"));

        EntityHelper.merge(grpFromDto);
        grpFromDto = (Parkplatzgruppe) EntityHelper.update(grpFromDto.getClass(), grpFromDto.getId());
        assertThat(grpFromDto.getName(), is("johann"));

        assertThat(grpFromDto.getStellplaetze().size(), is(5));

        // jetzt mit "DTO" Gruppe und ohne "update"
        dto = new Parkplatzgruppe();
        dto.setId(grpFromDto.getId());

        dto.getStellplaetze().add(new Stellplatz(dto, "Johanns Stellplatz4"));

        EntityHelper.merge(dto);
        grpFromDto = (Parkplatzgruppe) EntityHelper.update(dto.getClass(), dto.getId());

        // Das mehr oder wenier leere dto Object hat das gefüllte Object per merge überschrieben
        assertThat(grpFromDto.getName(), Matchers.nullValue());
        assertThat(grpFromDto.getStellplaetze().size(), is(1));

        grpFromDto.setName("zweite Parkplatzgruppe");

        grpFromDto = (Parkplatzgruppe) EntityHelper.update(grpFromDto.getClass(), grpFromDto.getId());

        assertThat(grpFromDto.getName(), Matchers.nullValue());

        grpFromDto.setName("zweite Parkplatzgruppe");
        EntityHelper.merge(grpFromDto);
        grpFromDto = (Parkplatzgruppe) EntityHelper.update(grpFromDto.getClass(), grpFromDto.getId());

        assertThat(grpFromDto.getName(), is("zweite Parkplatzgruppe"));

    }

    @Test
    public void testAddStellplatzFilledDTO() {

        EntityHelper.clearTable("Stellplatz");
        EntityHelper.clearTable("Parkplatzgruppe");

        Parkplatzgruppe grp = createParkplatzGruppe("jörgensen");

        grp.getStellplaetze().add(new Stellplatz(grp, "jp1"));
        grp.getStellplaetze().add(new Stellplatz(grp, "jp2"));

        EntityHelper.merge(grp);

        EntityManager entitymanager = EntityHelper.getEntitymanager();

        Query query = entitymanager.createQuery("SELECT e FROM Parkplatzgruppe e");
        List<Parkplatzgruppe> grps = (List<Parkplatzgruppe>) query.getResultList();

        assertThat(grps.size(), is(1));

        grp = grps.get(0);
        assertThat(grp.getName(), is("jörgensen"));
        assertThat(grp.getStellplaetze().size(), is(2));

        grp.getStellplaetze().add(new Stellplatz(grp, "abcd"));
        EntityHelper.merge(grp);

        // bestehenden Stellplatz ändern
        Stellplatz stellplatzToBeChanged = grp.getStellplaetze().get(0);
        stellplatzToBeChanged.setName("neuer Name");
        EntityHelper.merge(stellplatzToBeChanged);

        Parkplatzgruppe grpNew = new Parkplatzgruppe();
        grpNew.setName("grpNew");
        EntityHelper.persist(grpNew);

        grp.getStellplaetze().remove(stellplatzToBeChanged);
        grpNew.getStellplaetze().add(stellplatzToBeChanged);
        stellplatzToBeChanged.setParkplatzgruppe(grpNew);

        EntityHelper.merge(grp);
        EntityHelper.merge(grpNew);

        assertThat(grp.getStellplaetze().size(), is(2));
        assertThat(grpNew.getStellplaetze().size(), is(1));

    }

    private Parkplatzgruppe createParkplatzGruppe() {

        EntityHelper.clearTable("Parkplatzgruppe");

        Parkplatzgruppe grp = new Parkplatzgruppe();
        grp.setName("greenfield");

        return (Parkplatzgruppe) EntityHelper.persist(grp);

    }

    private Parkplatzgruppe createParkplatzGruppe(String name) {
        EntityHelper.clearTable("Parkplatzgruppe");

        Parkplatzgruppe grp = new Parkplatzgruppe();
        grp.setName(name);

        return (Parkplatzgruppe) EntityHelper.persist(grp);
    }

}
